package com.example.spotifywrapped.ui.wrap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WrapViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WrapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is wrap fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}