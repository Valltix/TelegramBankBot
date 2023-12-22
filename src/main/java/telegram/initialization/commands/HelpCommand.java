package telegram.initialization.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.initialization.BotConstants;
import telegram.menu.general.StartButtons;

import java.io.File;

import static telegram.menu.general.DescriptionButtonsMessage.botAbilities;

public class HelpCommand extends BotCommand {

    public HelpCommand() {
        super("help", "bot functionality description");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        SendMessage helpMessage = new SendMessage();
        InputFile inputFile = new InputFile();
        SendPhoto photo = new SendPhoto();
        SendChatAction action = new SendChatAction();

        botAbilities(helpMessage);
        helpMessage.setChatId(chat.getId());
        helpMessage.setReplyMarkup(StartButtons.setStart());
        inputFile.setMedia(new File(BotConstants.ABOUT_PATH));
        photo.setPhoto(inputFile);
        photo.setChatId(chat.getId());
        action.setChatId(chat.getId());
        action.setAction(ActionType.TYPING);

        try {
            absSender.execute(action);
            absSender.execute(photo);
            absSender.execute(helpMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
