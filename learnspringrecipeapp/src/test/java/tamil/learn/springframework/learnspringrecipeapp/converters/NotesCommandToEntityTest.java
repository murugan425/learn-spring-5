package tamil.learn.springframework.learnspringrecipeapp.converters;

import org.junit.Before;
import org.junit.Test;
import tamil.learn.springframework.learnspringrecipeapp.commands.NotesCommand;
import tamil.learn.springframework.learnspringrecipeapp.domain.Notes;

import static org.junit.Assert.*;

public class NotesCommandToEntityTest {
    public static final Long ID_VALUE = new Long(1L);
    public static final String NOTES_DESCRIPTION = "Test Recipe Notes";

    NotesCommandToEntity converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesCommandToEntity();
    }

    @Test
    public void testNullParameter() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    public void convert() throws Exception {
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setDescription(NOTES_DESCRIPTION);

        Notes notes = converter.convert(notesCommand);

        assertNotNull(notes);
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(NOTES_DESCRIPTION, notes.getDescription());
    }

}