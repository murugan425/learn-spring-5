/* Created by murugan_nagarajan on 9/18/2017 */
package tamil.learn.springframework.learnspringdi.controllers;

import org.springframework.stereotype.Controller;
import tamil.learn.springframework.learnspringdi.services.GreetingService;

@Controller
public class FirstController {

    private GreetingService greetingService;

    public String sayHello() {
        System.out.println("HELLO !!!!!!!");
        return greetingService.sayGreeting();
    }

    //There is one @PrimaryBean available, so even if the Qualifier is missing, no issues, the Primary Bean is executed.
    public FirstController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }
}
