package model;

//This class represents an entry with a Website value and a Password value.
public class Entry {
    private String label;
    private String username;
    private String password;
    private Boolean isImportant;


    //REQUIRES: w and p both have length greater than zero
    //EFFECTS: sets this entry's website to string w, and the password to string p
    public Entry(String w, String u, String p, Boolean i) {
        this.label = w;
        this.username = u;
        this.password = p;
        this.isImportant = i;
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
        return "Website: " + this.label + " Username: " + this.username + " Password: " + this.password;
    }

}
