package com.example.tabiincounters.ui.counters;

import static com.example.tabiincounters.utils.UtilFragment.changeFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.tabiincounters.R;
import com.example.tabiincounters.adapters.counter.CounterAdapter;
import com.example.tabiincounters.databinding.FragmentCounterSavesBinding;
import com.example.tabiincounters.domain.model.CounterItem;
import com.example.tabiincounters.ui.counters.counter.CounterViewModel;
import com.example.tabiincounters.ui.counters.counter.CreateCounterItemFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class CounterSavesFragment extends Fragment {

    private FragmentCounterSavesBinding binding;
    private CounterAdapter counterAdapter;
    public static WeakReference<CounterSavesFragment> ctx = null;
    private List<CounterItem> counterlist = new ArrayList<>();
    private CounterViewModel counterViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        counterAdapter = new CounterAdapter();

        binding = FragmentCounterSavesBinding
                .inflate(inflater, container, false);

        counterViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getActivity().getApplication()))
                .get(CounterViewModel.class);

        ctx = new WeakReference<>(this);

        //init();

        if (counterAdapter.getCurrentList().size() == 0) {
            binding.noRes.setVisibility(View.VISIBLE);
        } else {
            binding.noRes.setVisibility(View.GONE);
            //вот тут надо весь список выводить как?
        }

        binding.fabAddCounter.setOnClickListener(v -> {
            changeFragment(getActivity(),
                    new CreateCounterItemFragment(),
                    R.id.containerFragment,
                    savedInstanceState);
        });

        binding.recycleCounter.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recycleCounter.setHasFixedSize(true);
        binding.recycleCounter.setAdapter(counterAdapter);

        counterViewModel.getCounterlist().observe(getActivity(),
                counterItems -> counterAdapter.submitList(counterItems));



        return binding.getRoot();

    }

    public void init() {
        for(CounterItem counterItem : counterAdapter.getCurrentList()){
            counterlist.add(new CounterItem(
                    counterItem.getTitle(),
                    counterItem.getTarget(),
                    counterItem.getProgress()));
            notifyAll();
        }
    }

}