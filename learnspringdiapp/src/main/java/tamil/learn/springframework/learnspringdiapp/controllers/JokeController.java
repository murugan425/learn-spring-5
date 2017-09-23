/* Created by Murugan_Nagarajan on 9/21/2017 */
package tamil.learn.springframework.learnspringdiapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tamil.learn.springframework.learnspringdiapp.services.JokeService;

@Controller
public class JokeController {

    private JokeService jokeService;

    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @RequestMapping({"/", ""})
    public String showJoke(Model model) {
        model.addAttribute("joke", jokeService.getJoke());
        return "showjoke";
    }
}
