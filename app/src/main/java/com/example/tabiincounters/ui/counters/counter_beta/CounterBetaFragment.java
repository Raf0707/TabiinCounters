package com.example.tabiincounters.ui.counters.counter_beta;

import static com.example.tabiincounters.utils.UtilFragment.changeFragment;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;

import com.example.tabiincounters.R;
import com.example.tabiincounters.databinding.FragmentCounterBetaBinding;
import com.example.tabiincounters.ui.TutorialFragment;
import com.example.tabiincounters.ui.SettingsFragment;
import com.example.tabiincounters.utils.CallBack;
import com.example.tabiincounters.utils.OnSwipeTouchListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

public class CounterBetaFragment extends Fragment {

    private FragmentCounterBetaBinding binding;
    private int currentCount;
    private String defaultValue = "10";
    private int maxValue;
    private SharedPreferences sPref;
    private Handler handler;

    private static final TimeInterpolator GAUGE_ANIMATION_INTERPOLATOR =
            new DecelerateInterpolator(2);

    private static final long GAUGE_ANIMATION_DURATION = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCounterBetaBinding
                .inflate(inflater, container, false);

        handler = new Handler();

        binding.counterTarget.setCursorVisible(true);
        binding.counterTarget.setFocusableInTouchMode(true);
        binding.counterTarget.setEnabled(true);

        binding.saveEdition.setOnClickListener(view -> {
            binding.counterTarget.setText(
                    binding.counterTarget
                            .getText()
                            .toString()
                            .replaceAll("[\\.\\-,\\s]+", ""));

            binding.counterTarget.setCursorVisible(false);
            binding.counterTarget.setFocusableInTouchMode(false);
            binding.counterTarget.setEnabled(false);

            if (binding.counterTarget.getText().toString().length() == 0) {
                binding.counterTarget.setText(defaultValue);
                maxValue = Integer.parseInt(binding
                        .counterTarget
                        .getText()
                        .toString());

                binding.counterBetaProgress.setMax(maxValue);

                Snackbar.make(requireView(),
                                new StringBuilder()
                                        .append("Вы не ввели цель. По умолчанию: ")
                                        .append(defaultValue),
                                Snackbar.LENGTH_LONG)
                        .show();

            } else {

                if (Integer.parseInt(binding.counterTarget.getText().toString()) <= 0) {
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

                    maxValue = Integer.parseInt(binding.counterTarget.getText().toString());
                    binding.counterBetaProgress.setMax(maxValue);
                    binding
                            .textCounter
                            .setText(MessageFormat
                                    .format("{0}",
                                            currentCount));

                }
            }

        });

        binding.editCounter.setOnClickListener(view -> {
            binding.counterTarget.setCursorVisible(true);
            binding.counterTarget.setFocusableInTouchMode(true);
            binding.counterTarget.setEnabled(true);

            binding.counterTarget.requestFocus();

            binding.counterTarget.setSelection(
                    binding.counterTarget.getText().length()
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
                imm.showSoftInput(binding.counterTarget,
                        InputMethodManager.SHOW_FORCED);
            }
        });

        binding.deleteCounter.setOnClickListener(view -> {
            binding.counterTarget.getText().clear();
        });

