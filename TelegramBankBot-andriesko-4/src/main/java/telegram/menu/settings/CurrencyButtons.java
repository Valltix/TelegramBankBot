package telegram.menu.settings;

import banks.CurrencyName;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import settings.UserSettings;
import telegram.menu.Menu;

import java.util.*;

public class CurrencyButtons {

private static final Set<Menu> selectedCurrency = new HashSet<>();

    public static InlineKeyboardMarkup setButtons() {
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();

        InlineKeyboardButton buttonUSD = createCurrencyButton(Menu.USD, "Долар СЩА");
        InlineKeyboardButton buttonEUR = createCurrencyButton(Menu.EUR, "Євро");
        InlineKeyboardButton buttonPLN = createCurrencyButton(Menu.PLN, "Злотий");
        InlineKeyboardButton buttonGBP = createCurrencyButton(Menu.GBP, "Фунт стерлінгів");

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

        keyboardButtonsRow1.add(buttonUSD);
        keyboardButtonsRow1.add(buttonEUR);
        keyboardButtonsRow2.add(buttonPLN);
        keyboardButtonsRow2.add(buttonGBP);
        keyboardButtonsRow3.add(buttonPrevMenu);
        keyboardButtonsRow3.add(buttonMainMenu);

        return InlineKeyboardMarkup
                .builder()
                .keyboardRow(keyboardButtonsRow1)
                .keyboardRow(keyboardButtonsRow2)
                .keyboardRow(keyboardButtonsRow3)
                .build();
    }

    private static InlineKeyboardButton createCurrencyButton(Menu curencyMenu, String name) {
        String buttonText = name;
        if (selectedCurrency.contains(curencyMenu)) {
            buttonText += " ✔";
        }

        return InlineKeyboardButton
                .builder()
                .text(buttonText)
                .callbackData(curencyMenu.name())
                .build();
    }

    public static void handleCurrencyButton(String currencyCallbackData, UserSettings userSettings) {
        Menu currencyMenu = Menu.valueOf(currencyCallbackData);
        System.out.println("currencyCallbackData = " + currencyCallbackData);

        switch (currencyMenu) {
            case USD:
            case EUR:
            case PLN:
            case GBP:
                List<CurrencyName> selectedCurrencies = userSettings.getCurrencies();
                if (selectedCurrencies.contains(CurrencyName.valueOf(currencyCallbackData))) {
                    // Если валюта уже выбрана, удаляем ее
                    selectedCurrencies.remove(CurrencyName.valueOf(currencyCallbackData));
                } else {
                    // Если валюта не выбрана, добавляем ее
                    selectedCurrencies.clear();
                    selectedCurrencies.add(CurrencyName.valueOf(currencyCallbackData));
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown currency button: " + currencyCallbackData);
        }
    }
}
