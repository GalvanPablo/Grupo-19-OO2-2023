package com.unla.grupo19.grupo19OO22023.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter @NoArgsConstructor
public abstract class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int idDispositivo;

    @Column(nullable = false, length = 50)
    protected String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idZona", nullable = false)
    protected Zona zona;

    @CreationTimestamp
    @Column(nullable = false)
    protected LocalDateTime fechaHoraCreacion;

    @UpdateTimestamp
    @Column(nullable = false)
    protected LocalDateTime fechaHoraActualizacion;

    @Column(nullable = false)
    protected boolean baja;

    public Dispositivo(String nombre, Zona zona) {
        this.nombre = nombre;
        this.zona = zona;
        this.baja = false;
    }

    @Override
    public String toString(){
        return "idDispositivo: " + idDispositivo
                + ", nombre: " + nombre
                + ", zona: " + zona
                + ", fechaHoraCreacion: " + fechaHoraCreacion
                + ", fechaHoraActualizacion: " + fechaHoraActualizacion
                + ", baja: " + baja;
    }

}
