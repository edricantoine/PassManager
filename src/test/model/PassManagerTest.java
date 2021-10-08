package model;

import static org.junit.jupiter.api.Assertions.*;

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
        e1 = new Entry("www.google.com", "Jim", "12345", false, EntryType.OTHER);
        e2 = new Entry("www.facebook.com", "Bob", "abcde", false, EntryType.OTHER);
        e3 = new Entry("www.twitter.com", "Tim", "password", false, EntryType.OTHER);

    }

    @Test
    public void testGetter() {
        ptest.addEntry(e1);
        ptest.addEntry(e2);
        ptest.addEntry(e3);
        assertEquals(ptest.getEntries().size(), 3);

    }

    @Test
    public void testGetNumOfType() {
        Entry e4 = new Entry("1", "A", "B", true, EntryType.ENTERTAINMENT);
        Entry e5 = new Entry("2", "C", "D", false, EntryType.ENTERTAINMENT);
        Entry e6 = new Entry("3", "E", "F", false, EntryType.FINANCE);
        ptest.addEntry(e4);
        ptest.addEntry(e5);
        ptest.addEntry(e6);

        assertEquals(ptest.getNumEntriesOfType(EntryType.ENTERTAINMENT), 2);
        assertEquals(ptest.getNumImportantEntriesOfType(EntryType.ENTERTAINMENT), 1);
        assertEquals(ptest.getNumEntriesOfType(EntryType.FINANCE), 1);
        assertEquals(ptest.getNumImportantEntriesOfType(EntryType.FINANCE), 0);
        assertEquals(ptest.getNumEntriesOfType(EntryType.WORK), 0);
    }

    @Test
    public void testGetImportant() {
        ptest.addEntry(e1);
        ptest.addEntry(e2);
        ptest.addEntry(e3);
        e1.makeImportant();
        assertEquals(ptest.getNumImportantEntries(), 1);
    }

    @Test
    public void testAddContains() {
        assertEquals(ptest.getNumEntries(), 0);
        ptest.addEntry(e1);
        ptest.addEntry(e2);
        ptest.addEntry(e3);
        assertEquals(ptest.getNumEntries(), 3);
        assertTrue(ptest.pmContains(e1));
        assertTrue(ptest.pmContains(e2));
        assertTrue(ptest.pmContains(e3));
    }

    @Test
    public void testRemove() {
        assertFalse(ptest.removeEntry(e1));
        ptest.addEntry(e1);
        ptest.addEntry(e2);
        assertEquals(ptest.getNumEntries(), 2);
        assertFalse(ptest.removeEntry(e3));
        assertTrue(ptest.removeEntry(e2));
        assertEquals(ptest.getNumEntries(), 1);
        assertFalse(ptest.removeEntry(e2));

    }

    @Test
    public void testRetrieve() {
        ptest.addEntry(e1);
        ptest.addEntry(e2);
        ptest.addEntry(e3);

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
}