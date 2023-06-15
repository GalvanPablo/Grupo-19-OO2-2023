package com.unla.grupo19.grupo19OO22023.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Evento_Info_Model {
    private int idEvento;
    private String descripcion;
    private int idMedicion;
    private int idDispositivo;
    private LocalDateTime fechaHoraRegistro;
}
