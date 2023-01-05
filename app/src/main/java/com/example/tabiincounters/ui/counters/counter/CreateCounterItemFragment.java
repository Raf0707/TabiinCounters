package com.example.tabiincounters.ui.counters.counter;

import static com.example.tabiincounters.utils.UtilFragment.changeFragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tabiincounters.databinding.CounterItemCreateBinding;

import com.example.tabiincounters.R;
import com.example.tabiincounters.ui.counters.CounterSavesFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;


public class CreateCounterItemFragment extends Fragment {

    CounterItemCreateBinding binding;
    private Handler handler;
    private String defaultValue = "10";
    private int maxValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){

        binding = CounterItemCreateBinding
                .inflate(inflater, container, false);

        handler = new Handler();

        binding.counterTitle.setCursorVisible(true);
        binding.counterTitle.setFocusableInTouchMode(true);
        binding.counterTitle.setEnabled(true);

        binding.counterTitle.setCursorVisible(true);
        binding.counterTitle.setFocusableInTouchMode(true);
        binding.counterTitle.setEnabled(true);


        binding.createCounterNote.setOnClickListener(view -> {
            // saveText()
            binding.counterTitle.setText(binding
                    .counterTitle
                    .getText()
                    .toString()
                    .replaceAll("[\\.\\-,\\s]+", ""));

            binding.counterTitle.setCursorVisible(false);
            binding.counterTitle.setFocusableInTouchMode(false);
            binding.counterTitle.setEnabled(false);

            binding.counterTitle.setCursorVisible(false);
            binding.counterTitle.setFocusableInTouchMode(false);
            binding.counterTitle.setEnabled(false);


            if (binding.counterTarget
                    .getText().toString().length() == 0) {
                binding.counterTarget.setText(defaultValue);
                maxValue = Integer.parseInt(binding
                        .counterTitle
                        .getText()
                        .toString());

                Snackbar.make(requireView(),
                                new StringBuilder()
                                        .append("Вы не ввели цель. По умолчанию: ")
                                        .append(defaultValue),
                                Snackbar.LENGTH_LONG)
                        .show();

            } else {

                if (Integer.parseInt(binding
                        .counterTitle
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

                    maxValue = Integer.parseInt(binding
                            .counterTitle
                            .getText().toString());

                }
            }

            if (binding.counterTitle
                    .getText().toString().length() == 0) {
                binding.counterTitle
                        .setText(getRandomString(15));
            }

            //CreateCounterItemFragment ccit = new CreateCounterItemFragment();
            CounterSavesFragment csf = new CounterSavesFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", binding.counterTitle.getText().toString());
            bundle.putInt("tsel", Integer.parseInt(binding.counterTarget.getText().toString()));
            csf.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.containerFragment, csf).commit();

        });

        binding.editCounterNote
                .setOnClickListener(view -> {

                    binding.counterTitle.setCursorVisible(true);
                    binding.counterTitle.setFocusableInTouchMode(true);
                    binding.counterTitle.setEnabled(true);

                    binding.counterTitle.setCursorVisible(true);
                    binding.counterTitle.setFocusableInTouchMode(true);
                    binding.counterTitle.setEnabled(true);

                    binding.counterTitle.requestFocus();

                    binding.counterTitle.setSelection(
                            binding.counterTitle.getText().length()
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
                        imm.showSoftInput(binding.counterTitle,
                                InputMethodManager.SHOW_FORCED);
                    }

                });

        binding.cancelCreatingCounter.setOnClickListener(view -> {
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