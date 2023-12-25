package telegram.menu.exchange;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.menu.Menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultButtons {
    public static InlineKeyboardMarkup setButtons() {
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

        InlineKeyboardButton buttonRate = InlineKeyboardButton
                .builder()
                .text("Отримати курс \uD83D\uDCC8")
                .callbackData(Menu.GET_RATE.name())
                .build();

        InlineKeyboardButton buttonSettings = InlineKeyboardButton
                .builder()
                .text("Налаштування ⚙️")
                .callbackData(Menu.SETTINGS.name())
                .build();

        InlineKeyboardButton buttonMainMenu = InlineKeyboardButton
                .builder()
                .text("Головне меню \uD83C\uDFE0")
                .callbackData(Menu.START.name())
                .build();

        keyboardButtonsRow1.add(buttonRate);
        keyboardButtonsRow2.add(buttonSettings);
        keyboardButtonsRow2.add(buttonMainMenu);

        return InlineKeyboardMarkup
                .builder()
                .keyboardRow(keyboardButtonsRow1)
                .keyboardRow(keyboardButtonsRow2)
                .build();
    }
}
