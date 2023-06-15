package com.unla.grupo19.grupo19OO22023.models;

import java.util.Set;

import com.unla.grupo19.grupo19OO22023.entities.Zona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class DispositivoEstacionamientoModel {

    private int idDispositivo;
    private String nombre;
    private Zona zona;
    private Set<Integer> plazas;
    

    @Override
    public String toString() {
        return "{\n" +
            "   idDispositivo: " + idDispositivo + ",\n" +
            "   nombre: '" + nombre + '\'' + ",\n" +
            "   zona: " + zona + ",\n" +
            "   plazas: " + plazas + "\n" +
            "}";
    }

}
