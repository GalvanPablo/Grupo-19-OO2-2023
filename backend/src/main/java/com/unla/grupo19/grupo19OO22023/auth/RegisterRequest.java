package com.unla.grupo19.grupo19OO22023.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	private String nombre;
	private String apellido;
	private String email;
	private String password;

}