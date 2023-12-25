package telegram.menu.settings;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import settings.UserSettings;
import telegram.menu.Menu;

import java.util.*;

public class BankButtons {

private static final Set<Menu> selectedBanks = new HashSet<>();

    public static InlineKeyboardMarkup setButtons(UserSettings userSettings) {
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        readUserBankSettings(userSettings);
        InlineKeyboardButton buttonNBU = createBankButton(Menu.BANK_NBU, "НБУ");
        InlineKeyboardButton buttonPrivatBank = createBankButton(Menu.BANK_PRIVATBANK, "ПриватБанк");
        InlineKeyboardButton buttonMonoBank = createBankButton(Menu.BANK_MONOBANK, "МоноБанк");

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

        keyboardButtonsRow1.add(buttonPrivatBank);
        keyboardButtonsRow1.add(buttonMonoBank);
        keyboardButtonsRow2.add(buttonNBU);
        keyboardButtonsRow3.add(buttonPrevMenu);
        keyboardButtonsRow3.add(buttonMainMenu);

        return InlineKeyboardMarkup
                .builder()
                .keyboardRow(keyboardButtonsRow1)
                .keyboardRow(keyboardButtonsRow2)
                .keyboardRow(keyboardButtonsRow3)
                .build();
    }

    private static InlineKeyboardButton createBankButton(Menu bankMenu, String name) {
        String buttonText = name;
        if (selectedBanks.contains(bankMenu)) {
            buttonText += " ✅";
        }


        return InlineKeyboardButton
                .builder()
                .text(buttonText)
                .callbackData(bankMenu.name())
                .build();
    }

    private static void readUserBankSettings(UserSettings userSettings){
        selectedBanks.clear();
        switch (userSettings.getBankName()) {
            case "NBU":
                selectedBanks.add(Menu.BANK_NBU);
                break;
            case "Privat":
                selectedBanks.add(Menu.BANK_PRIVATBANK);
                break;
            case "Monobank":
                selectedBanks.add(Menu.BANK_MONOBANK);
                break;
            default:
                throw new IllegalArgumentException("Unknown bank button: " + userSettings.getBankName());
        }
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
        selectedBanks.clear();
        selectedBanks.add(Menu.valueOf(buttonData));
    }

    public static Set<Menu> getSelectedBanks() {
        return Collections.unmodifiableSet(selectedBanks);
    }
}
