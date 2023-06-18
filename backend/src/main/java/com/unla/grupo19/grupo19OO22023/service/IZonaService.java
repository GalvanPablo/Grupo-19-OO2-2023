package com.unla.grupo19.grupo19OO22023.service;

import java.util.List;

import com.unla.grupo19.grupo19OO22023.entities.Zona;

public interface IZonaService {
    
    public Zona insert(Zona zona) throws Exception;

    public List<Zona> getAll();

    public Zona findByIdZona(int idZona) throws Exception;

    public Zona findByNombre(String nombre) throws Exception;

}
