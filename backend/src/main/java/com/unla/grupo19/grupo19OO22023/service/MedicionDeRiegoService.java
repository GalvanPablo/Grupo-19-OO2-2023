package com.unla.grupo19.grupo19OO22023.service;

import java.util.List;

import com.unla.grupo19.grupo19OO22023.entities.MedicionDeRiego;
import com.unla.grupo19.grupo19OO22023.models.MedicionDeRiegoDTO;

public interface MedicionDeRiegoService {

	public void insert(MedicionDeRiego medicionDeRiego) throws Exception;
	
	public List<MedicionDeRiegoDTO> getAll();
	
	public MedicionDeRiegoDTO findByIdMedicion(int idMedicion);
}
