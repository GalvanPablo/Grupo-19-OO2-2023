package com.unla.grupo19.grupo19OO22023.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
public abstract class Medicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMedicion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDispositivo", nullable = false)
    private Dispositivo dispositivo;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime fechaHoraRegistro;


    // CONSTRUCTOR
    public Medicion(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

}
