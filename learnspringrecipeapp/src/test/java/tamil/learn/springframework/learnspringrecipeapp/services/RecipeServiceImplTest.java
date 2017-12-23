package tamil.learn.springframework.learnspringrecipeapp.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import tamil.learn.springframework.learnspringrecipeapp.converters.RecipeCommandToEntity;
import tamil.learn.springframework.learnspringrecipeapp.converters.RecipeEntityToCommand;
import tamil.learn.springframework.learnspringrecipeapp.domain.Recipe;
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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(categoryRepository, unitOfMeasureRepository, recipeRepository,
                                recipeCommandToEntity, recipeEntityToCommand);
    }

    @Test
    public void getAllRecipes() throws Exception {
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
    public void getRecipeById() throws Exception {
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
}