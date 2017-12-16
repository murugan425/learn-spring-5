/* Created by Murugan_Nagarajan on 9/14/2017 */
package tamil.learn.springframework.learnspringwebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tamil.learn.springframework.learnspringwebapp.repository.AuthorRepository;

@Controller
public class AuthorController {
    private AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @RequestMapping("/authors")
    public String getAuthors(Model model) {
        model.addAttribute("authors",authorRepository.findAll());
        return "authors";
    }
}
