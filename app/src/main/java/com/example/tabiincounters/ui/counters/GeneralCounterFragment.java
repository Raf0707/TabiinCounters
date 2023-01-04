package com.example.tabiincounters.ui.counters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tabiincounters.R;
import com.example.tabiincounters.databinding.FragmentGeneralCounterBinding;


public class GeneralCounterFragment extends Fragment {
    private FragmentGeneralCounterBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGeneralCounterBinding.inflate(getLayoutInflater());



        return binding.getRoot();
    }
}