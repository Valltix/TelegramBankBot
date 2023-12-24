package telegram.menu.settings;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.menu.Menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingsButtons {
    public static InlineKeyboardMarkup setButtons() {
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

        InlineKeyboardButton buttonBank = InlineKeyboardButton
                .builder()
                .text("Банк")
                .callbackData(Menu.BANK.name())
                .build();

        InlineKeyboardButton buttonCurrency = InlineKeyboardButton
                .builder()
                .text("Валюта")
                .callbackData(Menu.CURRENCY.name())
                .build();

        InlineKeyboardButton buttonNotification = InlineKeyboardButton
                .builder()
                .text("Сповіщення")
                .callbackData(Menu.NOTIFICATION.name())
                .build();

        InlineKeyboardButton buttonSignsAfterComa = InlineKeyboardButton
                .builder()
                .text("Знаки після коми")
                .callbackData(Menu.SIGNS_AFTER_COMA.name())
                .build();

        keyboardButtonsRow1.add(buttonBank);
        keyboardButtonsRow1.add(buttonSignsAfterComa);
        keyboardButtonsRow2.add(buttonCurrency);
        keyboardButtonsRow2.add(buttonNotification);
        return InlineKeyboardMarkup
                .builder()
                .keyboardRow(keyboardButtonsRow1)
                .keyboardRow(keyboardButtonsRow2)
                .build();
    }
}
