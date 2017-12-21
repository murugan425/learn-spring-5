/* Created by Murugan_Nagarajan on 9/28/2017 */
package tamil.learn.springframework.learnspringrecipeapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tamil.learn.springframework.learnspringrecipeapp.domain.Category;
import tamil.learn.springframework.learnspringrecipeapp.domain.Recipe;
import tamil.learn.springframework.learnspringrecipeapp.domain.UnitOfMeasure;
import tamil.learn.springframework.learnspringrecipeapp.repositories.CategoryRepository;
import tamil.learn.springframework.learnspringrecipeapp.repositories.RecipeRepository;
import tamil.learn.springframework.learnspringrecipeapp.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                             RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Optional<UnitOfMeasure> findUOMByDescription(String uomDesc) {
        return unitOfMeasureRepository.findByDescription(uomDesc);
    }

    @Override
    public Optional<Category> findCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryname(categoryName);
    }

    @Override
    public Set<Recipe> getAllRecipes() {
        log.debug(this.getClass().getSimpleName());
        Set<Recipe> recipes = new HashSet<Recipe>();
        recipeRepository.findAll(sortByIdAsc()).iterator().forEachRemaining(recipes::add);
        log.debug(recipes.toString());
        return recipes;
    }

    public Recipe getRecipeById(Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe Not Found.");
        }
        return recipeOptional.get();
    }

    private Sort sortByIdAsc() {
        return new Sort(Sort.Direction.ASC, "id");
    }

    private Sort sortByIdDesc() {
        return new Sort(Sort.Direction.DESC, "id");
    }
}
