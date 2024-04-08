package com.example.spotifywrapped.ui.update_account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotifywrapped.Song;
import com.example.spotifywrapped.User;

import java.util.ArrayList;
import java.util.List;

public class FriendsViewModel extends ViewModel {
    private final MutableLiveData<String> mText = new MutableLiveData<>();
    private MutableLiveData<List<User>> friendsList = new MutableLiveData<>();
    public FriendsViewModel() {
        mText.setValue("This is the friend fragment");
        friendsList.setValue(new ArrayList<>());
    }

    public void updateFriendsList(List<User> friends) {
        friendsList.setValue(friends);
    }

    public LiveData<List<User>> getFriendsList() {
        return friendsList;
    }

    public void updateText(String text) {

        mText.setValue(text);
    }
    public LiveData<String> getText() {
        return mText;
    }
}
