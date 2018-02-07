/* Created by Murugan_Nagarajan on 12/22/2017 */
package tamil.learn.springframework.learnspringrecipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import tamil.learn.springframework.learnspringrecipeapp.commands.UnitOfMeasureCommand;
import tamil.learn.springframework.learnspringrecipeapp.domain.UnitOfMeasure;

@Component
public class UnitOfMeasureEntityToCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand>{

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure != null) {
            final UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
            uomc.setId(unitOfMeasure.getId());
            uomc.setDescription(unitOfMeasure.getDescription());
            return uomc;
        }
        return null;
    }
}
