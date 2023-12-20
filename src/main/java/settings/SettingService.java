package settings;

import utils.Utils;
import java.io.IOException;

public class SettingService {

    public static String SETTINGS_PATH = "./src/main/java/settings/"; // standard static path to setting files

    /**
     * Отримує налаштування для юзера по значенню параметра chatId із файлу json що названий за паттерном chatId + "_settings.json".
     * Якщо такого файле не існує, то викликає метод для задання дефолтних налаштувань.
     * en:
     * Get the current User's settings by chatId from json file named in format chatId + "_settings.json". If there is no
     * such file, then method for default setting will be called.
     * @param chatId - ID of the current active chat.
     * @return UserSettings.class object.
     */
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

    /**
     * Зчитує json файл, який містить дефолтні налаштування і створює новий UserSettings об'єкт. Додає chatId і викликає метод
     * setSettings() для того щоб зберегти дефолтні налаштування як поточні налаштування юзера.
     * en:
     * Read json file with default settings and creates UserSettings object. Sets chatId and calls setSettings() method to
     * save default settings as a current user's settings.
     * @param chatId - ID of the current active chat
     * @return - created UserSettings object
     */
    private static UserSettings setDefaultSettings(long chatId) {
        UserSettings set = Utils.readFromJsonFileChecked(getSettingsPath("default"), UserSettings.class);
        set.setChatId(chatId);
        setSettings(chatId, set);
        return set;
    }

    public static void setSettings(long chatId, UserSettings settings) {
        // TODO: get new Settings and save it in file json (update exiting or create new one), named like chatId + "_settings.json"
        //  Suggestion: create Utils method writeToJsonFile like readFromJsonFile"
    }

    private static String getSettingsPath(String chatId) {
        return SETTINGS_PATH + chatId + "_settings.json";
    }


}
