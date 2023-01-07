package com.example.tabiincounters.adapters.counter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabiincounters.R;
import com.example.tabiincounters.databinding.CounterItemCreateBinding;
import com.example.tabiincounters.databinding.CounterItemElementBinding;
import com.example.tabiincounters.databinding.FragmentCounterSavesBinding;
import com.example.tabiincounters.domain.dao.CounterItemDao;
import com.example.tabiincounters.domain.model.CounterItem;
import com.example.tabiincounters.domain.repo.CounterRepository;
import com.example.tabiincounters.ui.counters.counter.CounterViewModel;

public class CounterAdapter extends ListAdapter<CounterItem, CounterAdapter.ViewHolder> {

    public CounterAdapter() {
        super(CALLBACK);
    }
    private CounterItemDao counterItemDao;

    private static final DiffUtil.ItemCallback<CounterItem> CALLBACK = new DiffUtil.ItemCallback<CounterItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull CounterItem oldItem, @NonNull CounterItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CounterItem oldItem, @NonNull CounterItem newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.counter_item_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CounterItem counterItem = getItem(position);



        holder.binding.titleId.setText(counterItem.getTitle());
        holder.binding.targetId.setText("Цель: " + counterItem.getTarget());
        holder.binding.progressId
                .setText(new StringBuilder()
                        .append("Прогресс: ")
                        .append(counterItem.getProgress())
                        .append("/")
                        .append(counterItem.getTarget())
                        .toString());
        holder.binding.deleteDBCounterItem.setOnClickListener(v -> {
            //getCounterItem(position);
            counterItemDao.delete(counterItem);
        });
    }

    public CounterItem getCounterItem(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CounterItemElementBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CounterItemElementBinding.bind(itemView);
        }
    }
}
