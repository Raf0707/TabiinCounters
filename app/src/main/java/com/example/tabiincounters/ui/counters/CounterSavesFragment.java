package com.example.tabiincounters.ui.counters;

import static com.example.tabiincounters.utils.UtilFragment.changeFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.tabiincounters.R;
import com.example.tabiincounters.adapters.counter.CounterAdapter;
import com.example.tabiincounters.databinding.FragmentCounterSavesBinding;
import com.example.tabiincounters.ui.counters.counter.CreateCounterItemFragment;


public class CounterSavesFragment extends Fragment {

    private FragmentCounterSavesBinding binding;
    private CounterAdapter counterAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCounterSavesBinding
                .inflate(inflater, container, false);

        /*
        if (counterAdapter.getCurrentList().size() == 0) {
            binding.noRes.setVisibility(View.VISIBLE);
        }
        // не работает
         */

        binding.fabAddCounter.setOnClickListener(v -> {
            changeFragment(getActivity(),
                    new CreateCounterItemFragment(),
                    R.id.containerFragment,
                    savedInstanceState);
        });

        return binding.getRoot();

    }

}