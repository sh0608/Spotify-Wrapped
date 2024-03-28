package com.example.spotifywrapped.ui.wrap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifywrapped.databinding.FragmentWrapBinding;

public class WrapFragment extends Fragment {

    private FragmentWrapBinding  binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WrapViewModel wrapViewModel =
                new ViewModelProvider(this).get(WrapViewModel.class);

        binding = FragmentWrapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        wrapViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}