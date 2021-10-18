package persistence;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//This class references code from the JsonSerializationDemo repo.
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriteInvalidFileName() {
        PassManager pm = new PassManager();
        try {
            JsonWriter wt = new JsonWriter("./data/ineaae\0invalid.json");
            wt.openFile();
            fail("Exception expected");
        } catch (IOException e) {
            System.out.println("Yay, an exception!");
        }

    }

    @Test
    public void testWriteEmptyManager() {

        try {
            PassManager pm = new PassManager();
            JsonWriter wt = new JsonWriter("./data/WriterEmpty.json");
            wt.openFile();
            wt.write(pm);
            wt.close();

            JsonReader rd = new JsonReader("./data/WriterEmpty.json");
            pm = rd.read();
            assertEquals(pm.getNumEntries(), 0);
        } catch (IOException e) {
            fail("Couldn't write...");
        }
    }

    @Test
    public void testWriteNormalManager() {
        try {
            PassManager pm = new PassManager();
            JsonWriter wt = new JsonWriter("./data/WriterNormal.json");
            pm.addEntry(new Entry("a", "b", "c", true, EntryType.OTHER));
            pm.addEntry(new Entry("d", "e", "f", false, EntryType.WORK));
            pm.addEntry(new Entry("g", "h", "i", true, EntryType.ENTERTAINMENT));
            wt.openFile();
            wt.write(pm);
            wt.close();

            JsonReader rd = new JsonReader("./data/WriterNormal.json");
            pm = rd.read();
            assertEquals(pm.getNumEntries(), 3);
            List<Entry> testList = pm.getEntries();
            checkEntry("a", "b", "c", true, EntryType.OTHER, testList.get(0));
            checkEntry("d", "e", "f", false, EntryType.WORK, testList.get(1));
            checkEntry("g", "h", "i", true, EntryType.ENTERTAINMENT, testList.get(2));
            assertEquals(pm.getNumImportantEntries(), 2);
            assertEquals(pm.getNumImportantEntriesOfType(EntryType.ENTERTAINMENT), 1);
        } catch (IOException e) {
            fail("Couldn't write...");
        }
    }
}
