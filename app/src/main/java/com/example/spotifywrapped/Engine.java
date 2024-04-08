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
import java.util.stream.Collectors;

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

    /**
     * Adds a user to the database
     * @param username username of user to be added
     * @param email email of user to be added
     */
    public void addUser(String username, String email) {

        db.collection("user-info")
                .get()
                .addOnCompleteListener(task_i -> {
                    if (task_i.isSuccessful()) {
                        boolean flg = false;
                        for (QueryDocumentSnapshot doc : task_i.getResult()) {
                            if(username.equals(doc.getString("username")) || email.equals(doc.getString("email"))) {
                                flg = true;
                            }
                        }
                        if(!flg){
                            User u = new User(username, email);
                            db.collection("user-info").document(u.getUsername()).set(u);
                        }
                    }
                });
    }


    /**
     * Deletes a user to the database
     * @param username username of user to be added
     */
    public void deleteUser(String username) {
            db.collection("connections")
                    .whereEqualTo("u_1", username)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection("connections").document(document.getId()).delete();
                            }
                        } else {
                            System.err.println("Error deleting connection: " + task.getException());
                        }
                    });
            db.collection("connections")
                    .whereEqualTo("u_2", username)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection("connections").document(document.getId()).delete();
                            }
                        } else {
                            System.err.println("Error deleting connection: " + task.getException());
                        }
                    });
            db.collection("saved_wraps")
                    .whereEqualTo("username", username)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection("saved_wraps").document(document.getId()).delete();
                            }
                        } else {
                            System.err.println("Error deleting wrapped: " + task.getException());
                        }
                    });
            db.collection("user-info")
                    .whereEqualTo("username", username)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection("user-info").document(document.getId()).delete();
                            }
                        } else {
                            System.err.println("Error fetching user documents: " + task.getException());
                        }
                    });
    }



    /**
     * Checks if a and b are friends, or if a request has been sent. If not, request is sent.
     * @param a User sending the friend request
     * @param b User receiving the friend request (potentially change to email)
     */
    public void addConnection(String a, String b) {
        Task<QuerySnapshot> task1 = db.collection("connections")
                .whereEqualTo("u_1", a)
                .whereEqualTo("u_2", b)
                .get();

        Task<QuerySnapshot> task2 = db.collection("connections")
                .whereEqualTo("u_1", b)
                .whereEqualTo("u_2", a)
                .get();

        Tasks.whenAllComplete(task1, task2).addOnCompleteListener(tasks -> {
            if (tasks.isSuccessful()) {
                List<Task<QuerySnapshot>> queryTasks = Arrays.asList(task1, task2);
                int tot = 0;
                for (Task<QuerySnapshot> queryTask : queryTasks)
                    tot += queryTask.getResult().size();
                if(tot == 0) {
                    // a send request to b: hash id to a + '_' + b
                    String hsh = a + "_" + b;
                    Map<String, Object> item = new HashMap<>();
                    item.put("u_1", a);
                    item.put("u_2", b);
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
    public void acceptConnection(String from, String to){
        // to accepts request from from
        String hsh = from + "_" + to;
        Map<String, Object> item = new HashMap<>();
        item.put("u_1", from);
        item.put("u_2", to);
        item.put("pending", 0);
        db.collection("connections").document(hsh).set(item);
    }
    public CompletableFuture<List<User>> getConnections(String username) {
        CompletableFuture<List<User>> future = new CompletableFuture<>();
        List<User> out = new CopyOnWriteArrayList<>();

        Task<QuerySnapshot> task1 = db.collection("connections")
                .whereEqualTo("u_1", username)
                .whereEqualTo("pending", 0)
                .get();

        Task<QuerySnapshot> task2 = db.collection("connections")
                .whereEqualTo("u_2", username)
                .whereEqualTo("pending", 0)
                .get();

        Tasks.whenAllComplete(task1, task2).addOnCompleteListener(tasks -> {
            if (tasks.isSuccessful()) {
                List<Task<QuerySnapshot>> queryTasks = Arrays.asList(task1, task2);

                for (Task<QuerySnapshot> queryTask : queryTasks) {
                    if (queryTask.isSuccessful()) {
                        for (QueryDocumentSnapshot document : queryTask.getResult()) {
                            String otherUserId = document.getString("u_1").equals(username) ? document.getString("u_2") : document.getString("u_1");
                            db.collection("user-info")
                                    .whereEqualTo("username", otherUserId)
                                    .get()
                                    .addOnCompleteListener(task_i -> {
                                        if (task_i.isSuccessful()) {
                                            for (QueryDocumentSnapshot doc : task_i.getResult()) {
                                                User user = new User(doc.getString("username"), doc.getString("email"));
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


    public void addWrapped(String username, Wrapped wrapped) {
            Map<String, Object> wrappedData = new HashMap<>();
            wrappedData.put("username", username);
            wrappedData.put("songs", wrapped.getSongs().stream().map(Song::toMap).collect(Collectors.toList()));
            wrappedData.put("albums", wrapped.getAlbums().stream().map(Album::toMap).collect(Collectors.toList()));
            wrappedData.put("artists", wrapped.getArtists().stream().map(Artist::toMap).collect(Collectors.toList()));
            wrappedData.put("genres", new ArrayList<>(wrapped.getGenres()));

            db.collection("saved_wraps")
                    .add(wrappedData)
                    .addOnSuccessListener(documentReference -> {
                        System.out.println("Wrapped data added with ID: " + documentReference.getId());
                    })
                    .addOnFailureListener(e -> {
                        System.err.println("Error adding wrapped data: " + e);
                    });

    }
    public void deleteWrapped(String email, Wrapped wrapped) {
        // TODO: Delete an individual wrapped for year
        // even necessary to implement?
    }

    public CompletableFuture<List<Wrapped>> getWraps(String username){
        CompletableFuture<List<Wrapped>> future = new CompletableFuture<>();

        db.collection("saved_wraps")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Wrapped> wraps = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();

                            List<Song> songs = ((List<Map<String, Object>>) data.get("songs"))
                                    .stream()
                                    .map(Song::fromMap)
                                    .collect(Collectors.toList());

                            List<Album> albums = ((List<Map<String, Object>>) data.get("albums"))
                                    .stream()
                                    .map(Album::fromMap)
                                    .collect(Collectors.toList());

                            List<Artist> artists = ((List<Map<String, Object>>) data.get("artists"))
                                    .stream()
                                    .map(Artist::fromMap)
                                    .collect(Collectors.toList());

                            List<String> genres = (List<String>) data.get("genres");

                            Wrapped wrapped = new Wrapped(songs, albums, artists);
                            wraps.add(wrapped);
                        }
                        future.complete(wraps);
                    } else {
                        future.completeExceptionally(task.getException());
                    }
                });

        return future;
    }
}
