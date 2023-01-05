package com.example.tabiincounters;

import static com.example.tabiincounters.utils.UtilFragment.changeFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tabiincounters.databinding.ActivityMainBinding;
import com.example.tabiincounters.ui.counters.CounterSavesFragment;
import com.example.tabiincounters.ui.counters.counter.CreateCounterItemFragment;
import com.example.tabiincounters.ui.about_app.AppAboutFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.counters:
                    changeFragment(this,
                            new CounterSavesFragment(),
                            R.id.containerFragment,
                            savedInstanceState);

                    break;

                case R.id.about_app:
                    changeFragment(this,
                            new AppAboutFragment(),
                            R.id.containerFragment,
                            savedInstanceState);

                    break;
            }
            return false;
        });
    }
}