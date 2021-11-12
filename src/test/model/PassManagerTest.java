package model;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.DuplicateLabelException;
import exceptions.InvalidLengthException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class PassManagerTest {

    private PassManager ptest;
    private Entry e1;
    private Entry e2;
    private Entry e3;

    @BeforeEach
    public void setUp() {

        ptest = new PassManager();
        try {
            e1 = new Entry("www.google.com", "Jim", "12345", false, EntryType.OTHER);
        } catch (InvalidLengthException e) {
            System.out.println("Error occurred...");
        }
        try {
            e2 = new Entry("www.facebook.com", "Bob", "abcde", false, EntryType.OTHER);
        } catch (InvalidLengthException e) {
            e.printStackTrace();
        }
        try {
            e3 = new Entry("www.twitter.com", "Tim", "password", false, EntryType.OTHER);
        } catch (InvalidLengthException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetter() {
        try {
            ptest.addEntry(e1);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        try {
            ptest.addEntry(e2);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        try {
            ptest.addEntry(e3);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        assertEquals(ptest.getEntries().size(), 3);

    }

    @Test
    public void testGetNumOfType() {
        Entry e4 = null;
        try {
            e4 = new Entry("1", "A", "B", true, EntryType.ENTERTAINMENT);
        } catch (InvalidLengthException e) {
            e.printStackTrace();
        }
        Entry e5 = null;
        try {
            e5 = new Entry("2", "C", "D", false, EntryType.ENTERTAINMENT);
        } catch (InvalidLengthException e) {
            e.printStackTrace();
        }
        Entry e6 = null;
        try {
            e6 = new Entry("3", "E", "F", false, EntryType.FINANCE);
        } catch (InvalidLengthException e) {
            e.printStackTrace();
        }
        try {
            ptest.addEntry(e4);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        try {
            ptest.addEntry(e5);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        try {
            ptest.addEntry(e6);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }

        assertEquals(ptest.getNumEntriesOfType(EntryType.ENTERTAINMENT), 2);
        assertEquals(ptest.getNumImportantEntriesOfType(EntryType.ENTERTAINMENT), 1);
        assertEquals(ptest.getNumEntriesOfType(EntryType.FINANCE), 1);
        assertEquals(ptest.getNumImportantEntriesOfType(EntryType.FINANCE), 0);
        assertEquals(ptest.getNumEntriesOfType(EntryType.WORK), 0);
    }

    @Test
    public void testGetImportant() {
        try {
            ptest.addEntry(e1);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        try {
            ptest.addEntry(e2);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        try {
            ptest.addEntry(e3);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        e1.makeImportant();
        assertEquals(ptest.getNumImportantEntries(), 1);
    }

    @Test
    public void testAddContains() {
        assertEquals(ptest.getNumEntries(), 0);
        try {
            ptest.addEntry(e1);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        try {
            ptest.addEntry(e2);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        try {
            ptest.addEntry(e3);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        assertEquals(ptest.getNumEntries(), 3);
        assertTrue(ptest.pmContains(e1));
        assertTrue(ptest.pmContains(e2));
        assertTrue(ptest.pmContains(e3));
    }

    @Test
    public void testAddException() {
        try {
            ptest.addEntry(e1);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }

        try {
            ptest.addEntry(e1);
            fail("Exception expected");
        } catch (DuplicateLabelException e) {
            System.out.println("Correct exception thrown!");
        }
    }

    @Test
    public void testRemove() {
        assertFalse(ptest.removeEntry(e1));
        try {
            ptest.addEntry(e1);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        try {
            ptest.addEntry(e2);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }

        assertEquals(ptest.getNumEntries(), 2);
        assertFalse(ptest.removeEntry(e3));
        assertTrue(ptest.removeEntry(e2));
        assertEquals(ptest.getNumEntries(), 1);
        assertFalse(ptest.removeEntry(e2));

    }

    @Test
    public void testRetrieve() {
        try {
            ptest.addEntry(e1);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        try {
            ptest.addEntry(e2);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        try {
            ptest.addEntry(e3);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }

        assertEquals(ptest.retrieveString("www.google.com"),
                "Label: www.google.com Username: Jim Password: 12345");
        assertEquals(ptest.retrieveString("www.twitter.com"),
                "Label: www.twitter.com Username: Tim Password: password");
        assertEquals(ptest.retrieveString("www.facebook.com"),
                "Label: www.facebook.com Username: Bob Password: abcde");
        assertEquals(ptest.retrieveString("www.4chan.org"), "Website not found in list.");
        assertEquals(ptest.retrieveEntry("www.google.com"),
                e1);
        assertEquals(ptest.retrieveEntry("www.twitter.com"),
                e3);

        assertEquals(ptest.retrieveEntry("www.facebook.com"),
                e2);
        assertNull(ptest.retrieveEntry("www.4chan.org"));
    }

    @Test
    public void testRetrieveFromStringValue() {
        try {
            ptest.addEntry(e1);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }

        assertEquals(ptest.retrieveEntryFromStringValue("Label: www.google.com Username: Jim Password: 12345"),
                e1);
        assertNull(ptest.retrieveEntryFromStringValue("abcde"));

    }

    @Test
    public void testToJson() {
        try {
            ptest.addEntry(e1);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        try {
            ptest.addEntry(e2);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }
        try {
            ptest.addEntry(e3);
        } catch (DuplicateLabelException e) {
            fail("No exception expected...");
        }

        JSONObject temp = ptest.toJson();
        JSONArray array = temp.getJSONArray("entries");
        JSONArray array2 = new JSONArray();

        for (Entry e : ptest.getEntries()) {
            array2.put(e.toJson());
        }
        String s1 = array.toString();
        String s2 = array2.toString();
        assertEquals(s1, s2);
    }


}