/* Created by murugan_nagarajan on 9/18/2017 */
package tamil.learn.springframework.learnspringdi.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class FirstController {

    public String sayHello() {
        System.out.println("HELLO !!!!!!!");
        return "hello";
    }
}
