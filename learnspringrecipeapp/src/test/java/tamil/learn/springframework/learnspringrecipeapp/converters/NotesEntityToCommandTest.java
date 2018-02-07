package tamil.learn.springframework.learnspringrecipeapp.converters;

import org.junit.Before;
import org.junit.Test;
import tamil.learn.springframework.learnspringrecipeapp.commands.NotesCommand;
import tamil.learn.springframework.learnspringrecipeapp.domain.Notes;

import static org.junit.Assert.*;

public class NotesEntityToCommandTest {
    public static final Long ID_VALUE = new Long(1L);
    public static final String NOTES_DESCRIPTION = "Test Recipe Notes";

    NotesEntityToCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesEntityToCommand();
    }

    @Test
    public void testNullParameter() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    public void convert() throws Exception {
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setDescription(NOTES_DESCRIPTION);

        NotesCommand notesCommand = converter.convert(notes);

        assertEquals(ID_VALUE, notesCommand.getId());
        assertEquals(NOTES_DESCRIPTION, notesCommand.getDescription());
    }

}