/* Created by Murugan_Nagarajan on 9/28/2017 */
package tamil.learn.springframework.learnspringrecipeapp.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import tamil.learn.springframework.learnspringrecipeapp.domain.Ingredient;
import tamil.learn.springframework.learnspringrecipeapp.domain.Notes;
import tamil.learn.springframework.learnspringrecipeapp.domain.Recipe;
import tamil.learn.springframework.learnspringrecipeapp.domain.UnitOfMeasure;
import tamil.learn.springframework.learnspringrecipeapp.enums.Difficulty;
import tamil.learn.springframework.learnspringrecipeapp.repositories.CategoryRepository;
import tamil.learn.springframework.learnspringrecipeapp.repositories.RecipeRepository;
import tamil.learn.springframework.learnspringrecipeapp.repositories.UnitOfMeasureRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(loadRecipes());
    }

    public List<Recipe> loadRecipes() {
        List<Recipe> recipes = new ArrayList<Recipe>();

        Optional<UnitOfMeasure> teaSpoonUOMOptional = generateUOM("Teaspoon");
        Optional<UnitOfMeasure> tableSpoonUOMOptional = generateUOM("Tablespoon");
        Optional<UnitOfMeasure> cupUOMOptional = generateUOM("Cup");
        Optional<UnitOfMeasure> ounceUOMOptional = generateUOM("Ounce");
        Optional<UnitOfMeasure> pinchUOMOptional = generateUOM("Pinch");
        Optional<UnitOfMeasure> sliceUOMOptional = generateUOM("Slice");
        Optional<UnitOfMeasure> clovesUOMOptional = generateUOM("Cloves");
        Optional<UnitOfMeasure> glassUOMOptional = generateUOM("Glass");
        Optional<UnitOfMeasure> largeUOMOptional = generateUOM("Large");

        Recipe greenCorianderRecipe = new Recipe();
        greenCorianderRecipe.setDescription("Green Coriander Rice Recipe");
        greenCorianderRecipe.setCookTime(45);
        greenCorianderRecipe.setPrepTime(15);
        greenCorianderRecipe.setDifficulty(Difficulty.EASY);
        greenCorianderRecipe.setDirections("" +
                "In a non-stick vessel, heat oil and add cumin seeds.Once the seeds crackle, add the bay leaves, " +
                "stir gently and add chopped onions. Cook the onions till they turn brown.Now add ginger garlic paste," +
                " coriander mint paste and curd to the vessel and cook well. Once the masala is cooked well, you will" +
                " start getting a lovely aroma and this is the correct time to stir in the dry masalas.Cook for " +
                "5-6 minutes, time to add the crunchy capsicum. Once done, add the rice and 1 1/2 cups of water." +
                "Cover the vessel with a lid and let it cook for 15 minutes or till the rice is done.Garnish with some" +
                " coriander and serve fresh");

        greenCorianderRecipe.getIngredients().add(
                new Ingredient("Brown Rice",new BigDecimal(1) , cupUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(
                new Ingredient("Green Capsicum",new BigDecimal(1),largeUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(
                new Ingredient("Onion",new BigDecimal(1),largeUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(
                new Ingredient("Ginger Garlic Paste",new BigDecimal(1),teaSpoonUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(
                new Ingredient("Curd",new BigDecimal(0.5),ounceUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(
                new Ingredient("Garam Masala",new BigDecimal(1),teaSpoonUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(
                new Ingredient("Cumin Seeds",new BigDecimal(0.5),teaSpoonUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(
                new Ingredient("Olive Oil", new BigDecimal(1),teaSpoonUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(
                new Ingredient("Coriander Powder", new BigDecimal(0.5),teaSpoonUOMOptional.get(), greenCorianderRecipe));

        Notes greenCorianderNotes = new Notes();
        greenCorianderNotes.setNotes("" +
                "This brown rice recipe is loaded with fiber, minerals and vitamins like B6" +
                " and niacin. Not just that, you get a dash of protein and the crunchy capsicum provides you" +
                " with antioxidants like Vitamin C");
        greenCorianderNotes.setRecipe(greenCorianderRecipe);
        greenCorianderRecipe.setNotes(greenCorianderNotes);

        greenCorianderRecipe.getCategories().add(categoryRepository.findByCategoryname("Indian").get());

        recipes.add(greenCorianderRecipe);
        return recipes;
    }

    private Optional<UnitOfMeasure> generateUOM(String uomDescription) {
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription(uomDescription);
        if (!uomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM not found in DB .., Please Configure " + uomDescription);
        }
        return uomOptional;
    }
}
