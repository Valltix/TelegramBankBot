package initialization;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class StartButtons {
    public static InlineKeyboardMarkup setButtons() {

        InlineKeyboardButton buttonStart = InlineKeyboardButton
                .builder()
                .text("Перейти до курсу валют \uD83D\uDCB8")
                .callbackData("1")
                .build();

        InlineKeyboardButton buttonUAH = InlineKeyboardButton
                .builder()
                .text("Про гривню \uD83C\uDDFA\uD83C\uDDE6")
                .callbackData("2")
                .build();

        InlineKeyboardButton buttonHelp = InlineKeyboardButton
                .builder()
                .text("Що ще може бот-знайка \uD83E\uDD13")
                .callbackData("3")
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
