package telegram.menu.general;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.menu.Menu;

import java.util.*;

public class StartButtons {
    public static InlineKeyboardMarkup setButtons() {

        InlineKeyboardButton buttonStart = InlineKeyboardButton
                .builder()
                .text("Перейти до курсу валют \uD83D\uDCB8")
                .callbackData(Menu.CURRENCY_RATE.name())
                .build();

        InlineKeyboardButton buttonUAH = InlineKeyboardButton
                .builder()
                .text("Про гривню \uD83C\uDDFA\uD83C\uDDE6")
                .callbackData(Menu.ABOUT_UAH.name())
                .build();

        InlineKeyboardButton buttonHelp = InlineKeyboardButton
                .builder()
                .text("Що ще може бот-знайка \uD83E\uDD13")
                .callbackData(Menu.BOT_ABILITIES.name())
                .build();

        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(Collections.singletonList(buttonStart)))
                .keyboard(Collections.singletonList(Collections.singletonList(buttonUAH)))
                .keyboard(Collections.singletonList(Collections.singletonList(buttonHelp)))
                .build();
        return keyboard;
    }
}
