package com.example.laboratorio4.fragmentos;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.laboratorio4.R;
import com.example.laboratorio4.adapter.CiudadAdapter;
import com.example.laboratorio4.databinding.FragmentGeolocalizacionBinding;
import com.example.laboratorio4.entity.Ciudad;
import com.example.laboratorio4.listeners.AcelerometerEventListener;
import com.example.laboratorio4.service.CiudadService;
import com.example.laboratorio4.viewModels.NavegationActivityViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeolocalizacionFragment extends Fragment {

    private FragmentGeolocalizacionBinding binding;
    private CiudadAdapter ciudadAdapter;
    private NavegationActivityViewModel navegationActivityViewModel;
    private CiudadService ciudadService;
    private static final float UMBRAL_ACCELERACION = 10.0f;
    private SensorManager sensorManager;
    private AcelerometerEventListener sensorEventListener;
    private boolean isDialogVisible = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGeolocalizacionBinding.inflate(inflater, container, false);
        setupViews();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerAccelerometerListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterAccelerometerListener();
    }

    private void setupViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.ciudadesLista.setLayoutManager(layoutManager);
        navegationActivityViewModel = new ViewModelProvider(requireActivity()).get(NavegationActivityViewModel.class);
        ciudadAdapter = new CiudadAdapter(getContext());
        binding.ciudadesLista.setAdapter(ciudadAdapter);
        binding.button3.setOnClickListener(v -> onClickButton());
        createRetrofitServiceAndAccelerometerListener();
    }

    private void onClickButton() {
        String ciudad = binding.editTextText.getText().toString().trim();
        if (!ciudad.isEmpty()) {
            binding.button3.setEnabled(false);
            navegationActivityViewModel.setEnableNavigation(false);
            cargarListaWebService(ciudad);
            binding.editTextText.setText("");
            binding.editTextText.requestFocus();
        } else {
            binding.editTextText.requestFocus();
            Toast.makeText(requireContext(), "Ingresa una ciudad", Toast.LENGTH_SHORT).show();
        }
    }

    private void createRetrofitServiceAndAccelerometerListener() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ciudadService = retrofit.create(CiudadService.class);

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorEventListener = new AcelerometerEventListener(accelerationTotal -> {
            if (accelerationTotal > UMBRAL_ACCELERACION && !isDialogVisible) {
                mostrarDialogoDeshacer();
            }
        });
        registerAccelerometerListener();
    }

    private void registerAccelerometerListener() {
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void unregisterAccelerometerListener() {
        sensorManager.unregisterListener(sensorEventListener);
    }

    private void cargarListaWebService(String ciudad) {
        ciudadService.getCiudadDetalle(ciudad, 1, "8dd6fc3be19ceb8601c2c3e811c16cf1").enqueue(new Callback<List<Ciudad>>() {
            @Override
            public void onResponse(Call<List<Ciudad>> call, Response<List<Ciudad>> response) {
                binding.button3.setEnabled(true);
                if (response.isSuccessful()) {
                    List<Ciudad> city = response.body();
                    if (city != null && !city.isEmpty()) {
                        ciudadAdapter.setCiudades(city);
                    } else {
                        Toast.makeText(requireContext(), "No hay datos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Error Consulta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ciudad>> call, Throwable t) {
                binding.button3.setEnabled(true);
                Toast.makeText(requireContext(), "Error Consulta", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarDialogoDeshacer() {
        isDialogVisible = true;
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Eliminar Ciudad")
                .setMessage("¿Deseas eliminar la ultima ciudad?")
                .setPositiveButton("eliminar", (dialogInterface, i) -> {
                    eliminarUltimaCiudad();
                    isDialogVisible = false;
                })
                .setNegativeButton("Cancelar", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    isDialogVisible = false;
                })
                .setOnDismissListener(dialogInterface -> isDialogVisible = false)
                .show();
    }

    private void eliminarUltimaCiudad() {
        List<Ciudad> cities = navegationActivityViewModel.getCiudades();
        if (cities != null && !cities.isEmpty()) {
            cities.remove(0);
            ciudadAdapter.setCiudades(cities);
            navegationActivityViewModel.setCiudades(cities);
        }
    }
}
