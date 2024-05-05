package com.example.laboratorio4.fragmentos;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.laboratorio4.adapter.ClimaAdapter;
import com.example.laboratorio4.databinding.FragmentClimaBinding;
import com.example.laboratorio4.entity.Clima;
import com.example.laboratorio4.listeners.MagnetometerEventListener;
import com.example.laboratorio4.service.ClimaService;
import com.example.laboratorio4.viewModels.NavegationActivityViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClimaFragment extends Fragment {

    private ClimaAdapter climaAdapter;
    private FragmentClimaBinding binding;
    private ClimaService climaService;
    private SensorManager sensorManager;
    private MagnetometerEventListener magEventListener;
    private NavegationActivityViewModel navigationActivityViewModel;
    private String direction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClimaBinding.inflate(inflater, container, false);
        setupViews();
        return binding.getRoot();
    }

    private void setupViews() {
        setupRecyclerView();
        setupButton();
        createRetrofitService();
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.climaLista.setLayoutManager(layoutManager);
        navigationActivityViewModel = new ViewModelProvider(requireActivity()).get(NavegationActivityViewModel.class);
        climaAdapter = new ClimaAdapter(getContext());
        List<Clima> climas = navigationActivityViewModel.getClimas();
        climaAdapter.setClimas(climas);
        binding.climaLista.setAdapter(climaAdapter);
    }

    private void setupButton() {
        binding.button2.setOnClickListener(v -> {
            String longitud = binding.editTextText3.getText().toString().trim();
            String latitud = binding.editTextText2.getText().toString().trim();

            if (!longitud.isEmpty() && !latitud.isEmpty()) {
                binding.button2.setEnabled(false);
                navigationActivityViewModel.setEnableNavigation(false);
                cargarListaWebService();
                binding.editTextText3.setText("");
                binding.editTextText2.setText("");
                binding.editTextText3.requestFocus();
            } else {
                binding.editTextText3.requestFocus();
                Toast.makeText(requireContext(), "ingrese longitud y latitud", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createRetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        climaService = retrofit.create(ClimaService.class);
    }

    private void registerMagnetometerListener() {
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magnetometer != null) {
            magEventListener = new MagnetometerEventListener(new MagnetometerEventListener.SensorListenerCallback() {
                @Override
                public void onMagneticFieldChanged(float x, float y, float z) {
                    double radians = Math.atan2(y, x);
                    double degrees = Math.toDegrees(radians);
                    if (degrees < 0) {
                        degrees += 360;
                    }
                    direction = getDirection(degrees);
                    Log.d("Direccion", "Direccion: " + direction);
                }
            });
            sensorManager.registerListener(magEventListener, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void unregisterMagnetometerListener() {
        if (magEventListener != null) {
            sensorManager.unregisterListener(magEventListener);
        }
    }

    private String getDirection(double degrees) {
        int directionIndex = (int) ((degrees + 22.5) / 45) % 8;
        switch (directionIndex) {
            case 0:
                return "Oeste";
            case 1:
                return "Noroeste";
            case 2:
                return "Norte";
            case 3:
                return "Noreste";
            case 4:
                return "Este";
            case 5:
                return "Sureste";
            case 6:
                return "Sur";
            default:
                return "Suroeste";
        }
    }

    private void cargarListaWebService() {
        double latitudeclimaToSearch = Double.parseDouble(binding.editTextText2.getText().toString());
        double longitudeclimaToSearch = Double.parseDouble(binding.editTextText3.getText().toString());

        climaService.getClimaDetalle(latitudeclimaToSearch, longitudeclimaToSearch, "8dd6fc3be19ceb8601c2c3e811c16cf1").enqueue(new Callback<Clima>() {
            @Override
            public void onResponse(Call<Clima> call, Response<Clima> response) {
                binding.button2.setEnabled(true);
                if (response.isSuccessful()) {
                    Clima clima = response.body();
                    List<Clima> climas = navigationActivityViewModel.getClimas();
                    if (climas == null) {
                        climas = new ArrayList<>();
                    }
                    for (Clima c : climas) {
                        Log.d("Dato", "Clima: " + c.getName() + ", Latitud: " + c.getCoord().getLat() + ", Longitud: " + c.getCoord().getLon());
                    }
                    if (clima != null) {
                        clima.getWind().setDirection(direction);
                        climas.add(0, clima);
                        climaAdapter.setClimas(climas);
                        navigationActivityViewModel.setClimas(climas);
                        navigationActivityViewModel.setEnableNavigation(true);
                    } else {
                        Toast.makeText(requireContext(), "No hay datos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("ErrorResponse", errorBody);
                        Toast.makeText(requireContext(), "Error Consulta: " + errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Clima> call, Throwable t) {
                binding.button2.setEnabled(true);
                Toast.makeText(requireContext(), "Error Consulta", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
