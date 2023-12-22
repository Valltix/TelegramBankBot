package telegram.menu.general;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


public class DescriptionButtonsMessage {
    private static SendMessage message = new SendMessage();

    public static SendMessage aboutUAH(SendMessage message) {
        message.setText("Сучасна гривня (символ — ₴, код — UAH) — офіційна валюта України. " +
                "Сьогодні в обігу перебувають монети номіналом 10, 50 копійок, 1, 2, 5, 10 гривень і банкноти " +
                "номіналом 1, 2, 5, 10, 20, 50, 100, 200, 500, 1000 гривень. Історія української валюти сягає " +
                "часів Київської Русі та походить від слова «гривна» — стародавньої нашийної прикраси, а також вагової, " +
                "лічильної та монетної одиниці, що мала поширення на теренах сучасної України.");
        return message;
    }

    public static SendMessage botAbilities(SendMessage message) {
        message.setText("Зараз розповім, що я можу. \uD83E\uDD14 \n" +
                "По-перше, обожнювати кожного, хто до мене завітав ❤\uFE0F \n" +
                "По-друге, надати тобі: \n" +
                " - поточний курс різних валют, які постійно оновлюються; \n" +
                " - обрати курс певного банку; \n" +
                " - обрати кількість знаків після коми, щоб керувати точністю; \n\n" +
                "Та ще дещо цікаве, можливість встановити дату та час, в який я відправлю тобі поточний курс \uD83D\uDD70 -> ☺\uFE0F");
        return message;
    }
}
