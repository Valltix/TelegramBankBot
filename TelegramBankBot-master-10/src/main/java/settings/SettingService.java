package settings;

import banks.CurrencyName;
import utils.Utils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class SettingService {

    public static final String SETTINGS_PATH = "./src/main/java/settings/";

    public static UserSettings getCurrentSettings(long chatId) {
        UserSettings currentSettings;

        try {
            currentSettings = Utils.readFromJsonFile(getSettingsPath(String.valueOf(chatId)), UserSettings.class);
        } catch (IOException e) {
            System.out.println("Default settings will be set for " + chatId);
            currentSettings = setDefaultSettings(chatId);
        }

        return currentSettings;
    }

    private static UserSettings setDefaultSettings(long chatId) {
        UserSettings defaultSettings = Utils.readFromJsonFileChecked(getSettingsPath("default"), UserSettings.class);
        defaultSettings.setChatId(chatId);
        setSettings(chatId, defaultSettings);
        return defaultSettings;
    }

    public static void setSettings(long chatId, UserSettings settings) {
        try {
            writeToJsonFile(getSettingsPath(String.valueOf(chatId)), settings);
        } catch (IOException e) {
            System.err.println("Error saving settings for " + chatId + ": " + e.getMessage());
        }
    }

    public static void writeToJsonFile(String settingsPath, UserSettings settings) throws IOException {
        Utils.writeToJsonFile(settingsPath, settings);
    }

    public static void main(String[] args) {
        // Example of creating, reading, and writing UserSettings
        long chatId = 123456789L;

        // Create a new UserSettings object
        UserSettings newUserSettings = new UserSettings();
        newUserSettings.setChatId(chatId);
       // newUserSettings.setBank(new Bank("ExampleBank")); // Assuming Bank has a constructor that takes the bank name
        newUserSettings.setAfterPoint(2);
        newUserSettings.setCurrencies(List.of(CurrencyName.USD, CurrencyName.EUR));
        newUserSettings.setNotification(LocalTime.now());

        // Save the settings
        setSettings(chatId, newUserSettings);

        // Retrieve and print the saved settings
        UserSettings retrievedSettings = getCurrentSettings(chatId);
        System.out.println("Retrieved Settings:\n" + retrievedSettings);
    }

    private static String getSettingsPath(String chatId) {
        return SETTINGS_PATH + chatId + "_settings.json";
    }
}
