package taweryawer.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import taweryawer.service.KeyboardService;
import taweryawer.service.UserService;
import taweryawer.statemachine.ActionFailedException;
import taweryawer.statemachine.UserEvents;
import taweryawer.statemachine.UserStates;
import taweryawer.statemachine.WrongFriendCodeFormatException;

@Component
public class PersistFriendCodeAction implements Action<UserStates, UserEvents> {

    @Autowired
    UserService userService;

    @Autowired
    KeyboardService keyboardService;

    @Override
    public void execute(StateContext<UserStates, UserEvents> context) {
        try {
            final DefaultAbsSender bot = (DefaultAbsSender) context.getMessage().getHeaders().get("bot");
            final Update update = (Update) context.getMessage().getHeaders().get("update");
            String text = update.getMessage().getText();
            String telegramId = update.getMessage().getFrom().getId().toString();
            if (text.matches("\\d{12}")) {
                userService.changeUserFriendCode(text, telegramId);
                SendMessage message = new SendMessage(telegramId, "You've successfully set your friend code to " + text + "\nYou're all ready! Make sure to set all your preferences in settings tab, good luck");
                message.setReplyMarkup(keyboardService.getMainKeyboard());
                bot.execute(message);
            } else {
                bot.execute(new SendMessage(telegramId, "Your friend code is in wrong format, make sure it's 12 letters without whitespaces!"));
                throw new WrongFriendCodeFormatException();
            }
        } catch( TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

