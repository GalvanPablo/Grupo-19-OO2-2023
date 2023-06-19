package com.unla.grupo19.grupo19OO22023.simulation;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.unla.grupo19.grupo19OO22023.dao.IDispositivoEstacionamientoRepository;
import com.unla.grupo19.grupo19OO22023.dao.IZonaRepository;
import com.unla.grupo19.grupo19OO22023.entities.DispositivoEstacionamiento;
import com.unla.grupo19.grupo19OO22023.entities.Zona;

@Component
public class PrecargaDispositivosEstacionamiento implements CommandLineRunner {

    @Autowired
    private IDispositivoEstacionamientoRepository repository;

    @Autowired
    private IZonaRepository zonaRepository;

    @Override
    public void run(String... args) throws Exception {

        List<Zona> zonas = zonaRepository.findAll();

        int cantDispositivos = 0;
        if (!zonas.isEmpty()) {
            for (Zona zona : zonas) {
                try {
                    repository.save(new DispositivoEstacionamiento("E_00" + cantDispositivos, zona, Set.of(1, 2)));
                    cantDispositivos++;

                    repository.save(new DispositivoEstacionamiento("E_00" + cantDispositivos, zona, Set.of(3, 4)));
                    cantDispositivos++;
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }

    }

}
