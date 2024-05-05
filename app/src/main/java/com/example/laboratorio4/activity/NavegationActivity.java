package com.example.laboratorio4.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.laboratorio4.R;
import com.example.laboratorio4.databinding.ActivityNavigationBinding;
import com.example.laboratorio4.fragmentos.ClimaFragment;
import com.example.laboratorio4.fragmentos.GeolocalizacionFragment;
import com.example.laboratorio4.viewModels.NavegationActivityViewModel;


public class NavegationActivity extends AppCompatActivity {

    private NavegationActivityViewModel navigationActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ActivityNavigationBinding binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.fragmentContainerView.getId(), new GeolocalizacionFragment());
        transaction.commit();

        binding.buttonToGeolocalizacion.setOnClickListener(view -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(binding.fragmentContainerView.getId(), new GeolocalizacionFragment())
                    .commit();
        });

        binding.buttonToClima.setOnClickListener(view -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(binding.fragmentContainerView.getId(), new ClimaFragment())
                    .commit();
        });


        navigationActivityViewModel = new ViewModelProvider(this).get(NavegationActivityViewModel.class);
        navigationActivityViewModel.getEnableNavigation().observe(this, enable -> {
            binding.buttonToGeolocalizacion.setEnabled(enable);
            binding.buttonToClima.setEnabled(enable);

            if (enable) {
                binding.buttonToGeolocalizacion.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
                binding.buttonToClima.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
            } else {
                binding.buttonToGeolocalizacion.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
                binding.buttonToClima.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
            }
        });
    }
}
