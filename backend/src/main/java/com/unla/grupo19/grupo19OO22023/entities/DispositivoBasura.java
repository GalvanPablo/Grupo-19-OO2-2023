package com.unla.grupo19.grupo19OO22023.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "idDispositivo")
@Setter @Getter @NoArgsConstructor
public class DispositivoBasura extends Dispositivo{
    
}
