package com.unla.grupo19.grupo19OO22023.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "idMedicion")
@Setter
@Getter
@NoArgsConstructor
public class MedicionDeRiego extends Medicion {

	@Column(nullable = false)
	private float humedadActual;

	@Column(nullable = false)
	private float temperaturaActual;

	public MedicionDeRiego(DispositivoDeRiego dispositivo, float humedadActual, float temperaturaActual) {
		super(dispositivo);
		this.humedadActual = humedadActual;
		this.temperaturaActual = temperaturaActual;
	}

	@Override
	public String toString() {
		return "MedicionDeRiego [humedadActual=" + humedadActual + ", temperaturaActual=" + temperaturaActual + "]";
	}

	

}
