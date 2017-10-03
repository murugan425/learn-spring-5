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


        greenCorianderRecipe.addIngredient(new Ingredient("Brown Rice",new BigDecimal(1) , cupUOMOptional.get()));
        greenCorianderRecipe.addIngredient(new Ingredient("Green Capsicum",new BigDecimal(1),largeUOMOptional.get()));
        greenCorianderRecipe.addIngredient(new Ingredient("Onion",new BigDecimal(1),largeUOMOptional.get()));
        greenCorianderRecipe.addIngredient(new Ingredient("Ginger Garlic Paste",new BigDecimal(1),teaSpoonUOMOptional.get()));
        greenCorianderRecipe.addIngredient(new Ingredient("Curd",new BigDecimal(0.5),ounceUOMOptional.get()));
        greenCorianderRecipe.addIngredient(new Ingredient("Garam Masala",new BigDecimal(1),teaSpoonUOMOptional.get()));
        greenCorianderRecipe.addIngredient(new Ingredient("Cumin Seeds",new BigDecimal(0.5),teaSpoonUOMOptional.get()));
        greenCorianderRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(1),teaSpoonUOMOptional.get()));
        greenCorianderRecipe.addIngredient( new Ingredient("Coriander Powder", new BigDecimal(0.5),teaSpoonUOMOptional.get()));

        /*
        greenCorianderRecipe.getIngredients().add(new Ingredient("Brown Rice",new BigDecimal(1) , cupUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(new Ingredient("Green Capsicum",new BigDecimal(1),largeUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(new Ingredient("Onion",new BigDecimal(1),largeUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(new Ingredient("Ginger Garlic Paste",new BigDecimal(1),teaSpoonUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(new Ingredient("Curd",new BigDecimal(0.5),ounceUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(new Ingredient("Garam Masala",new BigDecimal(1),teaSpoonUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(new Ingredient("Cumin Seeds",new BigDecimal(0.5),teaSpoonUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(new Ingredient("Olive Oil", new BigDecimal(1),teaSpoonUOMOptional.get(), greenCorianderRecipe));
        greenCorianderRecipe.getIngredients().add(new Ingredient("Coriander Powder", new BigDecimal(0.5),teaSpoonUOMOptional.get(), greenCorianderRecipe));
        */

        Notes greenCorianderNotes = new Notes();
        greenCorianderNotes.setNotes("" +
                "This brown rice recipe is loaded with fiber, minerals and vitamins like B6" +
                " and niacin. Not just that, you get a dash of protein and the crunchy capsicum provides you" +
                " with antioxidants like Vitamin C");
        //greenCorianderNotes.setRecipe(greenCorianderRecipe);
        greenCorianderRecipe.setNotes(greenCorianderNotes);

        greenCorianderRecipe.getCategories().add(categoryRepository.findByCategoryname("Indian").get());


        Recipe chickenBiryani = new Recipe();
        chickenBiryani.setDescription("Biryani Rice Recipe");
        chickenBiryani.setCookTime(60);
        chickenBiryani.setPrepTime(20);
        chickenBiryani.setDifficulty(Difficulty.HARD);
        chickenBiryani.setDirections("Step 1"
                + " First, in order to marinate the chicken, take a large bowl, put greek yogurt, turmeric chili powder" +
                " along with salt according one's taste. Then, add the chicken thighs in the mixture and keep aside for " +
                "about 20-30 minutes so that the yogurt mixture is properly absorbed by the chicken. Also, soak saffron " +
                "in the milk and keep aside. "
                + "Step 2"
                + "In the meanwhile, pour refined oil in a deep-bottomed pan, keeping it on medium flame. " +
                "Fry cumin seeds and green cardamom for about 2 minutes. Once done, immediately add the sliced onion " +
                "and fry for 15 minutes straight. Make sure you don't burn it, so when the onion starts to get brown " +
                "in color, add tomatoes and tomato puree and fry for another 5 minutes."
                + "Step 3"
                + "Next, add the slit green chilies to the mixture along with ginger-garlic paste, frying the mixture " +
                "yet again for a minute. Then, add coriander powder, turn the flame to medium low, add the marinated " +
                "chicken and mix for a while so that the ingredients absorb the juices properly."
                + "Step 4"
                + "Turn the flame to medium again and heat-through for about 5-6 minutes only to turn it over to low " +
                "heat. Cover with a lid and let simmer for 5 minutes. Make sure to keep stirring during the entire " +
                "process, else the chicken might stick to the bottom, eventually ending up burnt. You can add little " +
                "water if you find the consistency too thick."
                + "Step 5"
                + "Once done, turn off the flame, take the boiled rice, putting half of its quantity in the pan, " +
                "sprinkle milk soaked saffron along with garam masala, mint and coriander. Put the remaining rice over " +
                "and garnish with the same mentioned four ingredients."
                + "Step 6"
                + "Lastly, cover the lid, turn the flame to low medium and let simmer for about 5 minutes. Once done, " +
                "put it off and let the biryani stay covered for about another 10 minutes. Serve hot, along with raita" +
                " or any chutney of your choice.");


        chickenBiryani.addIngredient(new Ingredient("Boiled Basmati rice", new BigDecimal(3), cupUOMOptional.get()));
        chickenBiryani.addIngredient(new Ingredient("Minced Mint Leaves", new BigDecimal(4), teaSpoonUOMOptional.get()));
        chickenBiryani.addIngredient(new Ingredient("Onion", new BigDecimal(2), largeUOMOptional.get()));
        chickenBiryani.addIngredient(new Ingredient("Ginger Garlic Paste", new BigDecimal(1), tableSpoonUOMOptional.get()));
        chickenBiryani.addIngredient(new Ingredient("Chicken Thighs", new BigDecimal(1), ounceUOMOptional.get()));
        chickenBiryani.addIngredient(new Ingredient("Garam Masala", new BigDecimal(1), teaSpoonUOMOptional.get()));
        chickenBiryani.addIngredient(new Ingredient("Cumin Seeds", new BigDecimal(0.5), teaSpoonUOMOptional.get()));
        chickenBiryani.addIngredient(new Ingredient("Olive Oil", new BigDecimal(1), teaSpoonUOMOptional.get()));
        chickenBiryani.addIngredient(new Ingredient("Coriander Powder", new BigDecimal(2), teaSpoonUOMOptional.get()));

        Notes chickenBiryaniNotes = new Notes();
        chickenBiryaniNotes.setNotes("Make this scrumptious Chicken Biryani and see the heads turning your way as the " +
                "aroma of this delectable dish will be all over your house. Biryani, which when cooked perfectly can " +
                "turn into a dish par excellence. Loved by all food lovers ");
        chickenBiryani.setNotes(chickenBiryaniNotes);

        chickenBiryani.getCategories().add(categoryRepository.findByCategoryname("Indian").get());

        recipes.add(chickenBiryani);
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
