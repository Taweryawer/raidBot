package taweryawer.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import taweryawer.entities.User;
import taweryawer.service.UserService;
import taweryawer.statemachine.UserEvents;
import taweryawer.statemachine.UserStates;

@Component
public class ProfileAction implements Action<UserStates, UserEvents> {

    @Autowired
    UserService userService;

    @Override
    public void execute(StateContext<UserStates, UserEvents> context) {
        final Update update = (Update) context.getMessage().getHeaders().get("update");
        final DefaultAbsSender bot = (DefaultAbsSender) context.getMessage().getHeaders().get("bot");
        String telegramId = update.getMessage().getFrom().getId().toString();
        User user = userService.getUser(telegramId);

        StringBuilder sb = new StringBuilder("*Your profile*\uD83D\uDC64: \n\n");
        sb.append("*Nickname*: " + user.getName() + "\n");
        sb.append("*Friend code*: " + user.getFriendCode() + "\n");

        try {
            SendMessage message = new SendMessage(telegramId, sb.toString());
            message.setParseMode(ParseMode.MARKDOWNV2);
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
