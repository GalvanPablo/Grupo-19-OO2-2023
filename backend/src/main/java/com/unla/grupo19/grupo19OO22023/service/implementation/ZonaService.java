package com.unla.grupo19.grupo19OO22023.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.grupo19.grupo19OO22023.dao.IZonaRepository;
import com.unla.grupo19.grupo19OO22023.entities.Zona;
import com.unla.grupo19.grupo19OO22023.service.IZonaService;

@Service("serviceZona")
public class ZonaService implements IZonaService {
    
    @Autowired
    @Qualifier("repositoryZona")
    private IZonaRepository repository;

    public static enum CodigoError {
        ID_INVALIDO,
        NOMBRE_INVALIDO,
        ZONA_INVALIDA,
        NOMBRE_YA_REGISTRADO
    }

    @Override
    public Zona insert(Zona zona) throws Exception {
        if(zona == null) throw new Exception(CodigoError.ZONA_INVALIDA.name()); // La zona es null
        if(zona.getNombre() == null || zona.getNombre().isEmpty()) throw new Exception(CodigoError.NOMBRE_INVALIDO.name()); // La zona no tiene nombre

        Zona zExistente = repository.findByNombre(zona.getNombre());
        if(zExistente != null) throw new Exception(CodigoError.NOMBRE_YA_REGISTRADO.name()); // Ya existe una zona con ese nombre

        return repository.save(zona);
    }

    @Override
    public List<Zona> getAll() {
        List<Zona> zonas = repository.findAll();
        if(zonas == null || zonas.isEmpty()) return new ArrayList<Zona>();
        return zonas;
    }

    @Override
    public Zona findByIdZona(int idZona) throws Exception {
        if(idZona <= 0) throw new Exception(CodigoError.ID_INVALIDO.name()); // El id es invalido
        return repository.findByIdZona(idZona);
    }

    @Override
    public Zona findByNombre(String nombre) throws Exception {
        if(nombre == null || nombre.isEmpty()) throw new Exception(CodigoError.NOMBRE_INVALIDO.name()); // El nombre es invalido
        return repository.findByNombre(nombre);
    }

    

}
