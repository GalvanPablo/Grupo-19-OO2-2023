package com.unla.grupo19.grupo19OO22023.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.grupo19.grupo19OO22023.entities.DispositivoDeRiego;

@Repository("repositoryDispositivoDeRiego")
public interface DispositivoDeRiegoDAO extends JpaRepository<DispositivoDeRiego, Long> {

	public abstract List<DispositivoDeRiego> findByBajaFalse();
}
