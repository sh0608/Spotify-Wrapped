package com.example.spotifywrapped;

import com.google.firebase.firestore.FirebaseFirestore;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
public class Engine {
    private FirebaseFirestore db;

    public Engine() {
        db = FirebaseFirestore.getInstance();
    }

    public void addUser(User u) {
        Map<String, Object> item = new HashMap<>();
        item.put("id", u.getId());
        item.put("username", u.getUsername());
        item.put("email", u.getEmail());
        db.collection("user-info").add(item);
    }
    public void addConnection(User a, User b) {
        Map<String, Object> item = new HashMap<>();
        item.put("id_1", a.getId());
        item.put("id_2", b.getId());
        db.collection("user-connections").add(item);
    }

    public ArrayList<User> getConnections(User u) {
        ArrayList<User> out = new ArrayList<User>();
        // TODO: query all connections where user a or b is user u.
        return out;
    }

    public static JSONObject createAndAddWrapped(User u, int year) {
        // TODO: Create and add an individual wrapped for year
        return null;
    }
    public void deleteWrapped(User u, int year) {
        // TODO: Delete an individual wrapped for year
    }
    public static JSONObject createAndAddWrapped(Connection c, int year) {
        // TODO: Create and add an duo-wrapped for year
        return null;
    }
    public void deleteWrapped(Connection c, int year) {
        // TODO: Delete a duo-wrapped for year (overloaded)
    }
    public static ArrayList<JSONObject> getWraps(User u){
        // TODO: return list of all spotify wraps for a user, that have been generated (overloaded)
        return null;
    }
}
