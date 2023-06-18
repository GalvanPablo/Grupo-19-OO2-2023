package com.unla.grupo19.grupo19OO22023.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.unla.grupo19.grupo19OO22023.entities.MedicionEstacionamiento;
import java.io.Serializable;

@Repository("repositoryMedicionEstacionamiento")
public interface IMedicionEstacionamientoRepository extends JpaRepository<MedicionEstacionamiento, Serializable> {
    
    // Por IdMedicion
    public abstract MedicionEstacionamiento findByIdMedicion(int idMedicion);

}
