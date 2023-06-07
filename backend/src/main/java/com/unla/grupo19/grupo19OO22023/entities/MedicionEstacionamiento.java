package com.unla.grupo19.grupo19OO22023.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "idMedicion")
@Getter @NoArgsConstructor
public class MedicionEstacionamiento extends Medicion {

    @Column(nullable = false)
    private int nroPlaza;

    @Column(nullable = false)
    private boolean ocupado;
    
    public MedicionEstacionamiento(DispositivoEstacionamiento dispositivo, int nroPlaza, boolean ocupado) throws Exception {
        super(dispositivo);
        setNroPlaza(nroPlaza);
        this.ocupado = ocupado;
    }

    private void setNroPlaza(int nroPlaza) throws Exception {
        if(nroPlaza <= 0) throw new Exception("El numero de plaza debe ser mayor a 0");
        this.nroPlaza = nroPlaza;
    }


    @Override
    public String toString(){
        return "MedicionEstacionamiento {" +
            "idMedicion=" + idMedicion +
            ", dispositivo=" + dispositivo.getIdDispositivo() +
            ", fechaHora=" + fechaHoraRegistro +
            ", nroPlaza=" + nroPlaza +
            ", ocupado=" + ocupado +
            "}";
    }
}
