package com.unla.grupo19.grupo19OO22023.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.grupo19.grupo19OO22023.entities.Zona;
import java.io.Serializable;

@Repository("repositoryZona")
public interface IZonaRepository extends JpaRepository<Zona, Serializable> {
    
    public abstract Zona findByIdZona(int idZona);

    public abstract Zona findByNombre(String nombre);

}
