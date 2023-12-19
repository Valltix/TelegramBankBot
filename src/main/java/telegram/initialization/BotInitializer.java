package telegram.initialization;

import banks.Currency;
import banks.ExchangeRateService;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import settings.SettingService;
import telegram.initialization.commands.StartCommand;
import telegram.menu.exchange.DefaultButtons;
import telegram.menu.exchange.DefaultMenu;
import telegram.menu.general.GeneralMenu;
import java.io.File;

public class BotInitializer extends TelegramLongPollingCommandBot {

    public BotInitializer() {
        register(new StartCommand());
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()){

            String callBackQuery = update.getCallbackQuery().getData();

            SendMessage message = new SendMessage();
            SendPhoto photo = new SendPhoto();
            InputFile inputFile = new InputFile();

            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            message.setChatId(chatId);
            photo.setChatId(chatId);
            photo.setPhoto(inputFile);

            message.setParseMode("markdown");

            if (callBackQuery.equals(GeneralMenu.CURRENCY_RATE.name())) {
                message.setText("Дякую ☺️\nОберіть нові _Налаштування_, або отримайте курс за поточними: \n\n" + SettingService.getCurrentSettings(chatId));
                message.setReplyMarkup(DefaultButtons.setButtons());
            }

            if (callBackQuery.equals(String.valueOf(GeneralMenu.ABOUT_UAH))){
                message.setText("Сучасна гривня (символ — ₴, код — UAH) — офіційна валюта України. " +
                        "Сьогодні в обігу перебувають монети номіналом 10, 50 копійок, 1, 2, 5, 10 гривень і банкноти " +
                        "номіналом 1, 2, 5, 10, 20, 50, 100, 200, 500, 1000 гривень. Історія української валюти сягає " +
                        "часів Київської Русі та походить від слова «гривна» — стародавньої нашийної прикраси, а також вагової, " +
                        "лічильної та монетної одиниці, що мала поширення на теренах сучасної України.");

                inputFile.setMedia(new File("src/main/java/telegram/initialization/image/UAH.jpeg"));
                try {
                    execute(photo);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (callBackQuery.equals(String.valueOf(GeneralMenu.BOT_ABILITIES))){
                message.setText("Зараз розповім, що я можу. \uD83E\uDD14 \n" +
                        "По-перше, обожнювати кожного, хто до мене завітав ❤\uFE0F \n" +
                        "По-друге, надати тобі: \n" +
                        " - поточний курс різних валют, які постійно оновлюються; \n" +
                        " - обрати курс певного банку; \n" +
                        " - обрати кількість знаків після коми, щоб керувати точністю; \n\n" +
                        "Та ще дещо цікаве, можливість встановити дату та час, в який я відправлю тобі поточний курс \uD83D\uDD70 -> ☺\uFE0F");

                inputFile.setMedia(new File("src/main/java/telegram/initialization/image/about.jpeg"));
                try {
                    execute(photo);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            // added for further Settings buttons and logic implementation
            if (DefaultMenu.SETTINGS.isQuery(callBackQuery)) { // this approach can be implemented for every ENUM
                message.setText("Технічні оновлення, вибачте за тимчасові незручності, намагаємося все владнати якомога скоріше ☺️");
                // TODO: implement settings button functionality
            }

            // Button request to get exchange rate according to the current set settings.
            // The default keyboard (Отримати курс, Налаштування) will be displayed. Exchange rate for the bank selected and currencies,
            // will be returned with selected after point parameter, in message text.
            if (DefaultMenu.GET_RATE.isQuery(callBackQuery)) {
                var settings = SettingService.getCurrentSettings(chatId); // Setting object is used here to get User's current settings
                message.setText(ExchangeRateService.getExchangeRateMessage(Currency.UAH, settings));
                message.setReplyMarkup(DefaultButtons.setButtons());
            }


            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Button pressed = " + callBackQuery);
            System.out.println("chatId = " + chatId);
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
