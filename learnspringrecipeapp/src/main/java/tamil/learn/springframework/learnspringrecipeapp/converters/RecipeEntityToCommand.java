/* Created by Murugan_Nagarajan on 12/22/2017 */
package tamil.learn.springframework.learnspringrecipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import tamil.learn.springframework.learnspringrecipeapp.commands.RecipeCommand;
import tamil.learn.springframework.learnspringrecipeapp.domain.Category;
import tamil.learn.springframework.learnspringrecipeapp.domain.Recipe;

@Component
public class RecipeEntityToCommand implements Converter<Recipe, RecipeCommand>{

    private final CategoryEntityToCommand categoryEntityToCommand;
    private final IngredientEntityToCommand ingredientEntityToCommand;
    private final NotesEntityToCommand notesEntityToCommand;

    public RecipeEntityToCommand(CategoryEntityToCommand categoryEntityToCommand,
                                 IngredientEntityToCommand ingredientEntityToCommand,
                                 NotesEntityToCommand notesEntityToCommand) {
        this.categoryEntityToCommand = categoryEntityToCommand;
        this.ingredientEntityToCommand = ingredientEntityToCommand;
        this.notesEntityToCommand = notesEntityToCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        final RecipeCommand command = new RecipeCommand();
        command.setId(recipe.getId());
        command.setCookTime(recipe.getCookTime());
        command.setPrepTime(recipe.getPrepTime());
        command.setDescription(recipe.getDescription());
        command.setDifficulty(recipe.getDifficulty());
        command.setDirections(recipe.getDirections());
        command.setServings(recipe.getServings());
        command.setSource(recipe.getSource());
        command.setUrl(recipe.getUrl());
        command.setNotes(notesEntityToCommand.convert(recipe.getNotes()));

        if (recipe.getCategories() != null && recipe.getCategories().size() > 0){
            recipe.getCategories()
                    .forEach((Category category) -> command.getCategories().add(categoryEntityToCommand.convert(category)));
        }

        if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0){
            recipe.getIngredients()
                    .forEach(ingredient -> command.getIngredients().add(ingredientEntityToCommand.convert(ingredient)));
        }

        return command;
    }
}
