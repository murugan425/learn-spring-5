package tamil.learn.springframework.learnspringrecipeapp.converters;

import org.junit.Before;
import org.junit.Test;
import tamil.learn.springframework.learnspringrecipeapp.commands.CategoryCommand;
import tamil.learn.springframework.learnspringrecipeapp.domain.Category;

import static org.junit.Assert.*;

public class CategoryCommandToEntityTest {
    public static final Long ID_VALUE = new Long(1L);
    public static final String CATEGORY_NAME = "New Test Category";
    CategoryCommandToEntity converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryCommandToEntity();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    public void convert() throws Exception {
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setCategoryname(CATEGORY_NAME);

        Category category = converter.convert(categoryCommand);

        assertEquals(ID_VALUE, category.getId());
        assertEquals(CATEGORY_NAME, category.getCategoryname());
    }

}