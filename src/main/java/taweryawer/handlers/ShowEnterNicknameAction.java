package taweryawer.handlers;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.statemachine.UserEvents;
import taweryawer.statemachine.UserStates;

@Component
public class ShowEnterNicknameAction implements Action<UserStates, UserEvents> {

    @Override
    public void execute(StateContext<UserStates, UserEvents> stateContext) {
        final DefaultAbsSender bot = (DefaultAbsSender) stateContext.getMessage().getHeaders().get("bot");
        final Update update = (Update) stateContext.getMessage().getHeaders().get("update");
        System.out.println(stateContext.getStateMachine().getState());
        try {
            bot.execute(new SendMessage(update.getMessage().getFrom().getId().toString(), "Hi, I can help you to easily find teammates for worldwide raid invites. But first, let's create a profile for you, so enter your in-game nickname please."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
