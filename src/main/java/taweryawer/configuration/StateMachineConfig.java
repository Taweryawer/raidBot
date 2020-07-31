package taweryawer.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import taweryawer.handlers.ErrorAction;
import taweryawer.handlers.PersistNicknameAction;
import taweryawer.service.ActionFactory;
import taweryawer.statemachine.UserEvents;
import taweryawer.statemachine.UserStates;
import taweryawer.handlers.ShowEnterNicknameAction;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<UserStates, UserEvents> {

    @Autowired
    private StateMachineRuntimePersister<UserStates, UserEvents, String> stateMachineRuntimePersister;
    @Autowired
    private ActionFactory actionFactory;

    @Override
    public void configure(StateMachineStateConfigurer<UserStates, UserEvents> states) throws Exception {
        states
                .withStates()
                .initial(UserStates.INITIAL)
                .states(EnumSet.allOf(UserStates.class));
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<UserStates, UserEvents> config) throws Exception {
        config
                .withConfiguration()
                    .autoStartup(false)
                .and()
                .withPersistence()
                    .runtimePersister(stateMachineRuntimePersister);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<UserStates, UserEvents> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(UserStates.INITIAL).target(UserStates.ENTERING_NICKNAME).event(UserEvents.USER_CREATED).action(actionFactory.showEnterNicknameAction())
                    .and()
                .withExternal()
                    .source(UserStates.ENTERING_NICKNAME).target(UserStates.ENTERING_CODE).event(UserEvents.MESSAGE).action(actionFactory.persistNicknameAction(), actionFactory.errorAction())
                    .and()
                .withExternal()
                    .source(UserStates.ENTERING_CODE).target(UserStates.NORMAL).event(UserEvents.MESSAGE).action(actionFactory.friendCodeAction(), actionFactory.errorAction())
                    .and()
                .withInternal()
                    .source(UserStates.NORMAL).event(UserEvents.MESSAGE).action(actionFactory.messageAction(), actionFactory.errorAction())
                    .and()
                .withInternal()
                    .source(UserStates.NORMAL).event(UserEvents.PROFILE).action(actionFactory.profileAction(), actionFactory.errorAction())
                    .and()
                .withInternal()
                    .source(UserStates.NORMAL).event(UserEvents.USER_CREATED).action(actionFactory.showMainKeyboardAction(), actionFactory.errorAction());
    }


    @Bean
    public StateMachineRuntimePersister<UserStates, UserEvents, String> stateMachineRuntimePersister(JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }
}
