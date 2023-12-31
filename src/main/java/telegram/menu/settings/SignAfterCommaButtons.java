package telegram.menu.settings;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import settings.UserSettings;
import telegram.menu.Menu;

import java.util.*;

public class SignAfterCommaButtons {

    private static final Set<Menu> selectedSignsAfterComma = new HashSet<>();

    public static InlineKeyboardMarkup setButtons(UserSettings userSettings) {
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        readUserSignAfterCommaSettings(userSettings);
        InlineKeyboardButton buttonSign1 = createButton(Menu.SIGNS_AFTER_COMA_1, "1");
        InlineKeyboardButton buttonSign2 = createButton(Menu.SIGNS_AFTER_COMA_2, "2");
        InlineKeyboardButton buttonSign3 = createButton(Menu.SIGNS_AFTER_COMA_3, "3");
        InlineKeyboardButton buttonSign4 = createButton(Menu.SIGNS_AFTER_COMA_4, "4");

        InlineKeyboardButton buttonPrevMenu = InlineKeyboardButton
                .builder()
                .text("Попереднє меню ↪\uFE0F")
                .callbackData(Menu.SETTINGS.name())
                .build();

        InlineKeyboardButton buttonMainMenu = InlineKeyboardButton
                .builder()
                .text("Головне меню \uD83C\uDFE0")
                .callbackData(Menu.START.name())
                .build();

        keyboardButtonsRow1.add(buttonSign1);
        keyboardButtonsRow1.add(buttonSign2);
        keyboardButtonsRow2.add(buttonSign3);
        keyboardButtonsRow2.add(buttonSign4);
        keyboardButtonsRow3.add(buttonPrevMenu);
        keyboardButtonsRow3.add(buttonMainMenu);

        return InlineKeyboardMarkup
                .builder()
                .keyboardRow(keyboardButtonsRow1)
                .keyboardRow(keyboardButtonsRow2)
                .keyboardRow(keyboardButtonsRow3)
                .build();
    }

    private static InlineKeyboardButton createButton(Menu itemMenu, String name) {
        String buttonText = name;
        if (selectedSignsAfterComma.contains(itemMenu)) {
            buttonText += " \u2714";
        }

        return InlineKeyboardButton
                .builder()
                .text(buttonText)
                .callbackData(itemMenu.name())
                .build();
    }

    public static void handleSingButton(String signCallbackData, UserSettings userSettings) {
        Menu signMenu = Menu.valueOf(signCallbackData);
        if (selectedSignsAfterComma.contains(signMenu)) {
            return;
        } else {
            selectedSignsAfterComma.clear();
            selectedSignsAfterComma.add(signMenu);

            // Обновляем настройки пользователя
            switch (signMenu) {
                case SIGNS_AFTER_COMA_1:
                    userSettings.setAfterPoint(1);
                    break;
                case SIGNS_AFTER_COMA_2:
                    userSettings.setAfterPoint(2);
                    break;
                case SIGNS_AFTER_COMA_3:
                    userSettings.setAfterPoint(3);
                    break;
                case SIGNS_AFTER_COMA_4:
                    userSettings.setAfterPoint(4);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown sign button: " + signCallbackData);
            }
        }
    }

    private static void readUserSignAfterCommaSettings(UserSettings userSettings){
        selectedSignsAfterComma.clear();
        switch (userSettings.getAfterPoint()) {
            case 1:
                selectedSignsAfterComma.add(Menu.SIGNS_AFTER_COMA_1);
                break;
            case 2:
                selectedSignsAfterComma.add(Menu.SIGNS_AFTER_COMA_2);
                break;
            case 3:
                selectedSignsAfterComma.add(Menu.SIGNS_AFTER_COMA_3);
                break;
            case 4:
                selectedSignsAfterComma.add(Menu.SIGNS_AFTER_COMA_4);
                break;
            default:
                throw new IllegalArgumentException("Unknown sign after comma: " + userSettings.getAfterPoint());
        }
    }

    public static Set<Menu> getSelectedSignsAfterComma() {
        return Collections.unmodifiableSet(selectedSignsAfterComma);
    }
}
