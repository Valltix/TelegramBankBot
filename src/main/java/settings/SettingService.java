package settings;

import utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class SettingService {

    public static Map<Long, UserSettings> settings = new HashMap<>();

    /**
     * Get the current User's settings by chatId.
     * @param chatId is the ID of the current active chat.
     * @return Settings.class object.
     */
    public static UserSettings getCurrentSettings(long chatId) {

        var currentSettings = settings.get(chatId);

        if  (currentSettings == null) {
            currentSettings = setDefaultSettings(chatId);
        }

        return currentSettings;
    }

    private static UserSettings setDefaultSettings(long chatId) {
        UserSettings set = Utils.readFromJsonFile("./src/main/java/settings/default_settings.json", UserSettings.class);
        set.setChatId(chatId);
        settings.put(chatId, set); // Saving into imagined database (Map)
        return set;
    }

    public static void setSettings(long chatId, UserSettings settings) {
        // TODO: get new Settings and save it in map or database with chatId received
    }


}
