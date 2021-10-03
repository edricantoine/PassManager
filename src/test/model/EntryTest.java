package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class EntryTest {
    private Entry e;

    @BeforeEach
    public void setUp() {
        e = new Entry("www.google.com", "Jim", "password");
    }

    @Test
    public void testSetPassword() {
        assertEquals(e.getPassword(), "password");
        e.setPassword("123456789");
        assertNotEquals("password", e.getPassword());
        assertEquals(e.getPassword(), "123456789");
    }

    @Test
    public void testSetUsername() {
        assertEquals(e.getUsername(), "Jim");
        e.setUsername("Steve");
        assertEquals(e.getUsername(), "Steve");
    }

    @Test
    public void testEntryString() {
        assertEquals(e.entryString(), "Website: www.google.com Username: Jim Password: password");
        e.setPassword("1234");
        assertEquals(e.entryString(), "Website: www.google.com Username: Jim Password: 1234");
        e.setUsername("Steve");
        assertEquals(e.entryString(), "Website: www.google.com Username: Steve Password: 1234");
    }

    @Test
    public void testImportant() {
        assertFalse(e.getImportant());
        e.makeImportant();
        assertTrue(e.getImportant());
        e.makeUnimportant();
        assertFalse(e.getImportant());
    }

}
