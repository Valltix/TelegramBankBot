package initialization;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

            if (callBackQuery.equals("1")){
                message.setText("Технічні оновлення, вибачте за тимчасові незручності, намагаємося все владнати якомога скоріше ☺\uFE0F ");
            }

            if (callBackQuery.equals("2")){
                message.setText("Сучасна гривня (символ — ₴, код — UAH) — офіційна валюта України. " +
                        "Сьогодні в обігу перебувають монети номіналом 10, 50 копійок, 1, 2, 5, 10 гривень і банкноти " +
                        "номіналом 1, 2, 5, 10, 20, 50, 100, 200, 500, 1000 гривень. Історія української валюти сягає " +
                        "часів Київської Русі та походить від слова «гривна» — стародавньої нашийної прикраси, а також вагової, " +
                        "лічильної та монетної одиниці, що мала поширення на теренах сучасної України.");

                inputFile.setMedia(new File("src/main/java/initialization/image/UAH.jpeg"));
                try {
                    execute(photo);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (callBackQuery.equals("3")){
                message.setText("Зараз розповім, що я можу. \uD83E\uDD14 \n" +
                        "По-перше, обожнювати кожного, хто до мене завітав ❤\uFE0F \n" +
                        "По-друге, надати тобі: \n" +
                        " - поточний курс різних валют, які постійно оновлюються; \n" +
                        " - обрати курс певного банку; \n" +
                        " - обрати кількість знаків після коми, щоб керувати точністю; \n\n" +
                        "Та ще дещо цікаве, можливість встановити дату та час, в який я відправлю тобі поточний курс \uD83D\uDD70 -> ☺\uFE0F");

                inputFile.setMedia(new File("src/main/java/initialization/image/about.jpeg"));
                try {
                    execute(photo);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
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
