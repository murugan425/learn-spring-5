package tamil.learn.springframework.learnspringrecipeapp.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import tamil.learn.springframework.learnspringrecipeapp.domain.Recipe;
import tamil.learn.springframework.learnspringrecipeapp.services.RecipeService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {
    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    private RecipeController recipeController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
    }

    @Test
    public void getRecipeById() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(2L);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        when(recipeService.getRecipeById(2L)).thenReturn(recipe);
        mockMvc.perform(get("/recipe/view/" + recipe.getId())).andExpect(status().isOk())
                .andExpect(view().name("showrecipe"))
                .andExpect(model().attributeExists("recipe"));
    }
}