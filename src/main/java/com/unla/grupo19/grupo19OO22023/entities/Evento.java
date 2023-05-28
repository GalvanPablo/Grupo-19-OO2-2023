package com.unla.grupo19.grupo19OO22023.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Evento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEvento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDispositivo", nullable = false)
    private Dispositivo dispositivo;

    @Column(nullable = false, length = 150)
    private String descripcion;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime fechaHoraRegistro;

}
