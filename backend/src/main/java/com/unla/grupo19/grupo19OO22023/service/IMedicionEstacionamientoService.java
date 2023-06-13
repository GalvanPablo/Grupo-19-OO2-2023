package com.unla.grupo19.grupo19OO22023.service;

import java.util.List;

import com.unla.grupo19.grupo19OO22023.entities.MedicionEstacionamiento;
import com.unla.grupo19.grupo19OO22023.models.MedicionEstacionamiento_Info_Model;

public interface IMedicionEstacionamientoService {
    
    public MedicionEstacionamiento_Info_Model insert(MedicionEstacionamiento medicion) throws Exception;

    public List<MedicionEstacionamiento_Info_Model> getAll();

    public MedicionEstacionamiento_Info_Model findByIdMedicion(int idMedicion) throws Exception;

    public List<MedicionEstacionamiento_Info_Model> getLastMeasurements();

}
