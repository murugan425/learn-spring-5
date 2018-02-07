package tamil.learn.springframework.learnspringrecipeapp.converters;

import org.junit.Before;
import org.junit.Test;
import tamil.learn.springframework.learnspringrecipeapp.commands.CategoryCommand;
import tamil.learn.springframework.learnspringrecipeapp.domain.Category;

import static org.junit.Assert.*;

public class CategoryEntityToCommandTest {
    public static final Long ID_VALUE = new Long(1L);
    public static final String CATEGORY_NAME = "New Test Category";
    CategoryEntityToCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryEntityToCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Category()));
    }


    @Test
    public void convert() throws Exception {
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setCategoryname(CATEGORY_NAME);

        CategoryCommand categoryCommand = converter.convert(category);

        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(CATEGORY_NAME, categoryCommand.getCategoryname());
    }

}