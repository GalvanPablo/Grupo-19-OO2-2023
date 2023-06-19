package com.unla.grupo19.grupo19OO22023.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.unla.grupo19.grupo19OO22023.entities.Evento;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Repository("repositoryEvento")
public interface IEventoRepository extends JpaRepository<Evento, Serializable> {

	public abstract Evento findByIdEvento(int idEvento);

	public abstract Evento findByDescripcion(String descripcion);

	public abstract List<Evento> findByFechaHoraRegistroBetween(LocalDateTime fechaHoraDesde,
			LocalDateTime fechaHoraHasta);

	public abstract List<Evento> findAllByDispositivoIdDispositivo(int idDispositivo);

}
