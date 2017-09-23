/* Created by murugan_nagarajan on 9/19/2017 */
package tamil.learn.springframework.learnspringdi.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import tamil.learn.springframework.learnspringdi.services.GreetingService;

@Controller
public class ConstructorInjectController {

    private GreetingService greetingService;

    //@Autowired - is optional in case of Constructor based Injection - Only after Spring-4.2
    //There is one @PrimaryBean available, so even if the Qualifier is missing, no issues, the Primary Bean is executed.
    public ConstructorInjectController(@Qualifier("constructorGreetingServiceImpl") GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public GreetingService getGreetingService() {
        return this.greetingService;
    }

    public String sayHello() {
        return getGreetingService().sayGreeting();
    }
}
