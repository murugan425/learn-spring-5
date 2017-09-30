/* Created by Murugan_Nagarajan on 9/28/2017 */
package tamil.learn.springframework.learnspringrecipeapp.services;

import tamil.learn.springframework.learnspringrecipeapp.domain.Category;
import tamil.learn.springframework.learnspringrecipeapp.domain.Recipe;
import tamil.learn.springframework.learnspringrecipeapp.domain.UnitOfMeasure;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {

    public Optional<UnitOfMeasure> findUOMByDescription(String uomDesc);
    public Optional<Category> findCategoryByName(String categoryName);
    public Set<Recipe> getAllRecipes();
}
