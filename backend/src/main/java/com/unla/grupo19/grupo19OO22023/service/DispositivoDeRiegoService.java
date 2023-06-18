package com.unla.grupo19.grupo19OO22023.service;

import java.util.List;

import com.unla.grupo19.grupo19OO22023.entities.DispositivoDeRiego;
import com.unla.grupo19.grupo19OO22023.models.DispositivoDeRiegoDTO;

public interface DispositivoDeRiegoService {

	public DispositivoDeRiegoDTO createDispositivoDeRiego(DispositivoDeRiegoDTO dispositivoDeRiegoDTO);

	public DispositivoDeRiegoDTO updateDispositivoDeRiego(DispositivoDeRiegoDTO dispositivoDeRiegoDTO, Long idDispositivo);

	public void deleteDispositivoDeRiego(Long idDispositivo);

	public List<DispositivoDeRiegoDTO> findAllActiveDispositivoDeRiego();
	
	public DispositivoDeRiego loadDispositivoDeRiegoById(Long idDispositivo);

}
