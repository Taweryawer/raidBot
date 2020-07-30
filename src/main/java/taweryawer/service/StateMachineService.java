package taweryawer.service;

import org.springframework.statemachine.StateMachine;
import taweryawer.statemachine.UserEvents;
import taweryawer.statemachine.UserStates;

public interface StateMachineService {

    public StateMachine<UserStates, UserEvents> getStateMachine(String userId) throws Exception;
}
