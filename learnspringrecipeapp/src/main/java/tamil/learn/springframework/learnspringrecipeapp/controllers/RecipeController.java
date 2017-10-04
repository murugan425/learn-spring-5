/* Created by Murugan_Nagarajan on 9/24/2017 */
package tamil.learn.springframework.learnspringrecipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tamil.learn.springframework.learnspringrecipeapp.services.RecipeService;

@Controller
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/recipe/view/{id}"})
    public String getRecipeById(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        return "showrecipe";
    }
}
