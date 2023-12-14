package initialization;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.Collections;

public class StartButtons {
    public static InlineKeyboardMarkup setButtons(){

        InlineKeyboardButton buttonStart = InlineKeyboardButton
                .builder()
                .text("НЕОБМІННО ХОЧУ!")
                .callbackData("1")
                .build();

        InlineKeyboardButton buttonHelp = InlineKeyboardButton
                .builder()
                .text("ДІЗНАТИСЬ БІЛЬШЕ")
                .callbackData("2")
                .build();

        InlineKeyboardMarkup build = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(Collections.singletonList(buttonStart)))
                .keyboard(Collections.singletonList(Collections.singletonList(buttonHelp)))
                .build();
        return build;
    }
}
