package com.example.tabiincounters.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tabiincounters.R;
import com.example.tabiincounters.databinding.FragmentSettingsBinding;


public class SettingsFragment extends Fragment {
    FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());



        return binding.getRoot();
    }


}