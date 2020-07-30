package taweryawer.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import taweryawer.service.UserService;
import taweryawer.statemachine.ActionFailedException;
import taweryawer.statemachine.UserEvents;
import taweryawer.statemachine.UserStates;

@Component
public class PersistNicknameAction implements Action<UserStates, UserEvents> {

    @Autowired
    private UserService userService;

    @Override
    public void execute(StateContext<UserStates, UserEvents> context) {
        final DefaultAbsSender bot = (DefaultAbsSender) context.getMessage().getHeaders().get("bot");
        final Update update = (Update) context.getMessage().getHeaders().get("update");
        String text = update.getMessage().getText();
        String telegramId = update.getMessage().getFrom().getId().toString();
        try {
            userService.changeUserNickname(text, telegramId);
            bot.execute(new SendMessage(telegramId, "You've successfully set your name to " + text + ".\nNow, plese enter your friend code without whitespaces."));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionFailedException();
        }
    }
}
