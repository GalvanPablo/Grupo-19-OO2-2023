package com.unla.grupo19.grupo19OO22023.models;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class DispositivoEstacionamientoModelNoZona {
    private int idDispositivo;
    private String nombre;
    private Set<Integer> plazas;
    

    @Override
    public String toString() {
        return "{\n" +
            "   idDispositivo: " + idDispositivo + ",\n" +
            "   nombre: '" + nombre + '\'' + ",\n" +
            "   plazas: " + plazas + "\n" +
            "}";
    }
}
