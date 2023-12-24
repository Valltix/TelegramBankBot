package notification;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import settings.SettingService;
import settings.UserSettings;

import java.io.File;
import java.util.*;
import java.util.Calendar;

public class NotificationScheduler {

    private static Scheduler scheduler;
    private static final Map<String, JobDetail> jobDetailsMap = new HashMap<>();
    private static final Map<String, Trigger> triggersMap = new HashMap<>();


    public static void init() throws SchedulerException {
        System.out.println("Init scheduler");
        StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
        schedulerFactory.initialize(getSchedulerProperties());

        scheduler = schedulerFactory.getScheduler();
        scheduler.start();


        Timer timer = new Timer();
        timer.schedule(new DBNotificationInitiator(), 500);


        System.out.println("scheduler.isStarted() " + scheduler.isStarted());
    }

    public static void addNotification(String chatId) throws SchedulerException {
        UserSettings userSettings = SettingService.getCurrentSettings(Long.parseLong(chatId));

        if (scheduler == null){
            init();
        }
            startDailyTask(chatId, userSettings.getNotification().getHour(), userSettings.getNotification().getMinute());
    }
    public static void deleteNotification(String chatId) throws SchedulerException {
        if (scheduler == null){
            init();
        }
        stopDailyTask(chatId);
    }


    private static void startDailyTask(String chatId, int hour, int minute) throws SchedulerException {
        if (jobDetailsMap.containsKey(chatId) && triggersMap.containsKey(chatId)) {
            deleteNotification(chatId);
        }
        // Визначаємо час виконання завдання
        System.out.println("Daily task will be executed at " + hour + " " + minute + " for chat id " + chatId);

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
                .withSchedule(SimpleScheduleBuilder.repeatHourlyForever(24))
//                .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(2))
                .build();

        jobDetailsMap.put(chatId, job);
        triggersMap.put(chatId, trigger);
        scheduler.scheduleJob(job, trigger);
    }

    private static void stopDailyTask(String chatId) throws SchedulerException {
        JobDetail jobDetail = jobDetailsMap.get(chatId);
        Trigger trigger = triggersMap.get(chatId);

        if (jobDetail != null && trigger != null) {
            scheduler.unscheduleJob(trigger.getKey());
            scheduler.deleteJob(jobDetail.getKey());
            jobDetailsMap.remove(chatId);
            triggersMap.remove(chatId);
            System.out.println("Job for chat id " + chatId + " has been unscheduled.");
        } else {
            System.out.println("No job found for chat id " + chatId);
        }
    }

    private static Properties getSchedulerProperties() {
        Properties properties = new Properties();
        properties.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, "org.quartz.simpl.SimpleThreadPool");
        properties.put("org.quartz.threadPool.threadCount", "10");
        properties.put("org.quartz.threadPool.threadPriority", "5");
        return properties;
    }

}
 class DBNotificationInitiator extends TimerTask {
     @Override
     public void run() {
         String settingsDirectory = "./src/main/java/settings/";

         File directory = new File(settingsDirectory);
         File[] userFiles = directory.listFiles();

        if (userFiles != null) {
            for (File userFile : userFiles) {
                if (userFile.isFile() && userFile.getName().endsWith(".json")) {
                    String chatId = userFile.getName().replace("_settings.json", "");
                    if(chatId.contains("default")){
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
