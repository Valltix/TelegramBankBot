package settings;

import javax.management.Notification;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationScheduler {
    public static void main(String[] args) {
        String settingDirectory = "./src/main/java/settings/default_settings.json";

        Timer timer = new Timer();
        timer.schedule(new NotificationTask(settingDirectory), 0, 60 * 1000);
    }
}
 class NotificationTask extends TimerTask {
    private String setingsDirectory;
    public NotificationTask(String setingsDirectory) {
        this.setingsDirectory = setingsDirectory;
    }

    @Override
     public void run() {
        File directory = new File(setingsDirectory);
        File[] userFiles = directory.listFiles();

        if (userFiles != null) {
            for (File userFile : userFiles) {
                if (userFile.isFile() && userFile.getName().endsWith(".json")) {
                    String userId = userFile.getName().replace(".json", "");
                    String userSettings = readUserSettings(userFile);
                }
            }
        }
    }

    private String readUserSettings (File userFile) {
        return null;
    }
 }
