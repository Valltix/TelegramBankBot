package initialization;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StartCommand extends BotCommand {

    public StartCommand() {
        super("start", "Start command");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String text = "Вітаю тебе на ринку валют! Чи хочеш дізнатись скільки доларів чи євро дають за рідну гривню?";
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(chat.getId());
        message.setReplyMarkup(StartButtons.setButtons());
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
