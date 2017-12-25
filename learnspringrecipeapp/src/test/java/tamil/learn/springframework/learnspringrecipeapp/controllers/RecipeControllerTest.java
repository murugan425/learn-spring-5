package tamil.learn.springframework.learnspringrecipeapp.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import tamil.learn.springframework.learnspringrecipeapp.commands.RecipeCommand;
import tamil.learn.springframework.learnspringrecipeapp.domain.Recipe;
import tamil.learn.springframework.learnspringrecipeapp.services.RecipeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {
    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    private RecipeController recipeController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    public void getRecipeById() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(2L);

        when(recipeService.getRecipeById(2L)).thenReturn(recipe);
        mockMvc.perform(get("/recipe/" + recipe.getId() +"/view")).andExpect(status().isOk())
                .andExpect(view().name("showrecipe"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void showRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe/form"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void saveRecipe() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1000L);
        recipeCommand.setDescription("JUnit Test");

        when(recipeService.saveRecipe(any())).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipe/save"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/"+recipeCommand.getId()+"/view/"));
    }

    @Test
    public void updateRecipe() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1000L);
        recipeCommand.setDescription("JUnit Test Recipe");

        when(recipeService.getRecipeCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1000/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }
}