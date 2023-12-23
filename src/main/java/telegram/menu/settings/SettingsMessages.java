package telegram.menu.settings;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class SettingsMessages {
    private static final SendMessage message = new SendMessage();

    public static SendMessage settingsMenu(SendMessage message) {
        message.setText("Виберіть яке налаштування Ви б хотіли змінити ☺️");
        return message;
    }

    public static SendMessage bankMenu(SendMessage message) {
        message.setText("Виберіть банк з якого Ви б хотіли отримувати курс валют ☺️");
        return message;
    }
    public static SendMessage selectedBank(SendMessage message) {
        message.setText("Вибрані банки: ");
        //TODO: "Якщо не вибрано жодного банку то дати сповіщення або виконати іншу дію"
        return message;
    }
    public static SendMessage selectedSignAfrerComma(SendMessage message) {
        message.setText("Вибранo точність: ");
        return message;
    }
    public static SendMessage signAfrerComma(SendMessage message) {
        message.setText("Виберіть бажану точність ");
        return message;
    }
}