        binding.counterBetaView.setOnTouchListener
                (new OnSwipeTouchListener(binding
                        .counterBetaView.getContext()) {

                    @Override
                    public void onClick() {
//                        currentCount++;
//                        binding.textBetaProgress
//                                .setText(Integer.toString(currentCount));

                        if (binding.counterTarget.getText().toString().length() == 0) {
                            maxValue = 100;
                            binding.counterTarget.setText(Integer.toString(maxValue));
                            binding.counterBetaProgress.setMax(100);
                            binding.textCounter
                                    .setText(MessageFormat.format("{0}",
                                            currentCount));
                        }
                        if (currentCount == maxValue) {
                            binding.textCounter
                                    .setText(MessageFormat
                                            .format("{0}",
                                                    binding.counterTarget
                                                            .getText()
                                                            .toString()));

                            Snackbar.make(requireView(),
                                            new StringBuilder()
                                                    .append("Цель достигнута! " +
                                                            "Да вознаградит вас Аллах!"),
                                            Snackbar.LENGTH_LONG)
                                    .show();
                        }

                        if (binding.counterTarget.getText().toString() != null) {
                            currentCount++;
                            if (currentCount <= Integer
                                    .parseInt(binding.counterTarget
                                            .getText()
                                            .toString())) {
                                binding.textCounter
                                        .setText(MessageFormat
                                                .format("{0}", currentCount));
                            }

                            ObjectAnimator animator = ObjectAnimator
                                    .ofInt(binding.counterBetaProgress,
                                            "progress",
                                            currentCount, currentCount);

                            animator.setInterpolator(GAUGE_ANIMATION_INTERPOLATOR);
                            animator.setDuration(GAUGE_ANIMATION_DURATION);
                            animator.start();


                            if (binding.counterTarget.length() != 0) {
                                maxValue = Integer.parseInt(binding.counterTarget.getText().toString());

                                if (currentCount == maxValue) {
                                    Snackbar.make(requireView(),
                                                    new StringBuilder()
                                                            .append("Цель достигнута! " +
                                                                    "Да вознаградит вас Аллах!"),
                                                    Snackbar.LENGTH_LONG)
                                            .show();

                                }

                            }

                        }


                        else {
                            Snackbar.make(requireView(),
                                            new StringBuilder()
                                                    .append("Введите цель!"),
                                            Snackbar.LENGTH_LONG)
                                    .show();
                        }

                    }

                    @Override
                    public void onSwipeDown() {
//                        currentCount--;
//                        binding.textBetaProgress
//                                .setText(Integer.toString(currentCount));

                        currentCount--;
                        if (currentCount < 0) {
                            currentCount = 0;
                        }

                        if (binding.counterTarget
                                .getText()
                                .toString()
                                .length() == 0) {
                            binding.textCounter
                                    .setText(MessageFormat.format("{0}",
                                            currentCount));
                        } else if (currentCount <= Integer
                                .parseInt(binding.counterTarget
                                        .getText()
                                        .toString())) {
                            binding.textCounter
                                    .setText(MessageFormat
                                            .format("{0}",
                                                    currentCount));

                        }

                        ObjectAnimator animatorMinus = ObjectAnimator
                                .ofInt(binding.counterBetaProgress,
                                        "progress",
                                        currentCount, currentCount);

                        animatorMinus.setInterpolator(GAUGE_ANIMATION_INTERPOLATOR);
                        animatorMinus.setDuration(GAUGE_ANIMATION_DURATION);
                        animatorMinus.start();
                    }

                    @Override
                    public void onLongClick() {
                        if (currentCount != 0) onMaterialAlert();
                    }

                });

        binding.openSettingsBtn.setOnClickListener(view -> {
            changeFragment(requireActivity(),
                    new SettingsFragment(),
                    R.id.containerFragment,
                    savedInstanceState
            );
        });

        binding.openTutorialBtn.setOnClickListener(view -> {
            changeFragment(requireActivity(),
                    new TutorialFragment(),
                    R.id.containerFragment,
                    savedInstanceState
            );
        });

        Thread thread = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
                handler.post(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();

        return binding.getRoot();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            CallBack.runAllCallbacks();
            handler.postDelayed(runnable, 10);
        }
    };

    public void onMaterialAlert() {
        new MaterialAlertDialogBuilder(requireContext(),
                R.style.AlertDialogTheme)
                .setTitle("Reset")
                .setMessage("Вы уверены, что хотите обновить счетчик?")
                .setPositiveButton("Да", (dialogInterface, i) -> {
                    currentCount = 0;
                    binding.textCounter
                            .setText(new StringBuilder()
                                    .append("0"));

                    ObjectAnimator animatorMaterial = ObjectAnimator
                            .ofInt(binding.counterBetaProgress,
                                    "progress", currentCount);
                    animatorMaterial
                            .setInterpolator(GAUGE_ANIMATION_INTERPOLATOR);
                    animatorMaterial
                            .setDuration(GAUGE_ANIMATION_DURATION);
                    animatorMaterial.start();
                })
                .setNeutralButton("Отмена",
                        (dialogInterface, i) ->
                                dialogInterface.cancel())
                .show();
    }

}