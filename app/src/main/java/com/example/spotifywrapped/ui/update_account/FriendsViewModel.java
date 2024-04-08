package com.example.spotifywrapped.ui.update_account;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FriendsViewModel extends ViewModel {
    private final MutableLiveData<String> mText = new MutableLiveData<>();
    public FriendsViewModel() {
        mText.setValue("This is the friend fragment");
    }
}
