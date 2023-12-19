package telegram.menu.exchange;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.menu.Menu;

import java.util.Collections;

public class DefaultButtons {
    public static InlineKeyboardMarkup setButtons() {

        InlineKeyboardButton buttonRate = InlineKeyboardButton
                .builder()
                .text("Отримати курс 📊")
                .callbackData(Menu.GET_RATE.name())
                .build();

        InlineKeyboardButton buttonSettings = InlineKeyboardButton
                .builder()
                .text("Налаштування ⚙️")
                .callbackData(Menu.SETTINGS.name())
                .build();

        return InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(Collections.singletonList(buttonRate)))
                .keyboard(Collections.singletonList(Collections.singletonList(buttonSettings)))
                .build();
    }
}
