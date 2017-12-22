/* Created by Murugan_Nagarajan on 12/22/2017 */
package tamil.learn.springframework.learnspringrecipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import tamil.learn.springframework.learnspringrecipeapp.commands.CategoryCommand;
import tamil.learn.springframework.learnspringrecipeapp.domain.Category;

@Component
public class CategoryCommandToEntity implements Converter<CategoryCommand, Category>{

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) {
            return null;
        }

        final Category category = new Category();
        category.setId(source.getId());
        category.setCategoryname(source.getCategoryname());
        return category;
    }
}
