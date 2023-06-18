package com.unla.grupo19.grupo19OO22023.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.grupo19.grupo19OO22023.Exception.DispositivoDeRiegoServiceException;
import com.unla.grupo19.grupo19OO22023.dao.DispositivoDeRiegoDAO;
import com.unla.grupo19.grupo19OO22023.entities.DispositivoDeRiego;
import com.unla.grupo19.grupo19OO22023.models.DispositivoDeRiegoDTO;
import com.unla.grupo19.grupo19OO22023.service.DispositivoDeRiegoService;

@Service("serviceDispositivoDeRiego")
public class DispositivoDeRiegoServiceImple implements DispositivoDeRiegoService {

	@Autowired
	@Qualifier("repositoryDispositivoDeRiego")
	private DispositivoDeRiegoDAO dispositivoDeRiegoDAO;
	
	private ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional()
	public DispositivoDeRiegoDTO createDispositivoDeRiego(DispositivoDeRiegoDTO dispositivoDeRiegoDTO) {

		DispositivoDeRiego newDispositivoDeRiego = new DispositivoDeRiego();
		modelMapper.map(dispositivoDeRiegoDTO, newDispositivoDeRiego);
		DispositivoDeRiego dispositivoGuardado = this.dispositivoDeRiegoDAO.save(newDispositivoDeRiego);
		modelMapper.map(dispositivoGuardado,dispositivoDeRiegoDTO );
		return dispositivoDeRiegoDTO;
	}

	@Override
	public DispositivoDeRiego loadDispositivoDeRiegoById(Long idDispositivo) {

		return this.dispositivoDeRiegoDAO.findById(idDispositivo)
				.orElseThrow(() -> new DispositivoDeRiegoServiceException("Dispositivo con ID: " + idDispositivo + " no se encontro"));

	}

	@Override
	@Transactional()
	public DispositivoDeRiegoDTO updateDispositivoDeRiego(DispositivoDeRiegoDTO dispositivoDeRiegoDTO, Long idDispositivo) {
		
		DispositivoDeRiego loadedDispositivo = this.loadDispositivoDeRiegoById(idDispositivo);
	    modelMapper.map(dispositivoDeRiegoDTO, loadedDispositivo);
	    DispositivoDeRiego updatedDispositivoDeRiego = this.dispositivoDeRiegoDAO.save(loadedDispositivo);
	    modelMapper.map(updatedDispositivoDeRiego, dispositivoDeRiegoDTO);
	    
	    return dispositivoDeRiegoDTO;
	}

	@Override
	@Transactional()
	public void deleteDispositivoDeRiego(Long idDispositivo) {
		
		DispositivoDeRiego dispositivoDeRiego = this.loadDispositivoDeRiegoById(idDispositivo);
		dispositivoDeRiego.setBaja(true);
		this.dispositivoDeRiegoDAO.save(dispositivoDeRiego);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DispositivoDeRiegoDTO> findAllActiveDispositivoDeRiego() {
		
		return this.dispositivoDeRiegoDAO.findByBajaFalse().stream()
				.map((dispositivo)-> this.modelMapper.map(dispositivo, DispositivoDeRiegoDTO.class))
				.collect(Collectors.toList());
	}

}
