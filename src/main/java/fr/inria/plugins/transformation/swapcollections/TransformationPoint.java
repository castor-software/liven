package fr.inria.plugins.transformation.swapcollections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class TransformationPoint {
    String clazz;
    int line;
    String point;
    Set<String> alternatives;

    public TransformationPoint (String clazz, int line, String point) {
        this.clazz = clazz;
        this.line = line;
        this.point = point;
        this.alternatives = new HashSet<>();
    }

    public void addAlternative(String alternative) {
        this.alternatives.add(alternative);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject res = new JSONObject();
        res.put("class", clazz);
        res.put("line", line);
        res.put("tp", point);

        JSONArray a = new JSONArray();
        for(String al : alternatives) {
            a.put(al);
        }
        res.put("alternatives", a);
        return res;
    }
}
