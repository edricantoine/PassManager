package model;

import exceptions.InvalidLengthException;
import persistence.Writable;
import org.json.JSONObject;


//This class represents an entry in the password manager with a Label, a Username value and a Password value.
//Username, Password, isImportant, Type fields can be changed, and an entry can be displayed as a String.
public class Entry implements Writable {

    private String label;
    private String username;
    private String password;
    private Boolean isImportant;
    private EntryType type;



    //EFFECTS: sets this entry's website to string w, and the password to string p
    public Entry(String w, String u, String p, Boolean i, EntryType t) throws InvalidLengthException {
        if (w.length() <= 0) {
            throw new InvalidLengthException();
        }
        if (u.length() <= 0) {
            throw new InvalidLengthException();
        }
        if (p.length() <= 0) {
            throw new InvalidLengthException();
        }
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

    //MODIFIES: this
    //EFFECTS: sets this entry's password to newPass
    public void setPassword(String newPassword) throws InvalidLengthException {
        if (newPassword.length() <= 0) {
            throw new InvalidLengthException();
        } else {
            this.password = newPassword;
        }

    }


    //MODIFIES: this
    //EFFECTS: sets this entry's username to newName
    public void setUsername(String newName) throws InvalidLengthException {
        if (newName.length() <= 0) {
            throw new InvalidLengthException();
        } else {
            this.username = newName;
        }
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

    //This method references code from the JsonSerializationDemo repo.
    //Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    //EFFECTS: returns JSONObject representation of this Entry

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("label", label);
        json.put("username", username);
        json.put("password", password);
        json.put("isImportant", isImportant);
        json.put("type", type);
        return json;
    }
}
