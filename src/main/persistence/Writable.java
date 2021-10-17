package persistence;

import org.json.JSONObject;

public interface Writable {
    //EFFECTS: returns this Writable as JSONObject
    JSONObject toJson();
}
