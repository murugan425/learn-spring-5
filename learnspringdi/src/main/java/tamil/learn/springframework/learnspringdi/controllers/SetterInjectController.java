/* Created by murugan_nagarajan on 9/19/2017 */
package tamil.learn.springframework.learnspringdi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import tamil.learn.springframework.learnspringdi.services.GreetingService;

@Controller
public class SetterInjectController {

    private GreetingService greetingService;

    @Autowired
    //@Qualifier("setterGreetingServiceImpl") - Qualifier can be mentioned at Method level or as Method Inputs also
    public void setGreetingService(@Qualifier("setterGreetingServiceImpl") GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public GreetingService getGreetingService() {
        return this.greetingService;
    }

    public String sayHello() {
        return getGreetingService().sayGreeting();
    }
}
