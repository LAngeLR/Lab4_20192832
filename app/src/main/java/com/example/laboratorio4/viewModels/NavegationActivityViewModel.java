package com.example.laboratorio4.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laboratorio4.entity.Ciudad;
import com.example.laboratorio4.entity.Clima;

import java.util.List;

public class NavegationActivityViewModel extends ViewModel {
    private List<Clima> climas;
    private List<Ciudad> ciudades;
    private MutableLiveData<Boolean> enableNavigation = new MutableLiveData<>();

    public LiveData<Boolean> getEnableNavigation() {
        return enableNavigation;
    }

    public void setEnableNavigation(boolean enable) {
        enableNavigation.setValue(enable);
    }

    public List<Clima> getClimas() {
        return climas;
    }

    public void setClimas(List<Clima> climas) {
        this.climas = climas;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }
}
