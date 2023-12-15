package initialization;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

public class StartCommand extends BotCommand {

    public StartCommand() {
        super("start", "Start command");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String text =
                "Привіт! Тебе вітає ботик-знайка курсу валют! Я знаю все про поточний реальний курс головних валют до рідної гривні у світі!";
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(chat.getId());
        message.setReplyMarkup(StartButtons.setButtons());
        SendPhoto photo = new SendPhoto();
        InputFile inputFile = new InputFile();
        inputFile.setMedia(new File("src/main/java/initialization/image/photo.png"));
        photo.setPhoto(inputFile);
        photo.setChatId(chat.getId());

        try {
            absSender.execute(photo);
            absSender.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
