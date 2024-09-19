package mk.ukim.finki.rideshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RideShareApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideShareApplication.class, args);
    }

}
