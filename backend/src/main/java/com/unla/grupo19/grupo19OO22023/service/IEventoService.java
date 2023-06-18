package com.unla.grupo19.grupo19OO22023.service;

import java.time.LocalDateTime;
import java.util.List;

import com.unla.grupo19.grupo19OO22023.entities.Evento;

public interface IEventoService {
    
    public Evento insert(Evento evento) throws Exception;

    public List<Evento> getAll();
    
    public Evento findByIdEvento(int idEvento) throws Exception;

    public Evento findByDescripcion(String descripcion) throws Exception;

    public List<Evento> findByFechaHoraRegistroBetween(LocalDateTime fechaHoraDesde, LocalDateTime fechaHoraHasta) throws Exception;

    public List<Evento> findAllByDispositivoIdDispositivo(int idDispositivo) throws Exception;

}
