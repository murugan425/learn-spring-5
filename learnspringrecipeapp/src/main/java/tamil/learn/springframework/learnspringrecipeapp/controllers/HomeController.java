/* Created by Murugan_Nagarajan on 9/24/2017 */
package tamil.learn.springframework.learnspringrecipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping({"","/","/index"})
    public String getIndexPage(){
        System.out.println("Testing the LIVE RELOAD of SPRING..,");
        return "index";
    }
}
