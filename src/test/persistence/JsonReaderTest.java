package persistence;
import exceptions.DuplicateLabelException;
import exceptions.InvalidLengthException;
import model.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.DynAnyPackage.Invalid;

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

    //THESE TWO TESTS SHOULD NEVER HAVE THESE SITUATIONS APPEAR DUE TO EXCEPTION HANDLING IN THE
    //OTHER MODEL CODE PREVENTING "INVALID" PASSWORD MANAGERS. THEY ARE HERE FOR 100% CODE COVERAGE.

    @Test
    public void testAddEntryInvalidLength() {
        JsonReader rd = new JsonReader("./data/ReaderExceptions.json");
        try {
            PassManager pm = rd.read();
            fail("NullPointerException expected");
        } catch (IOException e) {
            fail("Couldn't read file.");
        } catch (NullPointerException e) {
            System.out.println("Success, entry stayed as null causing exception!");
            //We expect a null pointer exception here since JsonReader.addEntries attempts to then call pm.addEntry
            //on a null Entry object.
        }

    }

    @Test
    public void testAddEntryDuplicateLabel() {
        JsonReader rd = new JsonReader("./data/ReaderDupLabel.json");
        try {
            PassManager pm = rd.read();
            assertEquals(pm.getNumEntries(), 3);
            //Here, the Entry object is not null, but it is never added to the password manager.
        } catch (IOException e) {
            fail("Couldn't read file.");
        }
    }

}
