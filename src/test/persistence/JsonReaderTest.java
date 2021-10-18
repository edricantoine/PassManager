package persistence;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//This class references code from the JsonSerializationDemo repo.
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest extends JsonTest {

    @Test
    public void testNoFileFound() {
        JsonReader rd = new JsonReader("./data/NonexistentFile.json");
        try {
            PassManager pm = rd.read();
            fail("Exception expected");
        } catch (IOException e) {
            System.out.println("Success");
        }
    }

    @Test
    public void testEmptyManager() {
        JsonReader rd = new JsonReader("./data/ReaderEmpty.json");
        try {
            PassManager pm = rd.read();
            assertEquals(pm.getNumEntries(), 0);
        } catch (IOException e) {
            fail("Couldn't read file.");
        }
    }

    @Test
    public void testNormalManager() {
        JsonReader rd = new JsonReader("./data/ReaderNormal.json");
        try {
            PassManager pm = rd.read();
            assertEquals(pm.getNumEntries(), 4);
            assertEquals(pm.getNumImportantEntries(), 2);
            assertEquals(pm.getNumImportantEntriesOfType(EntryType.FINANCE), 1);
            assertEquals(pm.getNumImportantEntriesOfType(EntryType.DEVICES), 1);
            assertEquals(pm.getNumImportantEntriesOfType(EntryType.ENTERTAINMENT), 0);
            List<Entry> testList = pm.getEntries();
            checkEntry("A", "B", "C", true, EntryType.FINANCE, testList.get(0));
            checkEntry("D", "E", "F", false, EntryType.ENTERTAINMENT, testList.get(1));
            checkEntry("G", "H", "I", false, EntryType.WORK, testList.get(2));
            checkEntry("J", "K", "L", true, EntryType.DEVICES, testList.get(3));
        } catch (IOException e) {
            fail("Couldn't read file.");
        }
    }

}
