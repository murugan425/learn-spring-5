package tamil.learn.springframework.learnspringrecipeapp.converters;

import org.junit.Before;
import org.junit.Test;
import tamil.learn.springframework.learnspringrecipeapp.commands.UnitOfMeasureCommand;
import tamil.learn.springframework.learnspringrecipeapp.domain.UnitOfMeasure;

import static org.junit.Assert.*;

public class UnitOfMeasureEntityToCommandTest {
    public static final Long LONG_VALUE = new Long(1L);
    public static final String DESCRIPTION = "description";

    UnitOfMeasureEntityToCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureEntityToCommand();
    }

    @Test
    public void testNullObjectConvert() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObj() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() throws Exception {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);

        UnitOfMeasureCommand command = converter.convert(uom);

        assertEquals(LONG_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }

}