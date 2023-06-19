package com.unla.grupo19.grupo19OO22023.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.unla.grupo19.grupo19OO22023.dao.UserRepository;
import com.unla.grupo19.grupo19OO22023.entities.Role;
import com.unla.grupo19.grupo19OO22023.entities.User;

@Component
public class DataInitializer implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {
		// Crear usuarios solo si no existen en la base de datos
		if (userRepository.findByEmail("Gustavo_admin@gmail.com").isEmpty()) {
			User admin1 = new User();
			admin1.setNombre("Gustavo");
			admin1.setApellido("Siciliano");
			admin1.setEmail("GustavoHernanSiciliano@gmail.com");
			admin1.setPassword(passwordEncoder.encode("123"));
			admin1.setRole(Role.ADMIN);
			userRepository.save(admin1);
		}

		if (userRepository.findByEmail("AlejandraVranic@gmail.com").isEmpty()) {
			User admin2 = new User();
			admin2.setNombre("Alejandra");
			admin2.setApellido("Vranic");
			admin2.setEmail("AlejandraVranic@gmail.com");
			admin2.setPassword(passwordEncoder.encode("123"));
			admin2.setRole(Role.ADMIN);
			userRepository.save(admin2);
		}
		
		if (userRepository.findByEmail("AndresEzequielCupo@gmail.com").isEmpty()) {
			User admin3 = new User();
			admin3.setNombre("Andres");
			admin3.setApellido("Cupo");
			admin3.setEmail("AndresEzequielCupo@gmail.com");
			admin3.setPassword(passwordEncoder.encode("123"));
			admin3.setRole(Role.ADMIN);
			userRepository.save(admin3);
		}
		
		if (userRepository.findByEmail("admin2@example.com").isEmpty()) {
			User admin4 = new User();
			admin4.setNombre("Pablo");
			admin4.setApellido("Galvan");
			admin4.setEmail("PabloGalvan@gmail.com");
			admin4.setPassword(passwordEncoder.encode("123"));
			admin4.setRole(Role.ADMIN);
			userRepository.save(admin4);
		}
		
		if (userRepository.findByEmail("SebastianMarioni@example.com").isEmpty()) {
			User admin5 = new User();
			admin5.setNombre("Sebastian");
			admin5.setApellido("Marioni");
			admin5.setEmail("SebastianMarioni@gmail.com");
			admin5.setPassword(passwordEncoder.encode("123"));
			admin5.setRole(Role.ADMIN);
			userRepository.save(admin5);
		}
		
		if (userRepository.findByEmail("ExampleAuditor@example.com").isEmpty()) {
			User auditor1 = new User();
			auditor1.setNombre("Auditor");
			auditor1.setApellido("Auditor");
			auditor1.setEmail("Auditor@gmail.com");
			auditor1.setPassword(passwordEncoder.encode("123"));
			auditor1.setRole(Role.AUDITOR);
			userRepository.save(auditor1 );
		}
	}

}
