package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.*;

//This class references code from the JsonSerializationDemo repo.
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkEntry(String lab, String name, String pass, Boolean imp, EntryType cat, Entry e) {
        assertEquals(e.getLabel(), lab);
        assertEquals(e.getUsername(), name);
        assertEquals(e.getPassword(), pass);
        assertEquals(e.getImportant(), imp);
        assertEquals(e.getType(), cat);
    }
}
