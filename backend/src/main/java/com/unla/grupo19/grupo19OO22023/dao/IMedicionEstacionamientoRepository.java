package com.unla.grupo19.grupo19OO22023.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.unla.grupo19.grupo19OO22023.entities.MedicionEstacionamiento;
import java.io.Serializable;
// import java.util.List;

@Repository("repositoryMedicionEstacionamiento")
public interface IMedicionEstacionamientoRepository extends JpaRepository<MedicionEstacionamiento, Serializable> {
    
    // Por IdMedicion
    public abstract MedicionEstacionamiento findByIdMedicion(int idMedicion);

    // // VISUALIZACION EN FORMATO TABLA
    // // Ver todos los registros, ordenados por fechaHoraRegistro
    // public abstract List<MedicionEstacionamiento> findAllByOrderByFechaHoraRegistroAsc();

    // // Ver todos los registros entre fechas, ordenados por fechaHoraRegistro
    // public abstract List<MedicionEstacionamiento> findByFechaHoraRegistroBetweenOrderByFechaHoraRegistroAsc(String fechaHoraRegistroDesde, String fechaHoraRegistroHasta);

    // // Ver todos los registros de un dispositivo, ordenados por fechaHoraRegistro
    // public abstract List<MedicionEstacionamiento> findByDispositivoIdDispositivoOrderByFechaHoraRegistroAsc(int idDispositivo);

    // // Ver todos los registros de un dispositivo entre fechas, ordenados por fechaHoraRegistro
    // public abstract List<MedicionEstacionamiento> findByDispositivoIdDispositivoAndFechaHoraRegistroBetweenOrderByFechaHoraRegistroAsc(int idDispositivo, String fechaHoraRegistroDesde, String fechaHoraRegistroHasta);

    // // Los registros de una zona, ordenados por fechaHoraRegistro
    // public abstract List<MedicionEstacionamiento> findByDispositivoZonaIdZonaOrderByFechaHoraRegistroAsc(int idZona);


    // // ESTADOS ACTUALES
    // // El ultimo registro de cada plaza de una zona, ordenados por nroPlaza

    // // El ultimo registro de cada plaza por zona de todas las zonas, ordenados por zona y plaza

}
