package notification;

import org.quartz.SchedulerException;

import java.io.File;
import java.util.TimerTask;

public class DBNotificationInitiator extends TimerTask {
    @Override
    public void run() {
        String settingsDirectory = "./src/main/java/settings/";

        File directory = new File(settingsDirectory);
        File[] userFiles = directory.listFiles();

        if (userFiles != null) {
            for (File userFile : userFiles) {
                if (userFile.isFile() && userFile.getName().endsWith(".json")) {
                    String chatId = userFile.getName().replace("_settings.json", "");
                    if (chatId.contains("default")) {
                        continue;
                    }
                    try {
                        runScheduler(chatId);
                    } catch (SchedulerException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void runScheduler(String chatId) throws SchedulerException {
        try {
            NotificationScheduler.addNotification(chatId);
        } catch (SchedulerException e) {
            System.out.println("Error adding notification for chat id " + chatId);
            NotificationScheduler.deleteNotification(chatId);
        }
    }
}
