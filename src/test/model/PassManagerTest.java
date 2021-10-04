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
        e1 = new Entry("www.google.com", "Jim", "12345", false);
        e2 = new Entry("www.facebook.com", "Bob", "abcde", false);
        e3 = new Entry("www.twitter.com", "Tim", "password", false);

    }

    @Test
    public void testGetter() {
        ptest.addEntry(e1);
        ptest.addEntry(e2);
        ptest.addEntry(e3);
        assertEquals(ptest.getEntries().size(), 3);
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
                "Website: www.google.com Username: Jim Password: 12345");
        assertEquals(ptest.retrieveString("www.twitter.com"),
                "Website: www.twitter.com Username: Tim Password: password");
        assertEquals(ptest.retrieveString("www.facebook.com"),
                "Website: www.facebook.com Username: Bob Password: abcde");
        assertEquals(ptest.retrieveString("www.4chan.org"), "Website not found in list.");
    }
}