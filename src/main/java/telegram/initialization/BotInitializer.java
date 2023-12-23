package telegram.initialization;

import banks.CurrencyName;
import banks.ExchangeRateService;
import notification.NotificationScheduler;
import org.quartz.SchedulerException;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import settings.SettingService;
import telegram.initialization.commands.HelpCommand;
import telegram.initialization.commands.MainMenuCommand;
import telegram.initialization.commands.StartCommand;
import telegram.menu.Menu;
import telegram.menu.exchange.DefaultButtons;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static telegram.menu.general.DescriptionButtonsMessage.aboutUAH;
import static telegram.menu.general.DescriptionButtonsMessage.botAbilities;

public class BotInitializer extends TelegramLongPollingCommandBot {

    public BotInitializer() {
        register(new StartCommand());
        register(new HelpCommand());
        register(new MainMenuCommand());

        setBotCommands();
        try {
            new NotificationScheduler().init();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

    }

    private SendMessage message = new SendMessage();
    private SendPhoto photo = new SendPhoto();
    private InputFile inputFile = new InputFile();



    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.hasCallbackQuery()) {

            String callBackQuery = update.getCallbackQuery().getData();

            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            message.setChatId(chatId);
            photo.setChatId(chatId);
            photo.setPhoto(inputFile);

            message.setParseMode("markdown"); // дає можливість стилізувати текст (жирний, курсив і т.д.).

            switch (Menu.valueOf(callBackQuery)) {
                case CURRENCY_RATE -> {
                    message.setText("Дякую ☺️\nОберіть нові _Налаштування_, або отримайте курс за поточними: \n\n" + SettingService.getCurrentSettings(chatId));
                    message.setReplyMarkup(DefaultButtons.setButtons());
                }

                case ABOUT_UAH -> {
                    aboutUAH(message);
                    inputFile.setMedia(new File(BotConstants.UAH_PATH));
                    try {
                        execute(photo);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }

                case BOT_ABILITIES -> {
                    botAbilities(message);
                    inputFile.setMedia(new File(BotConstants.ABOUT_PATH));
                    try {
                        execute(photo);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }

                // added for further Settings buttons and logic implementation
                case SETTINGS -> {
                    message.setText("Технічні оновлення, вибачте за тимчасові незручності, намагаємося все владнати якомога скоріше ☺️");
                    // TODO: implement settings button functionality
                }

                // Button request to get exchange rate according to the current set settings.
                // The default keyboard (Отримати курс, Налаштування) will be displayed. Exchange rate for the bank selected and currencies,
                // will be returned with selected after point parameter, in message text.
                case GET_RATE -> {
                    var settings = SettingService.getCurrentSettings(chatId); // Setting object is used here to get User's current settings
                    message.setText(ExchangeRateService.getExchangeRateMessage(CurrencyName.UAH, settings));
                    message.setReplyMarkup(DefaultButtons.setButtons());
                }

            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Button pressed = " + callBackQuery);
            System.out.println("chatId = " + chatId);
        }

        //Response for any random text from user
        if (update.hasMessage()) {

            String responseText = "На жаль, ви ввели невірну команду, будь-ласка, оберіть іншу \uD83D\uDE0A";
            message.setText(responseText);
            message.setChatId(update.getMessage().getChatId());

            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setBotCommands() {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("start", "Початок роботи"));
        commands.add(new BotCommand("help", "Допомога"));
        commands.add(new BotCommand("menu", "Головне меню"));
        SetMyCommands setMyCommands = new SetMyCommands();
        setMyCommands.setCommands(commands);
        try {
            this.execute(setMyCommands);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BotConstants.BOT_TOKEN;
    }
}
