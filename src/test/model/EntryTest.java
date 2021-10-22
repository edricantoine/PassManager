package model;
import exceptions.InvalidLengthException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
public class EntryTest {
    private Entry e;

    @BeforeEach
    public void setUp() {
        try {
            e = new Entry("www.google.com", "Jim", "password", false, EntryType.OTHER);
        } catch (InvalidLengthException e) {
            System.out.println("Error...");
        }
    }

    @Test
    public void testConstructorExceptions() {
        try {
            e = new Entry("", "Jim", "password", false, EntryType.OTHER);
            fail("Exception expected.");
        } catch (InvalidLengthException e) {
            System.out.println("Success!");
        }
        try {
            e = new Entry("aaa", "", "password", false, EntryType.OTHER);
            fail("Exception expected.");
        } catch (InvalidLengthException e) {
            System.out.println("Success!");
        }
        try {
            e = new Entry("aaa", "Jim", "", false, EntryType.OTHER);
            fail("Exception expected.");
        } catch (InvalidLengthException e) {
            System.out.println("Success!");
        }
    }

    @Test
    public void testGetSetType() {
        assertEquals(e.getType(), EntryType.OTHER);
        e.setType(EntryType.FINANCE);
        assertEquals(e.getType(), EntryType.FINANCE);
    }

    @Test
    public void testSetPasswordValidLength() {
        assertEquals(e.getPassword(), "password");
        try {
            e.setPassword("123456789");
        } catch (InvalidLengthException e) {
            fail("No exception expected...");
        }
        assertNotEquals("password", e.getPassword());
        assertEquals(e.getPassword(), "123456789");
    }

    @Test
    public void testSetPasswordInvalidLength() {
        try {
            e.setPassword("");
            fail("Exception expected.");
        } catch (InvalidLengthException e) {
            //pass
        }
        assertEquals(e.getPassword(), "password");
    }

    @Test
    public void testSetUsernameValidLength() {
        assertEquals(e.getUsername(), "Jim");
        try {
            e.setUsername("Steve");
        } catch (InvalidLengthException e) {
            fail("No exception expected");
        }
        assertEquals(e.getUsername(), "Steve");
    }

    @Test
    public void testSetUsernameInvalidLength() {
        try {
            e.setUsername("");
            fail("Exception expected.");
        } catch (InvalidLengthException e) {
            //pass
        }
        assertEquals(e.getUsername(), "Jim");
    }

    @Test
    public void testEntryString() {
        assertEquals(e.entryString(), "Label: www.google.com Username: Jim Password: password");
        try {
            e.setPassword("1234");
        } catch (InvalidLengthException e) {
            fail("No exception expected");
        }
        assertEquals(e.entryString(), "Label: www.google.com Username: Jim Password: 1234");
        try {
            e.setUsername("Steve");
        } catch (InvalidLengthException e) {
            fail("No exception expected");
        }
        assertEquals(e.entryString(), "Label: www.google.com Username: Steve Password: 1234");
    }

    @Test
    public void testImportant() {
        assertFalse(e.getImportant());
        e.makeImportant();
        assertTrue(e.getImportant());
        e.makeUnimportant();
        assertFalse(e.getImportant());
    }

    @Test
    public void testToJson() {
        JSONObject temp = e.toJson();
        assertEquals(temp.get("label"), e.getLabel());
        assertEquals(temp.get("username"), e.getUsername());
        assertEquals(temp.get("password"), e.getPassword());
        assertEquals(temp.get("isImportant"), e.getImportant());
        assertEquals(temp.get("type"), e.getType());
    }

}
