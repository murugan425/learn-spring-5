package tamil.learn.springframework.learnspringdi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import tamil.learn.springframework.learnspringdi.controllers.ConstructorInjectController;
import tamil.learn.springframework.learnspringdi.controllers.FirstController;
import tamil.learn.springframework.learnspringdi.controllers.PropertyInjectController;
import tamil.learn.springframework.learnspringdi.controllers.SetterInjectController;

@SpringBootApplication
public class LearnspringdiApplication {

	public static void main(String[] args) {

		ApplicationContext ctx =  SpringApplication.run(LearnspringdiApplication.class, args);

		//Always the bean name will be equivalent to the class name (but starts with lowercase)
		FirstController controller = (FirstController) ctx.getBean("firstController");
		System.out.println(controller.sayHello());
		System.out.println(ctx.getBean(PropertyInjectController.class).sayHello());
		System.out.println(ctx.getBean(SetterInjectController.class).sayHello());
		System.out.println(ctx.getBean(ConstructorInjectController.class).sayHello());
	}
}
