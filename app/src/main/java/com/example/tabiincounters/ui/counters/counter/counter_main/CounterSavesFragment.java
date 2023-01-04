package com.example.tabiincounters.ui.counters.counter.counter_main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tabiincounters.R;
import com.example.tabiincounters.adapters.counter.CounterAdapter;
import com.example.tabiincounters.database.CounterItems;
import com.example.tabiincounters.databinding.FragmentCounterSavesBinding;


public class CounterSavesFragment extends Fragment {//implements CounterAdapter.HandleItemClick {

    private FragmentCounterSavesBinding binding;
    //private CounterViewModel viewModel;
    private CounterAdapter counterAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCounterSavesBinding
                .inflate(inflater, container, false);

        return binding.getRoot();

    }

}