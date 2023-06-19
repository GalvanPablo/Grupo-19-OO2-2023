package com.unla.grupo19.grupo19OO22023.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unla.grupo19.grupo19OO22023.entities.DispositivoEstacionamiento;
import java.io.Serializable;
import java.util.List;

@Repository("repositoryDispositivoEstacionamiento")
public interface IDispositivoEstacionamientoRepository extends JpaRepository<DispositivoEstacionamiento, Serializable> {

    public abstract List<DispositivoEstacionamiento> findByBajaFalse();
    
    public abstract DispositivoEstacionamiento findByIdDispositivo(int id);

    @Query("SELECT d FROM DispositivoEstacionamiento d WHERE d.zona.idZona = (:idZona) AND d.baja = false")
    public abstract List<DispositivoEstacionamiento> findByZona(int idZona);

}
