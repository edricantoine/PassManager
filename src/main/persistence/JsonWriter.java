package persistence;

import model.PassManager;
import org.json.JSONObject;

import java.io.*;

//This class references code from the JsonSerializationDemo repo.
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//Represents a writer that writes data to a JSON file
public class JsonWriter {
    private static final int INDENT = 4;
    private PrintWriter writer;
    private String destination;

    //EFFECTS: constructs writer to write to dest, a JSON file
    public JsonWriter(String dest) {
        this.destination = dest;
    }

    //MODIFIES: this
    //EFFECTS: opens writer, throws FileNotFoundException if destination file cannot be found
    public void openFile() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //MODIFIES: this
    //EFFECTS: writes JSON representation of PassManager to file
    public void write(PassManager pm) {
        JSONObject json = pm.toJson();
        saveToFile(json.toString(INDENT));
    }

    //MODIFIES: this
    //EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes string to JSON file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
