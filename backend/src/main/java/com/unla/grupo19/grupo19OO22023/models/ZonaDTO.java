package com.unla.grupo19.grupo19OO22023.models;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ZonaDTO {

	@NotNull(message = "El id de Zona no puede ser nulo")
	private Integer idZona;
	
	@NotNull(message = "La zona tiene que tener un nombre")
	@Size(min = 5,max = 60, message = "El nombre tiene que tener entre 5 y 60 caracteres")
	private String nombre;	
}
