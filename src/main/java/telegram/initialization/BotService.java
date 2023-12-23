package telegram.initialization;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotService {
    private static BotInitializer telegramBot;

    public BotService() {
        telegramBot = getInstance();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public static BotInitializer getInstance() {
        if (telegramBot == null) {
            telegramBot = new BotInitializer();
        }
        return telegramBot;
    }
}
