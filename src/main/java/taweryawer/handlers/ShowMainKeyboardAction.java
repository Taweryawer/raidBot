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
import taweryawer.statemachine.UserEvents;
import taweryawer.statemachine.UserStates;

@Component
public class ShowMainKeyboardAction implements Action<UserStates, UserEvents> {

    @Autowired
    KeyboardService keyboardService;

    @Override
    public void execute(StateContext<UserStates, UserEvents> context) {
        final DefaultAbsSender bot = (DefaultAbsSender) context.getMessage().getHeaders().get("bot");
        final Update update = (Update) context.getMessage().getHeaders().get("update");
        String telegramId = update.getMessage().getFrom().getId().toString();
        SendMessage message = new SendMessage(telegramId, "Menu");
        message.setReplyMarkup(keyboardService.getMainKeyboard());
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
