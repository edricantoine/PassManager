package model;

import java.util.List;
import java.util.ArrayList;

//This class represents a collection of website-password pairs, with a List of entries.
//Functionality includes searching for specific entries by label, displaying all entries as Strings,
//returning the number of entries, etc.

public class PassManager {
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

    //REQUIRES: there are no other entries with the same website as e in entries
    //MODIFIES: this
    //EFFECTS: adds e to entries
    public void addEntry(Entry e) {
        this.entries.add(e);
    }

    //MODIFIES: this
    //EFFECTS: if e is in entries, removes e from entries and returns true. Returns false otherwise.
    public Boolean removeEntry(Entry e) {

        if (this.entries.contains(e)) {
            this.entries.remove(e);
            return true;
        } else {
            return false;
        }
    }

    //EFFECTS: if an entry exists with website same as key, that entry's string format is returned.
    public String retrieveString(String key) {
        for (Entry e : this.entries) {
            if (e.getLabel().equals(key)) {
                return e.entryString();
            }
        }

        return "Website not found in list.";
    }

    public Entry retrieveEntry(String key) {
        for (Entry e : this.entries) {
            if (e.getLabel().equals(key)) {
                return e;
            }
        }

        return null;
    }

}
