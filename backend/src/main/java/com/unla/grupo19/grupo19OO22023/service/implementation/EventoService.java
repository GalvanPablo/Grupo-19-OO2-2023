package com.unla.grupo19.grupo19OO22023.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.grupo19.grupo19OO22023.dao.IEventoRepository;
import com.unla.grupo19.grupo19OO22023.entities.Evento;
import com.unla.grupo19.grupo19OO22023.service.IEventoService;

@Service("serviceEvento")
public class EventoService implements IEventoService {

	@Autowired
	@Qualifier("repositoryEvento")
	private IEventoRepository repository;

	public static enum CodigoError {
		ID_INVALIDO, EVENTO_INVALIDO, DESCRIPCION_INVALIDA, DISPOSITIVO_INVALIDO, MEDICION_INVALIDA,
		DESDE_HASTA_INVALIDO, DESDE_HASTA_INVERTIDO,
	}

	@Override
	public Evento insert(Evento evento) throws Exception {
		if (evento == null)
			throw new Exception(CodigoError.EVENTO_INVALIDO.name()); // El evento es null
		if (evento.getDescripcion() == null || evento.getDescripcion().isEmpty())
			throw new Exception(CodigoError.DESCRIPCION_INVALIDA.name()); // El evento no tiene descripcion
		if (evento.getDispositivo() == null)
			throw new Exception(CodigoError.DISPOSITIVO_INVALIDO.name()); // El evento no tiene dispositivo
		if (evento.getMedicion() == null)
			throw new Exception(CodigoError.MEDICION_INVALIDA.name()); // El evento no tiene medicion
		return repository.save(evento);
	}

	@Override
	public List<Evento> getAll() {
		return repository.findAll();
	}

	@Override
	public Evento findByIdEvento(int idEvento) throws Exception {
		if (idEvento <= 0)
			throw new Exception(CodigoError.ID_INVALIDO.name()); // El id es invalido
		return repository.findByIdEvento(idEvento);
	}

	@Override
	public Evento findByDescripcion(String descripcion) throws Exception {
		if (descripcion == null || descripcion.isEmpty())
			throw new Exception(CodigoError.DESCRIPCION_INVALIDA.name()); // La descripcion es invalida
		return repository.findByDescripcion(descripcion);
	}

	@Override
	public List<Evento> findByFechaHoraRegistroBetween(LocalDateTime fechaHoraDesde, LocalDateTime fechaHoraHasta)
			throws Exception {
		if (fechaHoraDesde == null || fechaHoraHasta == null)
			throw new Exception(CodigoError.DESDE_HASTA_INVALIDO.name()); // La fechaHoraDesde o fechaHoraHasta es
																			// invalida
		if (fechaHoraDesde.isAfter(fechaHoraHasta))
			throw new Exception(CodigoError.DESDE_HASTA_INVERTIDO.name()); // La fechaHoraDesde es posterior a la
																			// fechaHoraHasta
		return repository.findByFechaHoraRegistroBetween(fechaHoraDesde, fechaHoraHasta);
	}

	@Override
	public List<Evento> findAllByDispositivoIdDispositivo(int idDispositivo) throws Exception {
		if (idDispositivo <= 0)
			throw new Exception(CodigoError.ID_INVALIDO.name()); // El id es invalido
		return repository.findAllByDispositivoIdDispositivo(idDispositivo);
	}

}
