package com.unla.grupo19.grupo19OO22023.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class MedicionEstacionamiento_New_Model {
    private int idDispositivo;
    private int nroPlaza;
    private boolean ocupado;

    @Override
    public String toString() {
        return "MedicionEstacionamiento_New_Model { idDispositivo: " + idDispositivo + ", nroPlaza: " + nroPlaza + ", ocupado: " + ocupado + "}";
    }
}
