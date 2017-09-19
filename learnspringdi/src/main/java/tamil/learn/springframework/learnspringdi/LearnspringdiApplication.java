package tamil.learn.springframework.learnspringdi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import tamil.learn.springframework.learnspringdi.controllers.FirstController;

@SpringBootApplication
public class LearnspringdiApplication {

	public static void main(String[] args) {

		ApplicationContext ctx =  SpringApplication.run(LearnspringdiApplication.class, args);

		//Always the bean name will be equivalent to the class name (but starts with lowercase)
		FirstController controller = (FirstController) ctx.getBean("firstController");
		controller.sayHello();

	}
}
