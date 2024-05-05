package com.example.laboratorio4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratorio4.databinding.ItemGeolocalizacionBinding;
import com.example.laboratorio4.entity.Ciudad;

import java.util.ArrayList;
import java.util.List;

public class CiudadAdapter extends RecyclerView.Adapter<CiudadAdapter.CiudadViewHolder> {
    private List<Ciudad> ciudades;
    private Context context;

    public CiudadAdapter(Context context) {
        this.context = context;
        this.ciudades = new ArrayList<>();
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades.clear();
        this.ciudades.addAll(ciudades);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CiudadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGeolocalizacionBinding itemBinding = ItemGeolocalizacionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CiudadViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CiudadViewHolder holder, int position) {
        Ciudad ciudad = ciudades.get(position);
        holder.bind(ciudad);
    }


    @Override
    public int getItemCount() {
        return ciudades.size();
    }

    public class CiudadViewHolder extends RecyclerView.ViewHolder {
        private final ItemGeolocalizacionBinding binding;

        public CiudadViewHolder(ItemGeolocalizacionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Ciudad ciudad) {
            binding.ciudad.setText(ciudad.getName());
            binding.latitud.setText(String.valueOf(ciudad.getLat()));
            binding.longitud.setText(String.valueOf(ciudad.getLon()));
        }
    }
}
