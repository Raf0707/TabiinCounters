package com.example.tabiincounters.ui.counters;

import static com.example.tabiincounters.utils.UtilFragment.changeFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.tabiincounters.database.CounterDatabase;
import com.example.tabiincounters.databinding.FragmentCounterSavesBinding;
import com.example.tabiincounters.domain.model.CounterItem;
import com.example.tabiincounters.ui.counters.counter.CounterMainFragment;
import com.example.tabiincounters.ui.counters.counter.CounterViewModel;
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
    private CounterDatabase counterDatabase;
    private CounterAdapter.MyViewHolder holder;
    private boolean edit;
    private String title;
    private int target;
    private int progress;
    private int id;


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


        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title", "title");
            target = bundle.getInt("target", 10);
            progress = bundle.getInt("progress", 0);
            id = bundle.getInt("id");
            edit = bundle.getBoolean("edit");
        }

        ctx = new WeakReference<>(this);

        binding.fabAddCounter.setOnClickListener(v -> {
            //counterItemDialog(false);
            onMaterialAlert(false);
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

    public void onMaterialAlert(boolean isForEdit) {
        MaterialAlertDialogBuilder alert =
                new MaterialAlertDialogBuilder(getContext());

        View dialogView = getLayoutInflater()
                .inflate(R.layout.create_counter_dialog, null);

        alert.setTitle("Новый счетчик");
        alert.setMessage("введите название и цель");
        alert.setCancelable(true);

        EditText counterTitle = dialogView.findViewById(R.id.counterTitle);
        EditText counterTarget = dialogView.findViewById(R.id.counterTarget);
        TextView counterProgress = dialogView.findViewById(R.id.counterProgress);

        if (isForEdit) {
            alert.setTitle("Изменить счетчик");
            counterTitle.setText(counterForEdit.title);
            counterTarget.setText(counterForEdit.target + "");
        }

        alert.setNegativeButton("Отмена", (dialogInterface, i) -> {

        });


        alert.setPositiveButton("ОК", (dialogInterface, i) -> {
            if (counterTitle.getText().toString().length() == 0) {
                counterTitle.setText(getRandomString(12));
            }

            if (counterTarget.getText().toString().length() == 0) {
                counterTarget.setText("10");
            }

            if (counterProgress.getText().toString().length() == 0) {
                counterProgress.setText("0");
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
        });

        alert.setView(dialogView);
        alert.show();
    }

    public static String getRandomString( int length) {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Проверяем по коду нужный результат
        if(requestCode == 120) {
            if(resultCode == Activity.RESULT_OK) {
                //Действия при возврате результата
                String updateTitle = data.getStringExtra("updateTitle");
                int updateTarget = data.getIntExtra("updateTarget", 10);
                int updateProgress = data.getIntExtra("updateProgress", progress);

                CounterItem counterItem = new CounterItem(updateTitle, updateTarget,
                        updateProgress);

                title =  updateTitle;
                target = updateTarget;
                progress = updateProgress;

                counterViewModel.update(counterItem);
            }
        }
    }


    @Override
    public void itemClick(CounterItem counterItem) {
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();
        bundle.putString("title", counterItem.title);
        bundle.putInt("target", counterItem.target);
        bundle.putInt("progress", counterItem.progress);
        bundle.putInt("id", counterItem.id);
        cmf.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.containerFragment, cmf).commit();

        Snackbar.make(binding.getRoot(), String.valueOf(counterItem.id),
                Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void deleteItem(CounterItem counterItem) {
        counterViewModel.delete(counterItem);
    }

    @Override
    public void editItem(CounterItem counterItem) {
        this.counterForEdit = counterItem;
        onMaterialAlert(true);
    }
}