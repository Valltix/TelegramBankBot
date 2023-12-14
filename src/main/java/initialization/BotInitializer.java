package initialization;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotInitializer extends TelegramLongPollingCommandBot {

    public BotInitializer() {
        register(new StartCommand());

    }

    @Override
    public void processNonCommandUpdate(Update update) {
        System.out.println("Non-command");

    }

//    @Override
//    public void onUpdateReceived(Update update) {
//        System.out.println("Update received");
//        if (update.hasCallbackQuery()){
//            String callBackQuery = update.getCallbackQuery().getData();
//            System.out.println(callBackQuery);
//        }
//
//    }

    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BotConstants.BOT_TOKEN;
    }


}
