/* Created by Murugan_Nagarajan on 12/22/2017 */
package tamil.learn.springframework.learnspringrecipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import tamil.learn.springframework.learnspringrecipeapp.commands.RecipeCommand;
import tamil.learn.springframework.learnspringrecipeapp.domain.Recipe;

@Component
public class RecipeCommandToEntity implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToEntity categoryCommandToEntity;
    private final IngredientCommandToEntity ingredientCommandToEntity;
    private final NotesCommandToEntity notesCommandToEntity;

    public RecipeCommandToEntity(CategoryCommandToEntity categoryCommandToEntity,
                                 IngredientCommandToEntity ingredientCommandToEntity,
                                 NotesCommandToEntity notesCommandToEntity) {
        this.categoryCommandToEntity = categoryCommandToEntity;
        this.ingredientCommandToEntity = ingredientCommandToEntity;
        this.notesCommandToEntity = notesCommandToEntity;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setNotes(notesCommandToEntity.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() > 0){
            source.getCategories()
                    .forEach( category -> recipe.getCategories().add(categoryCommandToEntity.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients()
                    .forEach(ingredient -> recipe.addIngredient(ingredientCommandToEntity.convert(ingredient)));
        }

        return recipe;
    }
}
