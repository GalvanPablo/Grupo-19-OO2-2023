package com.unla.grupo19.grupo19OO22023.service;

import java.util.List;

import com.unla.grupo19.grupo19OO22023.entities.DispositivoEstacionamiento;
import com.unla.grupo19.grupo19OO22023.models.DispositivoEstacionamientoModel;
import com.unla.grupo19.grupo19OO22023.models.DispositivoEstacionamientoModelNoZona;

public interface IDispositivoEstacionamientoService {

    public DispositivoEstacionamientoModel insert(DispositivoEstacionamiento dispositivo) throws Exception;

    public DispositivoEstacionamientoModel update(int idDispositivo, DispositivoEstacionamiento dispositivoActualizado) throws Exception;

    public boolean remove(int idDispositivo) throws Exception; // Dar de baja lógica

    public List<DispositivoEstacionamientoModel> getAll();

    public DispositivoEstacionamientoModel findByIdDispositivo(int id) throws Exception;

    public DispositivoEstacionamiento findByIdDispositivoEntity(int id) throws Exception;

    public List<DispositivoEstacionamientoModelNoZona> findByZona(int idZona) throws Exception;

}
