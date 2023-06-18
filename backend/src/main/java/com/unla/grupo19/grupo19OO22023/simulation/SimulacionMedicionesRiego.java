package com.unla.grupo19.grupo19OO22023.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.unla.grupo19.grupo19OO22023.dao.DispositivoDeRiegoDAO;
import com.unla.grupo19.grupo19OO22023.entities.DispositivoDeRiego;
import com.unla.grupo19.grupo19OO22023.entities.MedicionDeRiego;
import com.unla.grupo19.grupo19OO22023.service.MedicionDeRiegoService;

@Component
public class SimulacionMedicionesRiego {

	@Autowired
	@Qualifier("serviceMedicionRiego")
	MedicionDeRiegoService medicionDeRiegoService;

	@Autowired
	@Qualifier("repositoryDispositivoDeRiego")
	private DispositivoDeRiegoDAO dispositivoDeRiegoDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(SimulacionMedicionesRiego.class);

	private Random random = new Random();
	private float humedad;
	private float temperatura;

	@Scheduled(fixedRate = 10000) // Genera datos cada 10 segundos
	public void generateMedicionDeRiego() {

		List<DispositivoDeRiego> dispositivosDeRiego = new ArrayList<>();

		dispositivosDeRiego = dispositivoDeRiegoDAO.findByBajaFalse();
		
		for (DispositivoDeRiego dispositivo : dispositivosDeRiego) {
			this.humedad = random.nextFloat(101); // genera un valor aleatorio entre 0 y 100
			this.temperatura = -50 + (random.nextFloat() * 100); // genera un valor aleatorio entre -50 y 50 grados Celsius
			MedicionDeRiego medicionNueva = new MedicionDeRiego(dispositivo, humedad, temperatura);

			try {
				logger.info("Se agrego una nueva medicion para el siguiente dispositivo: \n " + dispositivo.toString());
				medicionDeRiegoService.insert(medicionNueva);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
