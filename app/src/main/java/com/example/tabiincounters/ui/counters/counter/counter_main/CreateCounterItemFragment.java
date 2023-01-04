package com.example.tabiincounters.ui.counters.counter.counter_main;

import static com.example.tabiincounters.utils.UtilFragment.changeFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.example.tabiincounters.R;
import com.example.tabiincounters.databinding.FragmentCreateCounterItemBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;


public class CreateCounterItemFragment extends Fragment {

        FragmentCreateCounterItemBinding binding;
        private Handler handler;
        private String defoltValue = "10";
        private int maxvalue;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){

            binding = FragmentCreateCounterItemBinding
                    .inflate(inflater, container, false);

            handler = new Handler();

            binding.titleDB.setCursorVisible(true);
            binding.titleDB.setFocusableInTouchMode(true);
            binding.titleDB.setEnabled(true);

            binding.titleDB.setCursorVisible(true);
            binding.titleDB.setFocusableInTouchMode(true);
            binding.titleDB.setEnabled(true);

            binding.titleDescriptFromCreateItem.setCursorVisible(true);
            binding.titleDescriptFromCreateItem.setFocusableInTouchMode(true);
            binding.titleDescriptFromCreateItem.setEnabled(true);

            binding.createItemC.setOnClickListener(view -> {
                // saveText()
                binding.titleDB.setText(binding
                        .titleDB
                        .getText()
                        .toString()
                        .replaceAll("[\\.\\-,\\s]+", ""));

                binding.titleDB.setCursorVisible(false);
                binding.titleDB.setFocusableInTouchMode(false);
                binding.titleDB.setEnabled(false);

                binding.titleDB.setCursorVisible(false);
                binding.titleDB.setFocusableInTouchMode(false);
                binding.titleDB.setEnabled(false);

                binding.titleDescriptFromCreateItem.setCursorVisible(false);
                binding.titleDescriptFromCreateItem.setFocusableInTouchMode(false);
                binding.titleDescriptFromCreateItem.setEnabled(false);

                if (binding.tselDB
                        .getText().toString().length() == 0) {
                    binding.tselDB.setText(defoltValue);
                    maxvalue = Integer.parseInt(binding
                            .titleDB
                            .getText()
                            .toString());

                    Snackbar.make(requireView(),
                                    new StringBuilder()
                                            .append("Вы не ввели цель. По умолчанию: ")
                                            .append(defoltValue),
                                    Snackbar.LENGTH_LONG)
                            .show();

                } else {

                    if (Integer.parseInt(binding
                            .titleDB
                            .getText().toString()) <= 0) {
                        Snackbar.make(requireView(),
                                        new StringBuilder()
                                                .append("Введите число больше нуля!"),
                                        Snackbar.LENGTH_LONG)
                                .show();

                    } else {

                        Snackbar.make(requireView(),
                                        new StringBuilder()
                                                .append("Цель установлена"),
                                        Snackbar.LENGTH_LONG)
                                .show();

                        maxvalue = Integer.parseInt(binding
                                .titleDB
                                .getText().toString());

                    }
                }

                if (binding.titleDB
                        .getText().toString().length() == 0) {
                    binding.titleDB
                            .setText(getRandomString(15));
                }

                //CreateCounterItemFragment ccit = new CreateCounterItemFragment();
                CounterSavesFragment csf = new CounterSavesFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", binding.titleDB.getText().toString());
                bundle.putInt("tsel", Integer.parseInt(binding.tselDB.getText().toString()));
                bundle.putString("description", binding.titleDescriptFromCreateItem
                        .getText().toString());
                csf.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.containerFragment, csf).commit();

            });

            binding.editItemC
                    .setOnClickListener(view -> {

                        binding.titleDB.setCursorVisible(true);
                        binding.titleDB.setFocusableInTouchMode(true);
                        binding.titleDB.setEnabled(true);

                        binding.titleDB.setCursorVisible(true);
                        binding.titleDB.setFocusableInTouchMode(true);
                        binding.titleDB.setEnabled(true);

                        binding.titleDescriptFromCreateItem.setCursorVisible(true);
                        binding.titleDescriptFromCreateItem.setFocusableInTouchMode(true);
                        binding.titleDescriptFromCreateItem.setEnabled(true);

                        binding.titleDB.requestFocus();

                        binding.titleDB.setSelection(
                                binding.titleDB.getText().length()
                        );

                        getActivity()
                                .getWindow()
                                .setFlags(WindowManager
                                                .LayoutParams
                                                .FLAG_NOT_FOCUSABLE,
                                        WindowManager
                                                .LayoutParams
                                                .FLAG_ALT_FOCUSABLE_IM
                                );

                        getActivity()
                                .getWindow()
                                .setSoftInputMode(
                                        WindowManager
                                                .LayoutParams
                                                .SOFT_INPUT_STATE_VISIBLE
                                );

                        getContext()
                                .getSystemService(Context
                                        .INPUT_METHOD_SERVICE);

                        InputMethodManager imm = (InputMethodManager) getActivity()
                                .getSystemService(Context
                                        .INPUT_METHOD_SERVICE);

                        if (imm != null) {
                            imm.showSoftInput(binding.titleDB,
                                    InputMethodManager.SHOW_FORCED);
                        }

                    });

            binding.cancelCreateItemC.setOnClickListener(view -> {
                changeFragment(requireActivity(),
                        new CounterSavesFragment(),
                        R.id.containerFragment,
                        savedInstanceState
                );
            });

            return binding.getRoot();
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