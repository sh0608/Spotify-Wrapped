package com.example.spotifywrapped.ui.update_account;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class UpdateAccountViewModel extends ViewModel {
    private final MutableLiveData<String> mText = new MutableLiveData<>();
    public UpdateAccountViewModel() {
        mText.setValue("This is update account fragment");
    }
}