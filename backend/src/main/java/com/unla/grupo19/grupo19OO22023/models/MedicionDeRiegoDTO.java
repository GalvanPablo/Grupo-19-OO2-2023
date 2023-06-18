package com.unla.grupo19.grupo19OO22023.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class MedicionDeRiegoDTO {

	private int idMedicion;
	private DispositivoDeRiegoDTO dispositivo;
	private float humedadActual;
	private float temperaturaActual;
	private boolean activo;
	
}
