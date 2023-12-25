package telegram.menu.settings;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class SettingsMessages {
    private static final SendMessage message = new SendMessage();

    public static SendMessage settingsMenu(SendMessage message) {
        message.setText("Виберіть налаштування які хочете змінити ☺️");
        return message;
    }

    public static SendMessage bankMenu(SendMessage message) {
        message.setText("Виберіть банк з якого отримувати курс валют ☺️");
        return message;
    }
    public static SendMessage selectedBank(SendMessage message) {
        message.setText("Обраний банк: ");
        return message;
    }
    public static SendMessage selectedSignAfrerComma(SendMessage message) {
        message.setText("Знаків після коми: ");
        return message;
    }
    public static SendMessage signAfrerComma(SendMessage message) {
        message.setText("Оберіть кількість знаків після коми ");
        return message;
    }
    public static SendMessage currencyMenu(SendMessage message) {
        message.setText("Виберіть валюти, курс яких Ви бажаєте отримувати");
        return message;
    }
    public static SendMessage selectedCurrency(SendMessage message) {
        message.setText("Вибрано валюти: ");
        return message;
    }
    public static SendMessage notificationMenu(SendMessage message) {
        message.setText("Виберіть, час коли Ви бажаєте отримувати сповіщення");
        return message;
    }
    public static SendMessage selectedNotification(SendMessage message) {
        message.setText("Вибрано наступний час коли для отримування сповіщення: ");
        return message;
    }
}
