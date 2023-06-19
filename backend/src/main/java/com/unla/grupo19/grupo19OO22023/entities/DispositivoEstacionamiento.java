package com.unla.grupo19.grupo19OO22023.entities;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.JoinColumn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "idDispositivo")
@Setter @Getter @NoArgsConstructor
public class DispositivoEstacionamiento extends Dispositivo{
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dispositivo_estacionamiento_plazas", joinColumns = @JoinColumn(name = "dispositivo_estacionamiento_id_dispositivo"))
    @Column(name = "plaza")
    private Set<Integer> plazas;

    public DispositivoEstacionamiento(String nombre, Zona zona, Set<Integer> plazas) {
        super(nombre, zona);
        this.plazas = plazas;
    }

    // toString formato json
    @Override
    public String toString() {
        return "{\n   " +
            super.toString() + ",\n" +
            "   plazas=" + plazas + "\n" +
            "}";
    }
}
