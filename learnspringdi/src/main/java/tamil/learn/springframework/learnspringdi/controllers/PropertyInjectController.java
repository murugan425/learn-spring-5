/* Created by murugan_nagarajan on 9/19/2017 */
package tamil.learn.springframework.learnspringdi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import tamil.learn.springframework.learnspringdi.services.GreetingService;

@Controller
public class PropertyInjectController {

    @Autowired
    @Qualifier("propertyGreetingServiceImpl")
    //If the property name matches the qualifier name then the qualifier can be ignored
    //Try to use propertyGreetingServiceImpl
    //public GreetingService propertyGreetingServiceImpl;
    public GreetingService greetingService;

    public GreetingService getGreetingService() {
        return this.greetingService;
    }

    public String sayHello() {
        return getGreetingService().sayGreeting();
    }
}
