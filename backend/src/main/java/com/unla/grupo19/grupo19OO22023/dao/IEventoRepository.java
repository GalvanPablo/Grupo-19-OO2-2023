package com.unla.grupo19.grupo19OO22023.dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.grupo19.grupo19OO22023.entities.Evento;

@Repository("repositoryEvento")
public interface IEventoRepository extends JpaRepository<Evento, Serializable> {
    
    public abstract Evento findByIdEvento(int idEvento);

    public abstract Evento findByDescripcion(String descripcion);

    public abstract List<Evento> findByFechaHoraRegistroBetween(LocalDateTime fechaHoraDesde, LocalDateTime fechaHoraHasta);

    public abstract List<Evento> findAllByDispositivoIdDispositivo(int idDispositivo);

}