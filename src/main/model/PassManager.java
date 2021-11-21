package model;

import exceptions.DuplicateLabelException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;
import java.util.ArrayList;

//This class represents a collection of website-password pairs, with a List of entries.
//Functionality includes searching for specific entries by label, displaying all entries as Strings,
//returning the number of entries, etc.

public class PassManager implements Writable {
    private List<Entry> entries;

    //EFFECTS: Creates a new PassManager with blank entries list.
    public PassManager() {
        this.entries = new ArrayList<>();
    }

    //getters
    public List<Entry> getEntries() {
        return this.entries;
    }

    public int getNumEntries() {
        return this.entries.size();
    }

    public int getNumImportantEntries() {
        int ans = 0;
        for (Entry e : entries) {
            if (e.getImportant()) {
                ans++;
            }
        }
        return ans;
    }

    public int getNumEntriesOfType(EntryType sortType) {
        int ans = 0;
        for (Entry e : entries) {
            if (e.getType().equals(sortType)) {
                ans++;
            }
        }
        return ans;
    }

    public int getNumImportantEntriesOfType(EntryType sortType) {
        int ans = 0;
        for (Entry e : entries) {
            if (e.getImportant() && e.getType().equals(sortType)) {
                ans++;
            }
        }
        return ans;
    }

    //EFFECTS: returns true if entry e is contained in list
    public Boolean pmContains(Entry e) {
        return this.entries.contains(e);
    }


    //MODIFIES: this
    //EFFECTS: adds e to entries and adds this info to EventLog
    public void addEntry(Entry e) throws DuplicateLabelException {
        for (Entry h : entries) {
            if (h.getLabel().equals(e.getLabel())) {
                throw new DuplicateLabelException();
            }
        }
        this.entries.add(e);
        EventLog.getInstance().logEvent(new Event("Added an entry with label " + e.getLabel()));
    }

    //MODIFIES: this
    //EFFECTS: if e is in entries, removes e from entries, adds info to EventLog, and returns true.
    //         Returns false otherwise.
    public Boolean removeEntry(Entry e) {

        if (this.entries.contains(e)) {
            this.entries.remove(e);
            EventLog.getInstance().logEvent(new Event("Removed an entry with label " + e.getLabel()));
            return true;
        } else {
            return false;
        }
    }

    //EFFECTS: if an entry exists with label same as key, that entry's string format is returned.
    public String retrieveString(String key) {
        for (Entry e : this.entries) {
            if (e.getLabel().equals(key)) {
                return e.entryString();
            }
        }

        return "Website not found in list.";
    }

    //EFFECTS: if an entry exists with label same as key, that entry is returned.
    public Entry retrieveEntry(String key) {
        for (Entry e : this.entries) {
            if (e.getLabel().equals(key)) {
                return e;
            }
        }

        return null;
    }

    //EFFECTS: if entry exists with entryString() value same as s, that entry is returned.
    public Entry retrieveEntryFromStringValue(String s) {
        for (Entry e : this.entries) {
            if (e.entryString().equals(s)) {
                return e;
            }
        }
        return null;
    }

    //This method references code from the JsonSerializationDemo repo.
    //Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    //EFFECTS: returns the JSONObject representation of this PassManager

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("entries", entriesToJson());
        return json;
    }

    //This method references code from the JsonSerializationDemo repo.
    //Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    //EFFECTS: returns the entries array as a JSON array

    private JSONArray entriesToJson() {
        JSONArray jarray = new JSONArray();

        for (Entry e : entries) {
            jarray.put(e.toJson());
        }
        return jarray;
    }
}
