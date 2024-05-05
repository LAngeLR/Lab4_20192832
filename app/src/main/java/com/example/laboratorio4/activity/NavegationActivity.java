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
import com.example.laboratorio4.fragmentos.ClimaFragment;
import com.example.laboratorio4.fragmentos.GeolocalizacionFragment;
import com.example.laboratorio4.viewModels.NavegationActivityViewModel;


public class NavegationActivity extends AppCompatActivity {

    private NavegationActivityViewModel navigationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Button locationButton = findViewById(R.id.buttonToGeolocalizacion);
        Button weatherButton = findViewById(R.id.buttonToClima);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, new GeolocalizacionFragment());
        transaction.commit();

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, new GeolocalizacionFragment())
                        .commit();
            }
        });

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, new ClimaFragment())
                        .commit();
            }
        });


        NavegationActivityViewModel navigationViewModel = new ViewModelProvider(this).get(NavegationActivityViewModel.class);


        navigationViewModel.getEnableNavigation().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean enable) {

                locationButton.setEnabled(enable);
                weatherButton.setEnabled(enable);

                int backgroundColor = enable ? android.R.color.white : android.R.color.darker_gray;
                locationButton.setBackgroundColor(ContextCompat.getColor(NavegationActivity.this, backgroundColor));
                weatherButton.setBackgroundColor(ContextCompat.getColor(NavegationActivity.this, backgroundColor));
            }
        });
    }
}
