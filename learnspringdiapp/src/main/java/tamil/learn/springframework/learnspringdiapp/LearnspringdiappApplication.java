package tamil.learn.springframework.learnspringdiapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:chuck-config.xml")
public class LearnspringdiappApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnspringdiappApplication.class, args);
	}
}
