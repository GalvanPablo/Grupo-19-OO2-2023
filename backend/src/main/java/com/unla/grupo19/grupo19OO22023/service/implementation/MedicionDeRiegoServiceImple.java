package com.unla.grupo19.grupo19OO22023.service.implementation;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.grupo19.grupo19OO22023.Exception.MedicionDeRiegoServiceException;
import com.unla.grupo19.grupo19OO22023.dao.MedicionDeRiegoDAO;
import com.unla.grupo19.grupo19OO22023.entities.DispositivoDeRiego;
import com.unla.grupo19.grupo19OO22023.entities.Evento;
import com.unla.grupo19.grupo19OO22023.entities.MedicionDeRiego;

import com.unla.grupo19.grupo19OO22023.models.MedicionDeRiegoDTO;
import com.unla.grupo19.grupo19OO22023.service.DispositivoDeRiegoService;
import com.unla.grupo19.grupo19OO22023.service.IEventoService;
import com.unla.grupo19.grupo19OO22023.service.MedicionDeRiegoService;

import jakarta.transaction.Transactional;

@Service("serviceMedicionRiego")
public class MedicionDeRiegoServiceImple implements MedicionDeRiegoService {

	@Autowired
	@Qualifier("repositoryMedicionDeRiego")
	MedicionDeRiegoDAO medicionDeRiegoDAO;

	@Autowired
	@Qualifier("serviceDispositivoDeRiego")
	DispositivoDeRiegoService dispositivoDeRiegoService;

	@Autowired
	@Qualifier("serviceEvento")
	private IEventoService serviceEvento;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional()
	public void insert(MedicionDeRiego medicionDeRiego) throws Exception {

		if (medicionDeRiego == null)
			throw new MedicionDeRiegoServiceException("La medicion es null");
		if (medicionDeRiego.getDispositivo() == null)
			throw new MedicionDeRiegoServiceException("La medicion no tiene dispositivo asociado");

		medicionDeRiegoDAO.save(medicionDeRiego);

		// Verificacion si se genera un nuevo evento
		DispositivoDeRiego dispositivo = (DispositivoDeRiego) medicionDeRiego.getDispositivo();
		boolean estadoAnterior = dispositivo.isActivo();
		if (dispositivo.cambioDeEstado(medicionDeRiego)) {

			if (estadoAnterior && !dispositivo.isActivo()) {
				String descripcion = "El dispositivo de riego " + dispositivo.getNombre() + " de la zona"
						+ dispositivo.getZona().getNombre() + " dejo de regar.";
				this.serviceEvento.insert(new Evento(dispositivo, medicionDeRiego, descripcion));
				this.dispositivoDeRiegoService.updateDispositivoDeRiego(dispositivo);// Guardo el cambio de estado del dispositivo
			} else if (!estadoAnterior && dispositivo.isActivo()) {
				String descripcion = "El dispositivo de riego " + dispositivo.getNombre() + " de la "
						+ dispositivo.getZona().getNombre() + " ha empezado a regar.";
				this.serviceEvento.insert(new Evento(dispositivo, medicionDeRiego, descripcion));
				this.dispositivoDeRiegoService.updateDispositivoDeRiego(dispositivo);// Guardo el cambio de estado del dispositivo
			}
		}

	}

	@Override
	public MedicionDeRiegoDTO findByIdMedicion(int idMedicion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MedicionDeRiegoDTO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
