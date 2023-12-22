package telegram.initialization.commands;

import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import telegram.initialization.BotConstants;
import telegram.menu.general.StartButtons;
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
                "Привіт! Тебе вітає ботик-знайка курсу валют! Я знаю все про поточний курс головних валют у світі до рідної гривні!";
        SendMessage message = new SendMessage();
        SendPhoto photo = new SendPhoto();
        InputFile inputFile = new InputFile();
        SendChatAction action = new SendChatAction();

        message.setText(text);
        message.setChatId(chat.getId());
        message.setReplyMarkup(StartButtons.setButtons());
        inputFile.setMedia(new File(BotConstants.HELLO_PATH));
        photo.setPhoto(inputFile);
        photo.setChatId(chat.getId());
        action.setChatId(chat.getId());
        action.setAction(ActionType.TYPING);

        try {
            absSender.execute(action);
            absSender.execute(photo);
            absSender.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
