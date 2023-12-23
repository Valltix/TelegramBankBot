package notification;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.File;
import java.util.*;
import java.util.Calendar;

public class NotificationScheduler {

    private static Scheduler scheduler;

    public static void main(String[] args) {
        String settingDirectory = "./src/main/java/settings/default_settings.json";
        Timer timer = new Timer();
        timer.schedule(new NotificationTask(settingDirectory), 0, 60 * 1000);
    }

    public static void init() throws SchedulerException {
        System.out.println("Init scheduler");
        StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
        schedulerFactory.initialize(getSchedulerProperties());

        scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        System.out.println("scheduler.isStarted() " + scheduler.isStarted());
        // Через 20 секунд зупиняємо завдання для chatId
//        stopDailyTask("5153293342", 20);
    }

    public static void addNotification(String chatId, int hour, int minute) throws SchedulerException {
        if (scheduler == null){
            init();
        }
        startDailyTask(chatId, hour, minute);

    }


    private static void startDailyTask(String chatId, int hour, int minute) throws SchedulerException {
        // Визначаємо час виконання завдання
        System.out.println("Daily task will be executed at " + hour + " " + minute);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        JobDetail job = JobBuilder.newJob(NotificationJob.class)
                .withIdentity(chatId, "daily-jobs")
                .usingJobData("chatId", chatId)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(chatId + "-trigger", "daily-triggers")
                .startAt(calendar.getTime())
//                .withSchedule(SimpleScheduleBuilder.repeatHourlyForever(24))
                .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(2))
//                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(19, 12).inTimeZone(timeZone))
                .build();

        scheduler.scheduleJob(job, trigger);
    }

    private static void stopDailyTask(String chatId, int delayInSeconds) throws SchedulerException {
        // Через delayInSeconds секунд відмінюємо завдання для користувача
        scheduler.scheduleJob(JobBuilder.newJob().build(), TriggerBuilder.newTrigger()
                .withIdentity(chatId + "-stop-trigger", "stop-triggers")
                .startAt(new Date(System.currentTimeMillis() + delayInSeconds * 1000))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
                .forJob(chatId, "daily-jobs")
                .build());
    }

    private static Properties getSchedulerProperties() {
        Properties properties = new Properties();
        properties.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, "org.quartz.simpl.SimpleThreadPool");
        properties.put("org.quartz.threadPool.threadCount", "10"); // Визначаємо максимальну кількість потоків у пулі
        properties.put("org.quartz.threadPool.threadPriority", "5"); // При необхідності, визначте пріоритет потоків
        return properties;
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
