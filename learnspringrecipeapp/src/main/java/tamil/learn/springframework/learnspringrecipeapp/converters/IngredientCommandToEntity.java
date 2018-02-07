/* Created by Murugan_Nagarajan on 12/22/2017 */
package tamil.learn.springframework.learnspringrecipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import tamil.learn.springframework.learnspringrecipeapp.commands.IngredientCommand;
import tamil.learn.springframework.learnspringrecipeapp.domain.Ingredient;

@Component
public class IngredientCommandToEntity implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToEntity unitOfMeasureCommandToEntity;

    public IngredientCommandToEntity(UnitOfMeasureCommandToEntity unitOfMeasureCommandToEntity) {
        this.unitOfMeasureCommandToEntity = unitOfMeasureCommandToEntity;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUom(unitOfMeasureCommandToEntity.convert(source.getUom()));
        return ingredient;
    }
}
