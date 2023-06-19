package com.unla.grupo19.grupo19OO22023.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.grupo19.grupo19OO22023.entities.MedicionDeRiego;

@Repository("repositoryMedicionDeRiego")
public interface MedicionDeRiegoDAO extends JpaRepository<MedicionDeRiego, Long> {

	// Por IdMedicion
    public abstract MedicionDeRiego findByIdMedicion(int idMedicion);
}
