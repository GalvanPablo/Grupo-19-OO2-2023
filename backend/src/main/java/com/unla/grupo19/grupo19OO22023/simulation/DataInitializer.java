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
	            admin1.setPassword(passwordEncoder.encode("adminPassword1"));
	            admin1.setRole(Role.ADMIN);
	            userRepository.save(admin1);
	        }

	        if (userRepository.findByEmail("admin2@example.com").isEmpty()) {
	            User admin2 = new User();
	            admin2.setNombre("Alejandra");
	            admin2.setApellido("Vranic");
	            admin2.setEmail("AlejandraVranic@gmail.com");
	            admin2.setPassword(passwordEncoder.encode("adminPassword2"));
	            admin2.setRole(Role.ADMIN);
	            userRepository.save(admin2);
	        }
	    }

}
