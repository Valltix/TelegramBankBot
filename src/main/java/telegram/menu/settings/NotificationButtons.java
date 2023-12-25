package telegram.menu.settings;

import notification.NotificationScheduler;
import org.quartz.SchedulerException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import settings.UserSettings;
import telegram.menu.Menu;

import java.time.LocalTime;
import java.util.*;

public class NotificationButtons {

    private static final Set<Menu> selectedNotification = new HashSet<>();


    public static InlineKeyboardMarkup setButtons() {



        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();

        InlineKeyboardButton button8 = createNotificationButton(Menu.NOTIFICATION_8, "8");
        InlineKeyboardButton button9 = createNotificationButton(Menu.NOTIFICATION_9, "9");
        InlineKeyboardButton button10 = createNotificationButton(Menu.NOTIFICATION_10, "10");
        InlineKeyboardButton button11 = createNotificationButton(Menu.NOTIFICATION_11, "11");
        InlineKeyboardButton button12 = createNotificationButton(Menu.NOTIFICATION_12, "12");
        InlineKeyboardButton button13 = createNotificationButton(Menu.NOTIFICATION_13, "13");
        InlineKeyboardButton button14 = createNotificationButton(Menu.NOTIFICATION_14, "14");
        InlineKeyboardButton button15 = createNotificationButton(Menu.NOTIFICATION_15, "15");
        InlineKeyboardButton button16 = createNotificationButton(Menu.NOTIFICATION_16, "16");
        InlineKeyboardButton button17 = createNotificationButton(Menu.NOTIFICATION_17, "17");
        InlineKeyboardButton button18 = createNotificationButton(Menu.NOTIFICATION_18, "18");
        InlineKeyboardButton button19 = createNotificationButton(Menu.NOTIFICATION_19, "19");
        InlineKeyboardButton buttonDisable = createNotificationButton(Menu.NOTIFICATION_DISABLE, "Вимкнути сповіщення");

        InlineKeyboardButton buttonPrevMenu = InlineKeyboardButton
                .builder()
                .text("Попереднє меню")
                .callbackData(Menu.SETTINGS.name())
                .build();
        InlineKeyboardButton buttonMainMenu = InlineKeyboardButton
                .builder()
                .text("Головне меню")
                .callbackData(Menu.START.name())
                .build();

        keyboardButtonsRow1.add(button8);
        keyboardButtonsRow1.add(button9);
        keyboardButtonsRow1.add(button10);
        keyboardButtonsRow1.add(button11);
        keyboardButtonsRow1.add(button12);
        keyboardButtonsRow1.add(button13);
        keyboardButtonsRow2.add(button14);
        keyboardButtonsRow2.add(button15);
        keyboardButtonsRow2.add(button16);
        keyboardButtonsRow2.add(button17);
        keyboardButtonsRow2.add(button18);
        keyboardButtonsRow2.add(button19);
        keyboardButtonsRow3.add(buttonDisable);
        keyboardButtonsRow4.add(buttonPrevMenu);
        keyboardButtonsRow4.add(buttonMainMenu);

        return InlineKeyboardMarkup
                .builder()
                .keyboardRow(keyboardButtonsRow1)
                .keyboardRow(keyboardButtonsRow2)
                .keyboardRow(keyboardButtonsRow3)
                .keyboardRow(keyboardButtonsRow4)
                .build();
    }

    private static InlineKeyboardButton createNotificationButton(Menu notificationMenu, String name) {
        String buttonText = name;
        if (selectedNotification.contains(notificationMenu)) {
            buttonText += " ✅";
        }

        return InlineKeyboardButton
                .builder()
                .text(buttonText)
                .callbackData(notificationMenu.name())
                .build();
    }

