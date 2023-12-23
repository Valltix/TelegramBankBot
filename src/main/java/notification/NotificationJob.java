package notification;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.initialization.BotInitializer;
import telegram.initialization.BotService;


public class NotificationJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        BotInitializer telegramBot;


        String chatId = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("chatId");
        System.out.println("Executing job for user id " + chatId);



        try {
            // Отримання вашого бота
            telegramBot = BotService.getInstance();
            // Відправлення повідомлення

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Test notification for userId " +chatId);
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
