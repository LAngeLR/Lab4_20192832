package com.example.laboratorio4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.laboratorio4.databinding.ItemClimaBinding;
import com.example.laboratorio4.entity.Clima;
import java.util.ArrayList;
import java.util.List;

public class ClimaAdapter extends RecyclerView.Adapter<ClimaAdapter.ClimaViewHolder> {

    private List<Clima> climaList;
    private Context context;

    public ClimaAdapter(Context context) {
        this.context = context;
        this.climaList = new ArrayList<>();
    }

    public void setClimas(List<Clima> climas) {
        this.climaList = climas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClimaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemClimaBinding itemBinding = ItemClimaBinding.inflate(layoutInflater, parent, false);
        return new ClimaViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ClimaViewHolder holder, int position) {
        Clima clima = climaList.get(position);
        holder.bind(clima);
    }

    @Override
    public int getItemCount() {
        return climaList.size();
    }

    public static class ClimaViewHolder extends RecyclerView.ViewHolder {
        private final ItemClimaBinding binding;

        public ClimaViewHolder(ItemClimaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Clima clima) {
            binding.ciudad.setText(clima.getName().isEmpty() ? "Desconocido" : clima.getName());
            binding.temperatura.setText(String.valueOf(clima.getMain().getTemp()) + "K");
            binding.Minima.setText(String.valueOf(clima.getMain().getTemp_min()) + "K");
            binding.Maxima.setText(String.valueOf(clima.getMain().getTemp_max()) + "K");
            binding.Viento.setText(String.valueOf(clima.getWind().getDirection()));
        }
    }
}
