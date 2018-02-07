package tamil.learn.springframework.learnspringrecipeapp.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import tamil.learn.springframework.learnspringrecipeapp.commands.CategoryCommand;
import tamil.learn.springframework.learnspringrecipeapp.commands.IngredientCommand;
import tamil.learn.springframework.learnspringrecipeapp.commands.NotesCommand;
import tamil.learn.springframework.learnspringrecipeapp.commands.RecipeCommand;
import tamil.learn.springframework.learnspringrecipeapp.converters.RecipeCommandToEntity;
import tamil.learn.springframework.learnspringrecipeapp.converters.RecipeEntityToCommand;
import tamil.learn.springframework.learnspringrecipeapp.domain.*;
import tamil.learn.springframework.learnspringrecipeapp.enums.Difficulty;
import tamil.learn.springframework.learnspringrecipeapp.repositories.CategoryRepository;
import tamil.learn.springframework.learnspringrecipeapp.repositories.RecipeRepository;
import tamil.learn.springframework.learnspringrecipeapp.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    private RecipeCommandToEntity recipeCommandToEntity;

    @Mock
    private RecipeEntityToCommand recipeEntityToCommand;

    private static final Long RECIPE_ID = 1L;
    private static final Integer COOK_TIME = Integer.valueOf("5");
    private static final Integer PREP_TIME = Integer.valueOf("7");
    private static final String DESCRIPTION = "Test Recipe";
    private static final String DIRECTIONS = "Test Recipe Directions";
    private static final Difficulty DIFFICULTY = Difficulty.EASY;
    private static final Integer SERVINGS = Integer.valueOf("3");
    private static final String SOURCE = "Source";
    private static final String URL = "Test URL";
    private static final Long CAT_ID_1 = 1L;
    private static final Long CAT_ID2 = 2L;
    private static final Long INGRED_ID_1 = 3L;
    private static final Long INGRED_ID_2 = 4L;
    private static final Long NOTES_ID = 9L;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(categoryRepository, unitOfMeasureRepository, recipeRepository,
                                recipeCommandToEntity, recipeEntityToCommand);
    }

    @Test
    public void findUOMByDescription() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(2L);
        uom.setDescription("Test UOM");
        Optional<UnitOfMeasure> uomData = Optional.of(uom);

        when(unitOfMeasureRepository.findByDescription(anyString())).thenReturn(uomData);

        Optional<UnitOfMeasure> uomDataReturned = recipeService.findUOMByDescription("Test UOM");
        assertNotNull(uomDataReturned);
        assertEquals("Test UOM", uomDataReturned.get().getDescription());
    }

    @Test
    public void getAllRecipes() {
        Recipe recipe = new Recipe();
        recipe.setId(2L);
        recipe.setPrepTime(10);
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(recipeRepository.findAll(any(Sort.class))).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getAllRecipes();
        recipes.iterator().forEachRemaining(recipeMocked -> System.out.println(recipeMocked));

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    public void getRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(2L);
        recipe.setPrepTime(10);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(2L)).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.getRecipeById(2L);

        assertNotNull("Not empty Recipe", recipeReturned);
        assertEquals(recipeReturned, recipe);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll(any(Sort.class));
    }

    @Test
    public void saveRecipe() {
        RecipeCommand recipeCommand = createRecipeCommand();
        Recipe recipe = createRecipe();

        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(recipeCommandToEntity.convert(any(RecipeCommand.class))).thenReturn(recipe);
        when(recipeEntityToCommand.convert(any(Recipe.class))).thenReturn(recipeCommand);

        RecipeCommand recipeCommandReturned = recipeService.saveRecipe(recipeCommand);

        assertEquals(recipeCommand.getDescription(), recipeCommandReturned.getDescription());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
        verify(recipeCommandToEntity, times(1)).convert(any(RecipeCommand.class));
        verify(recipeEntityToCommand, times(1)).convert(any(Recipe.class));
    }

    @Test
    public void getRecipeCommandById() {
        RecipeCommand recipeCommand = createRecipeCommand();
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setPrepTime(PREP_TIME);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeEntityToCommand.convert(any(Recipe.class))).thenReturn(recipeCommand);

        RecipeCommand recipeCommandReturned = recipeService.getRecipeCommandById(anyLong());
        assertEquals(recipeCommand.getDescription(), recipeCommandReturned.getDescription());
    }

    @Test
    public void deleteRecipeById() {
        Long recipeIdToDelete = RECIPE_ID;

        recipeService.deleteRecipeById(recipeIdToDelete);

        verify(recipeRepository, times(1)).deleteById(anyLong());
    }

    private RecipeCommand createRecipeCommand() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);

        NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);
        recipeCommand.setNotes(notes);

        CategoryCommand category = new CategoryCommand();
        category.setId(CAT_ID_1);
        CategoryCommand category2 = new CategoryCommand();
        category2.setId(CAT_ID2);
        recipeCommand.getCategories().add(category);
        recipeCommand.getCategories().add(category2);

        IngredientCommand ingredient = new IngredientCommand();
        ingredient.setId(INGRED_ID_1);
        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(INGRED_ID_2);
        recipeCommand.getIngredients().add(ingredient);
        recipeCommand.getIngredients().add(ingredient2);
        return recipeCommand;
    }

    private Recipe createRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);
        recipe.setNotes(notes);

        Category category = new Category();
        category.setId(CAT_ID_1);
        Category category2 = new Category();
        category2.setId(CAT_ID2);
        recipe.getCategories().add(category);
        recipe.getCategories().add(category2);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGRED_ID_1);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGRED_ID_2);
        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient2);
        return recipe;
    }
}