package com.unla.grupo19.grupo19OO22023.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class MedicionEstacionamiento_Info_Model {
    private int idMedicion;
    private int idDispositivo;
    private int idZona;
    private int nroPlaza;
    private boolean ocupado;
    private LocalDateTime fechaHoraRegistro;

}
