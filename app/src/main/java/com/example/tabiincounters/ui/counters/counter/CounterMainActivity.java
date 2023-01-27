package com.example.tabiincounters.ui.counters.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tabiincounters.databinding.ActivityCounterMainBinding;

public class CounterMainActivity extends AppCompatActivity {
    ActivityCounterMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCounterMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


    }
}