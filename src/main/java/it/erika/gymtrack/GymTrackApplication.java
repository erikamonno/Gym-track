package it.erika.gymtrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GymTrackApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymTrackApplication.class, args);
    }
}
