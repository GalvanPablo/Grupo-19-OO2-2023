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

import com.unla.grupo19.grupo19OO22023.dao.IDispositivoEstacionamientoRepository;
import com.unla.grupo19.grupo19OO22023.entities.DispositivoEstacionamiento;
import com.unla.grupo19.grupo19OO22023.entities.MedicionEstacionamiento;
import com.unla.grupo19.grupo19OO22023.service.implementation.MedicionEstacionamientoService;

@Component
public class SimulacionMedicionesEstacionamiento {

    // CODIGO MERAMENTE ESTETICO PARA DAR COLOR AL TEXTO EN LA CONSOLA //
    private String imprimir(String text) {
        return "\n\n\u001B[36m" + text + "\u001B[0m\n";
    }
    /////////////////////////////////////////////////////////////////////
    
    @Autowired
    @Qualifier("serviceMedicionEstacionamiento")
    MedicionEstacionamientoService service;

    @Autowired
    @Qualifier("repositoryDispositivoEstacionamiento")
    private IDispositivoEstacionamientoRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(SimulacionMedicionesEstacionamiento.class);

    private Random random = new Random();
    private int getRandomInt(int min, int max){
		return random.nextInt(max - min) + min;
	}

    @Scheduled(fixedRate = 10000) // Genera datos cada 10 segundos
    public void generateMedicionesEstacionamiento(){        
        try {
            List<DispositivoEstacionamiento> dispositivosEstacionamiento = new ArrayList<DispositivoEstacionamiento>();
            dispositivosEstacionamiento = repository.findByBajaFalse();
            
            if(!dispositivosEstacionamiento.isEmpty()){
                int dispositivoIndex = getRandomInt(0, dispositivosEstacionamiento.size());
                DispositivoEstacionamiento dispositivo = dispositivosEstacionamiento.get(dispositivoIndex);

                int plazaIndex = getRandomInt(0, dispositivo.getPlazas().size());
                List<Integer> plazas = new ArrayList<Integer>(dispositivo.getPlazas());
                int nroPlaza = plazas.get(plazaIndex);
            
                boolean ocupado = random.nextBoolean();

                MedicionEstacionamiento medicionNueva = new MedicionEstacionamiento(
                    dispositivo,
                    nroPlaza,
                    ocupado
                );

                if(service.insert(medicionNueva) != null){
                    logger.info(imprimir("Se agrego una nueva medicion de estacionamiento para el dispositivo estacionamiento: {id: " + dispositivo.getIdDispositivo() + ", nombre: " + dispositivo.getNombre() + "}\n"));
                } else {
                    logger.info(imprimir("No se ha podido crear una nueva medicion de estacionamiento para el dispositivo estacionamiento: {id: " + dispositivo.getIdDispositivo() + ", nombre: " + dispositivo.getNombre() + "}\n"));
                }
            } else {
                logger.info(imprimir("No hay dispositivos de estacionamiento en la base de datos"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
