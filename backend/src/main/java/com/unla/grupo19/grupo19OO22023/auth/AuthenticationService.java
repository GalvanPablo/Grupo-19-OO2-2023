package com.unla.grupo19.grupo19OO22023.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unla.grupo19.grupo19OO22023.config.JwtService;
import com.unla.grupo19.grupo19OO22023.dao.UserRepository;
import com.unla.grupo19.grupo19OO22023.entities.Role;
import com.unla.grupo19.grupo19OO22023.entities.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder().nombre(request.getNombre()).apellido(request.getApellido()).email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword())).role(Role.AUDITOR).build();
		repository.save(user);

		// Adjuntar el rol del usuario en el token
		Map<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("roles", user.getRole().name());

		var jwtToken = jwtService.generateToken(extraClaims, user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		// https://youtu.be/KxqlJblhzfI?t=7404

		var user = repository.findByEmail(request.getEmail()).orElseThrow();

		// Adjuntar el rol del usuario en el token
		Map<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("roles", user.getRole().name());

		var jwtToken = jwtService.generateToken(extraClaims, user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

}