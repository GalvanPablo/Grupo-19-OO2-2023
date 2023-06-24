package com.unla.grupo19.grupo19OO22023.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.grupo19.grupo19OO22023.Exception.DispositivoDeRiegoServiceException;
import com.unla.grupo19.grupo19OO22023.dao.DispositivoDeRiegoDAO;
import com.unla.grupo19.grupo19OO22023.entities.DispositivoDeRiego;
import com.unla.grupo19.grupo19OO22023.entities.Zona;
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
		
		Optional<DispositivoDeRiego> dispositivo = dispositivoDeRiegoDAO.buscarPorNombre(dispositivoDeRiegoDTO.getNombre());
		
		if(dispositivo.isPresent()) {
			throw new DispositivoDeRiegoServiceException("El nombre del dispositivo que quiere agregar ya esta ocupado, porfavor ingrese otro");
		}

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
		
		Optional<DispositivoDeRiego> dispositivo = dispositivoDeRiegoDAO.buscarPorNombre(dispositivoDeRiegoDTO.getNombre());
		DispositivoDeRiego loadedDispositivo = this.loadDispositivoDeRiegoById(idDispositivo);
		
		if(!dispositivo.isEmpty() ) {
			if(!loadedDispositivo.getNombre().equalsIgnoreCase(dispositivo.get().getNombre()))
			{
				if(dispositivo.isPresent() && dispositivo.get().getNombre().equalsIgnoreCase(dispositivoDeRiegoDTO.getNombre())) {
					throw new DispositivoDeRiegoServiceException("El nombre del dispositivo que quiere agregar ya esta ocupado, porfavor ingrese otro");
				}
			}
		}
		
		Zona zona = new Zona();
		modelMapper.map(dispositivoDeRiegoDTO.getZona(), zona);
		loadedDispositivo.setZona(zona);
		loadedDispositivo.setTemperatura(dispositivoDeRiegoDTO.getTemperatura());
		loadedDispositivo.setHumedad(dispositivoDeRiegoDTO.getHumedad());
		loadedDispositivo.setNombre(dispositivoDeRiegoDTO.getNombre());
		
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

	@Override
	public void updateDispositivoDeRiego(DispositivoDeRiego dispositivoDeRiego) {
		this.dispositivoDeRiegoDAO.save(dispositivoDeRiego);
	}

}
