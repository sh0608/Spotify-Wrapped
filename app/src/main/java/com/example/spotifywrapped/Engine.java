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
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.Source;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import android.util.Log;

public class Engine {
    private FirebaseFirestore db;

    public Engine() {
        db = FirebaseFirestore.getInstance();
    }

//    /**
//     * Adds a user to the database
//     * @param username username of user to be added
//     * @param email email of user to be added
//     */

    // Temporary add user function -- change to username and email in future
    public void addUser(User u) {
        // fetch id
        db.collection("user-info").document(u.getId()).set(u);
    }

    /**
     * Checks if a and b are friends, or if a request has been sent. If not, request is sent.
     * @param a User sending the friend request
     * @param b User receiving the friend request (potentially change to email)
     */
    public void addConnection(User a, User b) {
        Task<QuerySnapshot> task1 = db.collection("connections")
                .whereEqualTo("id_1", a.getId())
                .whereEqualTo("id_2", b.getId())
                .get();

        Task<QuerySnapshot> task2 = db.collection("connections")
                .whereEqualTo("id_1", b.getId())
                .whereEqualTo("id_2", a.getId())
                .get();

        Tasks.whenAllComplete(task1, task2).addOnCompleteListener(tasks -> {
            if (tasks.isSuccessful()) {
                List<Task<QuerySnapshot>> queryTasks = Arrays.asList(task1, task2);
                int tot = 0;
                for (Task<QuerySnapshot> queryTask : queryTasks)
                    tot += queryTask.getResult().size();
                if(tot == 0) {
                    // a send request to b: hash id to a + '_' + b
                    String hsh = a.getId() + "_" + b.getId();
                    Map<String, Object> item = new HashMap<>();
                    item.put("id_1", a.getId());
                    item.put("id_2", b.getId());
                    item.put("pending", 1);
                    Log.d("LOG_TAG", "New connection!");
                    db.collection("connections").document(hsh).set(item);
                }
            }
        });
    }

    /**
     * Changes an active request from pending to not pending
     * @param from User that sent request
     * @param to User accepting request
     */
    public void acceptConnection(User from, User to){
        // to accepts request from from
        String hsh = from.getId() + "_" + to.getId();
        Map<String, Object> item = new HashMap<>();
        item.put("id_1", from.getId());
        item.put("id_2", to.getId());
        item.put("pending", 0);
        db.collection("connections").document(hsh).set(item);
    }
    public CompletableFuture<List<User>> getConnections(User u) {
        CompletableFuture<List<User>> future = new CompletableFuture<>();
        List<User> out = new CopyOnWriteArrayList<>();

        Task<QuerySnapshot> task1 = db.collection("connections")
                .whereEqualTo("id_1", u.getId())
                .whereEqualTo("pending", 0)
                .get();

        Task<QuerySnapshot> task2 = db.collection("connections")
                .whereEqualTo("id_2", u.getId())
                .whereEqualTo("pending", 0)
                .get();

        Tasks.whenAllComplete(task1, task2).addOnCompleteListener(tasks -> {
            if (tasks.isSuccessful()) {
                List<Task<QuerySnapshot>> queryTasks = Arrays.asList(task1, task2);

                for (Task<QuerySnapshot> queryTask : queryTasks) {
                    if (queryTask.isSuccessful()) {
                        for (QueryDocumentSnapshot document : queryTask.getResult()) {
                            String otherUserId = document.getString("id_1").equals(u.getId()) ? document.getString("id_2") : document.getString("id_1");
                            db.collection("user-info")
                                    .whereEqualTo("id", otherUserId)
                                    .get()
                                    .addOnCompleteListener(task_i -> {
                                        if (task_i.isSuccessful()) {
                                            for (QueryDocumentSnapshot doc : task_i.getResult()) {
                                                User user = new User(doc.getString("id"), doc.getString("username"), doc.getString("email"));
                                                out.add(user);
                                            }
                                            if (out.size() >= (task1.getResult().size() + task2.getResult().size())) {
                                                future.complete(out);
                                            }
                                        } else {
                                            future.completeExceptionally(task_i.getException());
                                        }
                                    });
                        }
                    } else {
                        future.completeExceptionally(queryTask.getException());
                    }
                }
            } else {
                future.completeExceptionally(tasks.getException());
            }
        });

        return future;
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
