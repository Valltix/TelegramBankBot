package telegram.menu.exchange;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;

public class DefaultButtons {
    public static InlineKeyboardMarkup setButtons() {

        InlineKeyboardButton buttonRate = InlineKeyboardButton
                .builder()
                .text("Отримати курс 📊")
                .callbackData(DefaultMenu.GET_RATE.name())
                .build();

        InlineKeyboardButton buttonSettings = InlineKeyboardButton
                .builder()
                .text("Налаштування ⚙️")
                .callbackData(DefaultMenu.SETTINGS.name())
                .build();

        return InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(Collections.singletonList(buttonRate)))
                .keyboard(Collections.singletonList(Collections.singletonList(buttonSettings)))
                .build();
    }
}
