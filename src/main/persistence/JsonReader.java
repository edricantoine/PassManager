package persistence;

import model.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//This class references code from the JsonSerializationDemo repo.
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//Represents a reader that reads PassManagers from JSON files

public class JsonReader {
    private String sourceFile;

    //EFFECTS: constructs a JsonReader to read from sourceFile
    public JsonReader(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    //EFFECTS: reads data from sourceFile and returns it as PassManager.
    //         throws IOException if an error occurs while reading file
    public PassManager read() throws IOException {
        String data = readFile(sourceFile);
        JSONObject object = new JSONObject(data);
        return parsePassManager(object);
    }

    //EFFECTS: reads source file as string, returns it
    public String readFile(String sfile) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(sfile), StandardCharsets.UTF_8)) {
            stream.forEach(s -> builder.append(s));
        }

        return builder.toString();
    }

    //EFFECTS: parses PassManager from sourceFile and returns it
    private PassManager parsePassManager(JSONObject o) {
        PassManager pm = new PassManager();
        addEntries(pm, o);
        return pm;
    }

    //MODIFIES: pm
    //EFFECTS: gets entries array as JSONArray, adds each entry in JSONArray to PassManager as normal Entry
    private void addEntries(PassManager pm, JSONObject o) {
        JSONArray jarray = o.getJSONArray("entries");
        for (Object json : jarray) {
            JSONObject nextEntry = (JSONObject) json;
            addEntry(pm, nextEntry);
        }
    }

    //MODIFIES: pm
    //EFFECTS: constructs an Entry from data in JSONObject, adds it to pm
    private void addEntry(PassManager pm, JSONObject o) {
        String label = o.getString("label");
        String username = o.getString("username");
        String password = o.getString("password");
        Boolean isImportant = o.getBoolean("isImportant");
        EntryType type = EntryType.valueOf(o.getString("type"));

        Entry entry = new Entry(label, username, password, isImportant, type);
        pm.addEntry(entry);

    }
}
