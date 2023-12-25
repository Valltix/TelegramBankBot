package telegram.menu.settings;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import settings.UserSettings;
import telegram.menu.Menu;

import java.util.*;
import java.util.stream.Collectors;

public class BankButtons {

private static final Set<Menu> selectedBanks = new HashSet<>();

    public static InlineKeyboardMarkup setButtons() {
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

        InlineKeyboardButton buttonNBU = createBankButton(Menu.BANK_NBU, "НБУ");
        InlineKeyboardButton buttonPrivatBank = createBankButton(Menu.BANK_PRIVATBANK, "ПриватБанк");
        InlineKeyboardButton buttonMonoBank = createBankButton(Menu.BANK_MONOBANK, "МоноБанк");

        InlineKeyboardButton buttonPrevMenu = InlineKeyboardButton
                .builder()
                .text("Попереднє меню ")
                .callbackData(Menu.SETTINGS.name())
                .build();

        keyboardButtonsRow1.add(buttonNBU);
        keyboardButtonsRow1.add(buttonMonoBank);
        keyboardButtonsRow2.add(buttonPrivatBank);
        keyboardButtonsRow2.add(buttonPrevMenu);

        return InlineKeyboardMarkup
                .builder()
                .keyboardRow(keyboardButtonsRow1)
                .keyboardRow(keyboardButtonsRow2)
                .build();
    }

    private static InlineKeyboardButton createBankButton(Menu bankMenu, String name) {
        String buttonText = name;
        if (selectedBanks.contains(bankMenu)) {
            buttonText += " \u2714";
        }

        return InlineKeyboardButton
                .builder()
                .text(buttonText)
                .callbackData(bankMenu.name())
                .build();
    }

    public static void handleBankButton(String buttonData, UserSettings userSettings) {
        switch (buttonData) {
            case "BANK_NBU":
                userSettings.setBankName("NBU");
                break;
            case "BANK_PRIVATBANK":
                userSettings.setBankName("Privat");
                break;
            case "BANK_MONOBANK":
                userSettings.setBankName("Monobank");
                break;
            default:
                throw new IllegalArgumentException("Unknown bank button: " + buttonData);
        }
    }

    public static Set<Menu> getSelectedBanks() {
        return Collections.unmodifiableSet(selectedBanks);
    }
}
