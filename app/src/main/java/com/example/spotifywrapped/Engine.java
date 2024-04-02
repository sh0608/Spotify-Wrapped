package com.example.spotifywrapped;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import org.json.JSONObject;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import androidx.annotation.NonNull;
import com.google.firebase.firestore.Query;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import android.util.Log;

public class Engine {
    private FirebaseFirestore db;

    public Engine() {
        db = FirebaseFirestore.getInstance();
    }

    public void addUser(User u) {
        db.collection("user-info").document(u.getId()).set(u);
    }
    public void addConnection(User a, User b) {
        Map<String, Object> item = new HashMap<>();
        item.put("id_1", a.getId());
        item.put("id_2", b.getId());
        item.put("pending", 0);
        db.collection("user-connections").add(item);
    }

    public ArrayList<User> getConnections(User u) {
        // query all connections where user a or b is user u.
        ArrayList<User> out = new ArrayList<User>();

        Query capitalCities = db.collection("user-info").whereEqualTo("id", "1");

        db.collection("user-info")
                .whereEqualTo("id", "1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("LOG_TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("LOG_TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        return out;

//        Task<QuerySnapshot> task1 = db.collection("user-connections")
//                .whereEqualTo("id_1", u.getId())
//                .whereEqualTo("pending", "0")
//                .get();
//
//        Task<QuerySnapshot> task2 = db.collection("user-connections")
//                .whereEqualTo("id_2", u.getId())
//                .whereEqualTo("pending", "0")
//                .get();
//
//        Tasks.whenAllComplete(task1, task2).addOnCompleteListener(tasks -> {
//            if (tasks.isSuccessful()) {
//                for (Task<QuerySnapshot> task : Arrays.asList(task1, task2)) {
//                    if (task.isSuccessful()) {
//                        QuerySnapshot snapshot = task.getResult();
//                        for (QueryDocumentSnapshot document : snapshot) {
//                            String otherUserId = document.getString("id_1").equals(u.getId()) ? document.getString("id_2") : document.getString("id_1");
//                            System.out.println("reached");
//                            db.collection("user-info").document(otherUserId).get().addOnCompleteListener(userTask -> {
//                                if (userTask.isSuccessful() && userTask.getResult().exists()) {
//                                    User user = userTask.getResult().toObject(User.class);
//                                    out.add(user);
//                                }
//                            });
//                        }
//                    }
//                }
//            }
//        });
//        System.out.println("DONE");
//        System.out.println("out size: " + out.size());
//        for(int i=0; i<out.size(); i++) {
//            System.out.println("printed!");
//            System.out.println("LOGGED: " + out.get(i).getUsername());
//        }
//        return out;
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
