package com.example.tabiincounters.ui.counters;

import static com.example.tabiincounters.utils.UtilFragment.changeFragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tabiincounters.R;
import com.example.tabiincounters.adapters.counter.CounterAdapter;
import com.example.tabiincounters.databinding.CounterItemElementBinding;
import com.example.tabiincounters.databinding.CreateCounterDialogBinding;
import com.example.tabiincounters.databinding.FragmentCounterSavesBinding;
import com.example.tabiincounters.domain.model.CounterItem;
import com.example.tabiincounters.ui.counters.change_data.EditDataCounterFragment;
import com.example.tabiincounters.ui.counters.counter.CounterMainFragment;
import com.example.tabiincounters.ui.counters.counter.CounterViewModel;
import com.example.tabiincounters.ui.counters.counter.CreateCounterItemFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CounterSavesFragment extends Fragment
        implements CounterAdapter.HandleCounterClick {

    private FragmentCounterSavesBinding binding;
    private CounterAdapter counterAdapter;
    public static WeakReference<CounterSavesFragment> ctx = null;
    private List<CounterItem> counterlist = new ArrayList<>();
    private CounterViewModel counterViewModel;
    private CounterItem counterForEdit;
    private CounterMainFragment cmf;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cmf = new CounterMainFragment();

        counterAdapter = new CounterAdapter(getContext(), this);

        binding = FragmentCounterSavesBinding
                .inflate(inflater, container, false);

        counterViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getActivity().getApplication()))
                .get(CounterViewModel.class);

        ctx = new WeakReference<>(this);

        binding.fabAddCounter.setOnClickListener(v -> {
            counterItemDialog(false);
        });

        initRecycleView();
        initViewModel();
        counterViewModel.getAllCounterList();
        return binding.getRoot();
    }

    public void initRecycleView() {
        binding.recycleCounter.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recycleCounter.setHasFixedSize(true);
        binding.recycleCounter.setAdapter(counterAdapter);
    }

    public void initViewModel() {
        counterViewModel = new ViewModelProvider(this)
                .get(CounterViewModel.class);
        counterViewModel.getCounterlistObserver()
                .observe(requireActivity(), counterItems -> {
            if (counterItems == null) {
                binding.noRes.setVisibility(View.VISIBLE);
                binding.recycleCounter.setVisibility(View.GONE);
            } else {
                counterAdapter.setCounterList(counterItems);
                binding.recycleCounter.setVisibility(View.VISIBLE);
                binding.noRes.setVisibility(View.GONE);
            }
        });
    }

    private void counterItemDialog(boolean isForEdit) {
        AlertDialog alert =
                new AlertDialog.Builder(getContext()).create();

        View dialogView = getLayoutInflater()
                .inflate(R.layout.create_counter_dialog, null);

        EditText counterTitle = dialogView.findViewById(R.id.counterTitle);
        EditText counterTarget = dialogView.findViewById(R.id.counterTarget);
        Button createCounterNote = dialogView.findViewById(R.id.createCounterNote);
        Button cancelCreatingCounter = dialogView.findViewById(R.id.cancelCreatingCounter);

        if (isForEdit) {
            //createCounterNote.setText("Изменить");
            counterTitle.setText(counterForEdit.title);
            counterTarget.setText(counterForEdit.target + "");
        }

        cancelCreatingCounter.setOnClickListener(v -> {
            alert.dismiss();
        });

        createCounterNote.setOnClickListener(view -> {
            if (counterTitle.getText().toString().length() == 0) {
                counterTitle.setText(getRandomString(12));
            }

            if (counterTarget.getText().toString().length() == 0) {
                counterTarget.setText("10");
            }

            if (isForEdit) {
                counterForEdit.title = counterTitle.getText().toString();
                counterForEdit.target = Integer.parseInt(counterTarget
                        .getText().toString());
                counterViewModel.update(counterForEdit);
            } else {
                counterViewModel.insert(counterTitle.getText().toString(),
                        Integer.parseInt(counterTarget.getText().toString()));
            }

            alert.dismiss();

        });

        alert.setView(dialogView);
        alert.show();

    }


    @Override
    public void itemClick(CounterItem counterItem) {
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();
        bundle.putString("title", counterItem.title);
        bundle.putInt("target", counterItem.target);
        bundle.putInt("progress", counterItem.progress);
        cmf.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.containerFragment, cmf).commit();
    }

    @Override
    public void deleteItem(CounterItem counterItem) {
        counterViewModel.delete(counterItem);
        //notifyAll();
        //counterViewModel.getAllCounterList();
    }

    @Override
    public void editItem(CounterItem counterItem) {
        this.counterForEdit = counterItem;
        counterItemDialog(true);
    }

    public static String getRandomString ( int length){
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(3);
            long result = 0;
            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append((char) result);
                    break;
                case 1:
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append((char) result);
                    break;
                case 2:
                    sb.append(new Random().nextInt(10));
                    break;
            }


        }
        return sb.toString();
    }
}