    public static void handleNotificationButton(String notificationCallbackData, UserSettings userSettings) {
        Menu notificationMenu = Menu.valueOf(notificationCallbackData);

        // Если выбранное уведомление уже в списке, то ничего не делаем
        if (selectedNotification.contains(notificationMenu)) {
            return;
        }

        // Очищаем список и добавляем новое выбранное уведомление
        selectedNotification.clear();
        selectedNotification.add(notificationMenu);

        // Обновляем настройки пользователя
        switch (notificationMenu) {
            case NOTIFICATION_DISABLE:
                // Отключаем уведомления
                userSettings.setNotificationOn(false);
                updateNotificationSchedule(userSettings);
                break;
            case NOTIFICATION_8:
                // Включаем уведомления и устанавливаем время на 8 утра
                userSettings.setNotificationOn(true);
                userSettings.setNotification(LocalTime.of(8, 0, 0));
                updateNotificationSchedule(userSettings);
                break;
            case NOTIFICATION_9:
                // Включаем уведомления и устанавливаем время на 9 утра
                userSettings.setNotificationOn(true);
                userSettings.setNotification(LocalTime.of(9, 0, 0));
                updateNotificationSchedule(userSettings);
                break;
            case NOTIFICATION_10:
                // Включаем уведомления и устанавливаем время на 10 утра
                userSettings.setNotificationOn(true);
                userSettings.setNotification(LocalTime.of(10, 0, 0));
                updateNotificationSchedule(userSettings);
                break;
            case NOTIFICATION_11:
                // Включаем уведомления и устанавливаем время на 11 утра
                userSettings.setNotificationOn(true);
                userSettings.setNotification(LocalTime.of(11, 0, 0));
                updateNotificationSchedule(userSettings);
                break;
            case NOTIFICATION_12:
                // Включаем уведомления и устанавливаем время на 12 дня
                userSettings.setNotificationOn(true);
                userSettings.setNotification(LocalTime.of(12, 0, 0));
                updateNotificationSchedule(userSettings);
                break;
            case NOTIFICATION_13:
                // Включаем уведомления и устанавливаем время на 13 часов
                userSettings.setNotificationOn(true);
                userSettings.setNotification(LocalTime.of(13, 0, 0));
                updateNotificationSchedule(userSettings);
                break;
            case NOTIFICATION_14:
                // Включаем уведомления и устанавливаем время на 14 часов
                userSettings.setNotificationOn(true);
                userSettings.setNotification(LocalTime.of(14, 0, 0));
                updateNotificationSchedule(userSettings);
                break;
            case NOTIFICATION_15:
                // Включаем уведомления и устанавливаем время на 15 часов
                userSettings.setNotificationOn(true);
                userSettings.setNotification(LocalTime.of(15, 0, 0));
                updateNotificationSchedule(userSettings);
                break;
            case NOTIFICATION_16:
                // Включаем уведомления и устанавливаем время на 16 часов
                userSettings.setNotificationOn(true);
                userSettings.setNotification(LocalTime.of(16, 0, 0));
                updateNotificationSchedule(userSettings);
                break;
            case NOTIFICATION_17:
                // Включаем уведомления и устанавливаем время на 17 часов
                userSettings.setNotificationOn(true);
                userSettings.setNotification(LocalTime.of(17, 0, 0));
                updateNotificationSchedule(userSettings);
                break;
            case NOTIFICATION_18:
                // Включаем уведомления и устанавливаем время на 18 часов
                userSettings.setNotificationOn(true);
                userSettings.setNotification(LocalTime.of(18, 0, 0));
                updateNotificationSchedule(userSettings);
                break;
            case NOTIFICATION_19:
                // Включаем уведомления и устанавливаем время на 19 часов
                userSettings.setNotificationOn(true);
                userSettings.setNotification(LocalTime.of(20, 46, 0));
                updateNotificationSchedule(userSettings);
                break;
            default:
                throw new IllegalArgumentException("Unknown notification button: " + notificationCallbackData);
        }

    }

    private static void updateNotificationSchedule(UserSettings userSettings) {
        try {
            NotificationScheduler.updateScheduledJob(String.valueOf(userSettings.getChatId()), userSettings.getNotification().getHour(), userSettings.getNotification().getMinute());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
