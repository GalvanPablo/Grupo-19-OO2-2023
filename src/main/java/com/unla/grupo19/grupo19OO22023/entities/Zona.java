package com.unla.grupo19.grupo19OO22023.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idZona;

    @Column(nullable = false, length = 50)
    private String nombre;

    public Zona(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Zona { idZona: " + idZona + ", nombre: " + nombre + "}";
    }

}
