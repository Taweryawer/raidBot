package taweryawer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.telegram.telegrambots.ApiContextInitializer;

import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException {
        ApiContextInitializer.init();
        SpringApplication.run(Main.class, args);
    }
}
