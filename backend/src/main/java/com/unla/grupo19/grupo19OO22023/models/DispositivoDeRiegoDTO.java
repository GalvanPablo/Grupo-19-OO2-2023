package com.unla.grupo19.grupo19OO22023.models;


import org.hibernate.validator.constraints.Range;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DispositivoDeRiegoDTO {

	@NotNull(message = "El dispositivo tiene que tener un ID")
	private int idDispositivo;
	
	@NotNull(message = "El dispositivo tiene que tener un nombre")
	@Size(min = 5, max = 60, message = "El nombre del Dispositivo tiene que tener entre 5 y 60 caracteres")
	private String nombre;
	
	@NotNull(message = "El dispositivo tiene que tener una zona asignada")
	@Valid
	private ZonaDTO zona;
	
	@NotNull(message = "El dispositivo tiene que tener una medida de humedad")
	@Range(min = 1, max = 100, message = "El valor de configuracion de humeddad solo acepta valores entre 1  a 100 g/m³")
	private float humedad;
	
	@NotNull(message = "El dispositivo tiene que tener una medida de temperatura")
	@Range(min = 1, max = 50, message = "El valor de la temperatura solo acepta valore entre 1 a 50 grados ºC")
	private float temperatura;
	
	private boolean activo;
}
