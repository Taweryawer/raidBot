package taweryawer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.data.jpa.JpaRepositoryStateMachinePersist;
import org.springframework.stereotype.Service;
import taweryawer.service.StateMachineService;
import taweryawer.statemachine.UserEvents;
import taweryawer.statemachine.UserStates;

@Service
public class StateMachineServiceImpl implements StateMachineService {

    private JpaRepositoryStateMachinePersist<UserStates, UserEvents> jpaRepositoryStateMachinePersist;
    private final StateMachineFactory<UserStates, UserEvents> stateMachineFactory;

    @Autowired
    public StateMachineServiceImpl(JpaRepositoryStateMachinePersist<UserStates, UserEvents> jpaRepositoryStateMachinePersist, StateMachineFactory<UserStates, UserEvents> stateMachineFactory) {
        this.jpaRepositoryStateMachinePersist = jpaRepositoryStateMachinePersist;
        this.stateMachineFactory = stateMachineFactory;
    }


    @Override
    public StateMachine<UserStates, UserEvents> getStateMachine(String userId) throws Exception {
        StateMachine<UserStates, UserEvents> sm = createStateMachine(userId);
        if (sm == null) {
            return null;
        }
        StateMachineContext<UserStates, UserEvents> smc = jpaRepositoryStateMachinePersist.read(userId);
        if (smc != null) {
            sm.getStateMachineAccessor()
                    .doWithAllRegions(sma -> sma.resetStateMachine(smc));
        }
        sm.start();
        return sm;
    }

    public StateMachine<UserStates, UserEvents> createStateMachine(String userId) {
        if (userId != null) {
            return stateMachineFactory.getStateMachine(userId);
        } else return null;
    }


}
