package tamil.learn.springframework.learnspringdi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import tamil.learn.springframework.learnspringdi.controllers.ConstructorInjectController;
import tamil.learn.springframework.learnspringdi.controllers.FirstController;
import tamil.learn.springframework.learnspringdi.controllers.PropertyInjectController;
import tamil.learn.springframework.learnspringdi.controllers.SetterInjectController;

@SpringBootApplication
@ComponentScan(basePackages = {"tamil.learn.springframework"})
//Optional in this case, use only if you use a package outside the package of the SpringBootApplication
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
