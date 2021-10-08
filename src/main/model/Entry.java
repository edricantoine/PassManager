package model;

//This class represents an entry with a Website value and a Password value.
public class Entry {
    private String label;
    private String username;
    private String password;
    private Boolean isImportant;
    private EntryType type;


    //REQUIRES: w and p both have length greater than zero
    //EFFECTS: sets this entry's website to string w, and the password to string p
    public Entry(String w, String u, String p, Boolean i, EntryType t) {
        this.label = w;
        this.username = u;
        this.password = p;
        this.isImportant = i;
        this.type = t;
    }

    //getters
    public String getLabel() {
        return this.label;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Boolean getImportant() {
        return this.isImportant;
    }

    public EntryType getType() {
        return this.type;
    }

    //REQUIRES: newPassword has length greater than zero
    //MODIFIES: this
    //EFFECTS: sets this entry's password to newPass
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    //REQUIRES: newName has length greater than zero
    //MODIFIES: this
    //EFFECTS: sets this entry's username to newName
    public void setUsername(String newName) {
        this.username = newName;
    }

    //MODIFIES: this
    //EFFECTS: sets this entry's type to e
    public void setType(EntryType e) {
        this.type = e;
    }

    //MODIFIES: this
    //EFFECTS: marks this entry as important
    public void makeImportant() {
        this.isImportant = true;
    }

    //MODIFIES: this
    //EFFECTS: marks this entry as unimportant
    public void makeUnimportant() {
        this.isImportant = false;
    }

    //EFFECTS: returns string representation of an entry
    public String entryString() {
        return "Label: " + this.label + " Username: " + this.username + " Password: " + this.password;
    }

}
