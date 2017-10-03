/* Created by Murugan_Nagarajan on 9/24/2017 */
package tamil.learn.springframework.learnspringrecipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tamil.learn.springframework.learnspringrecipeapp.domain.Category;
import tamil.learn.springframework.learnspringrecipeapp.domain.UnitOfMeasure;
import tamil.learn.springframework.learnspringrecipeapp.services.RecipeService;

import java.util.Optional;

@Controller
public class HomeController {

    private RecipeService recipeService;

    public HomeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(Model model){

        Optional<UnitOfMeasure> unitOfMeasureOptional = recipeService.findUOMByDescription("Ounce");
        Optional<Category> categoryOptional = recipeService.findCategoryByName("Chinese");

        System.out.println("UOM Id is " + unitOfMeasureOptional.get().getId());
        System.out.println("Category Id is " + categoryOptional.get().getId());
        System.out.println("Category Name is " + categoryOptional.get().getCategoryname());
        System.out.println("Testing the LIVE RELOAD of SPRING..,");

        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "index";
    }

    @RequestMapping({"/getrecipes"})
    public String getAllRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "recipes";
    }
}
