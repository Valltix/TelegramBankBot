package telegram.menu.settings;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.menu.Menu;

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

    private static String createNotificationButton(String buttonText) {
        if (selectedNotification.contains(Menu.valueOf(buttonText))) {
            buttonText += " ✔";
        }
        return buttonText;
    }

    private static InlineKeyboardButton createNotificationButton(Menu notificationMenu, String name) {
        String buttonText = name;
        if (selectedNotification.contains(notificationMenu)) {
            buttonText += " ✔";
        }

        return InlineKeyboardButton
                .builder()
                .text(buttonText)
                .callbackData(notificationMenu.name())
                .build();
    }

    public static void handleNotificationButton(String notificationCallbackData) {
        Menu notificationMenu = Menu.valueOf(notificationCallbackData);
        if (selectedNotification.contains(notificationMenu)) {
            selectedNotification.remove(notificationMenu);
        } else {
            selectedNotification.clear();
            selectedNotification.add(notificationMenu);
        }
    }

    public static Set<Menu> getSelectedNotification() {
        return Collections.unmodifiableSet(selectedNotification);
    }
}
