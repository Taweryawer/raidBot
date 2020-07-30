package taweryawer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.service.MessageBuilderBuilder;
import taweryawer.statemachine.UserEvents;
import taweryawer.statemachine.UserStates;
import taweryawer.service.StateMachineService;
import taweryawer.service.UserService;

import java.util.Arrays;

@Component
public class RaidBot extends TelegramLongPollingBot {

    private StateMachineService stateMachineService;
    private UserService userService;
    private MessageBuilderBuilder<UserEvents> messageBuilderBuilder;
    private Log log = LogFactory.getLog(RaidBot.class);

    @Autowired
    public RaidBot(StateMachineService stateMachineService, UserService userService, MessageBuilderBuilder<UserEvents> messageBuilderBuilder) {
        this.stateMachineService = stateMachineService;
        this.userService = userService;
        this.messageBuilderBuilder = messageBuilderBuilder;
    }

    public void onUpdateReceived(Update update)  {
        try {
            if (update.hasMessage()) {
                StateMachine<UserStates, UserEvents> stateMachine = stateMachineService.getStateMachine(update.getMessage().getFrom().getId().toString());
                messageBuilderBuilder
                        .addHeader("bot", this)
                        .addHeader("update", update);

                if (update.getMessage().getText().equals("/start")) {
                    userService.getUser(update.getMessage().getFrom().getId().toString());
                    Message<UserEvents> message = messageBuilderBuilder.build(UserEvents.USER_CREATED);
                    stateMachine.sendEvent(message);
                } else {
                    stateMachine.sendEvent(messageBuilderBuilder.build(UserEvents.MESSAGE));
                }
            }
        } catch (Exception e) {
            log.error("Oh! Something went really wrong!\n" + update);
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return System.getenv("BOT_USERNAME") == null ? "raidinvites_bot" : System.getenv("BOT_USERNAME");
    }

    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }
}
