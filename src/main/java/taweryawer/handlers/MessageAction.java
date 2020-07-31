package taweryawer.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.service.MessageBuilderBuilder;
import taweryawer.statemachine.UserEvents;
import taweryawer.statemachine.UserStates;

@Component
public class MessageAction implements Action<UserStates, UserEvents> {

    @Autowired
    MessageBuilderBuilder<UserEvents> messageBuilderBuilder;

    @Override
    public void execute(StateContext<UserStates, UserEvents> context) {
        final Update update = (Update) context.getMessage().getHeaders().get("update");
        final DefaultAbsSender bot = (DefaultAbsSender) context.getMessage().getHeaders().get("bot");
        String text = update.getMessage().getText();

        messageBuilderBuilder.addHeader("update", update);
        messageBuilderBuilder.addHeader("bot", bot);

        switch (text) {
            case "Profile\uD83D\uDC64" : {
                context.getStateMachine().sendEvent(messageBuilderBuilder.build(UserEvents.PROFILE));
            }
        }
    }
}
