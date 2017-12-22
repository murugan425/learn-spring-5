/* Created by Murugan_Nagarajan on 12/22/2017 */
package tamil.learn.springframework.learnspringrecipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import tamil.learn.springframework.learnspringrecipeapp.commands.IngredientCommand;
import tamil.learn.springframework.learnspringrecipeapp.domain.Ingredient;

@Component
public class IngredientEntityToCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureEntityToCommand unitOfMeasureEntityToCommand;

    public IngredientEntityToCommand(UnitOfMeasureEntityToCommand unitOfMeasureEntityToCommand) {
        this.unitOfMeasureEntityToCommand = unitOfMeasureEntityToCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredient.getId());
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setDescription(ingredient.getDescription());
        ingredientCommand.setUom(unitOfMeasureEntityToCommand.convert(ingredient.getUom()));
        return ingredientCommand;
    }
}
