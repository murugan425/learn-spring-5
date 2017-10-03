package tamil.learn.springframework.learnspringrecipeapp.domain;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

    private static Category category;

    @BeforeClass
    public static void setUp() throws Exception {
        System.out.println("Execute Setup once before the test class..," + category);
        category = new Category();
        category.setId(15L);
    }

    @Before
    public void setUpTest() throws Exception {
        System.out.println("Execute Setup before each test method.., " + category);
        category = new Category();
    }

    @Test
    public void getId() throws Exception {
        Long categoryId = 10L;
        category.setId(categoryId);
        assertEquals(categoryId, category.getId());
    }

    @Test
    public void getCategoryname() throws Exception {
    }

    @Test
    public void getRecipes() throws Exception {
    }

}