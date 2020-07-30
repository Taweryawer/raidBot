package taweryawer.configuration;

import com.esotericsoftware.kryo.Kryo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.statemachine.data.jpa.JpaRepositoryStateMachinePersist;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import taweryawer.service.MessageBuilderBuilder;
import taweryawer.statemachine.UserEvents;
import taweryawer.statemachine.UserStates;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@EnableTransactionManagement
public class BeansConfig {

    @Autowired
    private JpaStateMachineRepository jpaStateMachineRepository;

    @Bean
    public Kryo kryo() {
        return new Kryo();
    }

    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("raidbot");
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

    @Bean
    public JpaRepositoryStateMachinePersist<UserStates, UserEvents> jpaRepositoryStateMachinePersist() {
        return new JpaRepositoryStateMachinePersist<>(jpaStateMachineRepository);
    }

    @Bean
    @Scope("prototype")
    public MessageBuilderBuilder<UserEvents> messageBuilderBuilder() {
        return new MessageBuilderBuilder<>();
    }

}
