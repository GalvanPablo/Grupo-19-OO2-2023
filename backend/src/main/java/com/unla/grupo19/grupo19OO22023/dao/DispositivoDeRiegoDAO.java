package com.unla.grupo19.grupo19OO22023.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.grupo19.grupo19OO22023.entities.DispositivoDeRiego;

@Repository("repositoryDispositivoDeRiego")
public interface DispositivoDeRiegoDAO extends JpaRepository<DispositivoDeRiego, Long> {

	public abstract List<DispositivoDeRiego> findByBajaFalse();
	
	@Query("SELECT d FROM Dispositivo d WHERE d.nombre = :nombre")
    public abstract Optional<DispositivoDeRiego>  buscarPorNombre(@Param("nombre") String nombre);
	
}
