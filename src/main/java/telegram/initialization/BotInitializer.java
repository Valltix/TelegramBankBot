package telegram.initialization;

import banks.CurrencyName;
import banks.ExchangeRateService;
import notification.NotificationScheduler;
import org.quartz.SchedulerException;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import settings.SettingService;
import settings.UserSettings;
import telegram.initialization.commands.HelpCommand;
import telegram.initialization.commands.MainMenuCommand;
import telegram.initialization.commands.StartCommand;
import telegram.menu.Menu;
import telegram.menu.exchange.DefaultButtons;
import telegram.menu.general.StartButtons;
import telegram.menu.settings.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static telegram.menu.general.DescriptionButtonsMessage.*;
import static telegram.menu.settings.SettingsMessages.*;

public class BotInitializer extends TelegramLongPollingCommandBot {

    public BotInitializer() {
        register(new StartCommand());
        register(new HelpCommand());
        register(new MainMenuCommand());

        setBotCommands();
        try {
            NotificationScheduler.init();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

    }

    private final SendMessage message = new SendMessage();
    private final SendPhoto photo = new SendPhoto();
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
                case START -> {
                    message.setText("Ви знаходитесь на головному меню! Оберіть, будь-ласка, дію ☺️");
                    message.setReplyMarkup(StartButtons.setButtons());
                }

                case CURRENCY_RATE -> {
                    message.setText("Отримайте курс за поточними налаштуваннями, або встановіть нові. \n\nПоточні: \n" + SettingService.getCurrentSettings(chatId));
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

                case SETTINGS -> {
                    settingsMenu(message);
                    message.setReplyMarkup(SettingsButtons.setButtons());
                }

                case BANK -> {
                    bankMenu(message);
                    message.setReplyMarkup(BankButtons.setButtons());

                }
                case BANK_NBU, BANK_PRIVATBANK, BANK_MONOBANK -> {
                    UserSettings userSettings = SettingService.getCurrentSettings(chatId);
                    BankButtons.handleBankButton(callBackQuery, userSettings);
                    SettingService.setSettings(chatId, userSettings);
                    selectedBank(message);
                    message.setReplyMarkup(BankButtons.setButtons());

                }

                case SIGNS_AFTER_COMA ->{
                    signAfrerComma(message);
                    message.setReplyMarkup(SignAfterCommaButtons.setButtons());
                }

                case SIGNS_AFTER_COMA_1, SIGNS_AFTER_COMA_2, SIGNS_AFTER_COMA_3, SIGNS_AFTER_COMA_4 -> {
                    UserSettings userSettings = SettingService.getCurrentSettings(chatId);
                    SignAfterCommaButtons.handleSingButton(callBackQuery, userSettings);
                    SettingService.setSettings(chatId, userSettings);
                    selectedSignAfrerComma(message);
                    message.setReplyMarkup(SignAfterCommaButtons.setButtons());

                }
                case CURRENCY ->{
                    currencyMenu(message);
                    message.setReplyMarkup(CurrencyButtons.setButtons());
                }

                case USD, EUR, PLN, GBP -> {
                    UserSettings userSettings = SettingService.getCurrentSettings(chatId);
                    CurrencyButtons.handleCurrencyButton(callBackQuery, userSettings);
                    SettingService.setSettings(chatId, userSettings);
                    selectedCurrency(message);
                    message.setReplyMarkup(CurrencyButtons.setButtons());

                }
                case NOTIFICATION ->{
                    notificationMenu(message);
                    message.setReplyMarkup(NotificationButtons.setButtons());
                }

                case NOTIFICATION_DISABLE, NOTIFICATION_8, NOTIFICATION_9, NOTIFICATION_10, NOTIFICATION_11, NOTIFICATION_12, NOTIFICATION_13, NOTIFICATION_14, NOTIFICATION_15, NOTIFICATION_16, NOTIFICATION_17, NOTIFICATION_18, NOTIFICATION_19 -> {
                    UserSettings userSettings = SettingService.getCurrentSettings(chatId);
                    NotificationButtons.handleNotificationButton(callBackQuery, userSettings);
                    SettingService.setSettings(chatId, userSettings);
                    selectedNotification(message);
                    message.setReplyMarkup(NotificationButtons.setButtons());

                   /* try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    */
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
        commands.add(new BotCommand("start", "Початок роботи \uD83D\uDE80"));
        commands.add(new BotCommand("menu", "Головне меню \uD83C\uDFE0"));
        commands.add(new BotCommand("help", "Допомога \uD83E\uDEF6"));
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
