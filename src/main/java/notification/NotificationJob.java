package notification;

import banks.CurrencyName;
import banks.ExchangeRateService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import settings.SettingService;
import telegram.initialization.BotInitializer;
import telegram.initialization.BotService;
import telegram.menu.exchange.DefaultButtons;


public class NotificationJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        BotInitializer telegramBot;

        String chatId = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("chatId");
        System.out.println("Executing job for chat id " + chatId);

        var settings = SettingService.getCurrentSettings(Long.parseLong(chatId));
        System.out.println("With notification  " + settings.getNotificationOn());

        if(!settings.getNotificationOn()){
            try {
                NotificationScheduler.deleteNotification(chatId);
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        try {
            telegramBot = BotService.getInstance();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(ExchangeRateService.getExchangeRateMessage(CurrencyName.UAH, settings));
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("Error executing job for chat id " + chatId);
            System.out.println("Error message " + e.getMessage());
        }

    }
}
