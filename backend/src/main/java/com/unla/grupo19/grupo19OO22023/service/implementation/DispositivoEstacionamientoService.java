package com.unla.grupo19.grupo19OO22023.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.grupo19.grupo19OO22023.dao.IDispositivoEstacionamientoRepository;
import com.unla.grupo19.grupo19OO22023.entities.DispositivoEstacionamiento;
import com.unla.grupo19.grupo19OO22023.entities.Zona;
import com.unla.grupo19.grupo19OO22023.models.DispositivoEstacionamientoModel;
import com.unla.grupo19.grupo19OO22023.models.DispositivoEstacionamientoModelNoZona;
import com.unla.grupo19.grupo19OO22023.service.IDispositivoEstacionamientoService;

@Service("serviceDispositivoEstacionamiento")
public class DispositivoEstacionamientoService implements IDispositivoEstacionamientoService {

    @Autowired
    @Qualifier("repositoryDispositivoEstacionamiento")
    private IDispositivoEstacionamientoRepository repository;

    private ModelMapper modelMapper = new ModelMapper();

    private boolean existeDispositivoEnLaMismaZonaConAlgunaDeLasPlazas(DispositivoEstacionamiento dispositivo) {
        List<DispositivoEstacionamiento> dispositivos = repository.findByZona(dispositivo.getZona().getIdZona()); // Obtengo todos los dispositivos de la zona que no esten dados de baja
        for (DispositivoEstacionamiento d : dispositivos) { // Los itero
            System.out.println("Dispositivo iterado:\n" + d);
            if(d.getIdDispositivo() != dispositivo.getIdDispositivo()) { // En caso que se este actualizando para que revisar las plazas si es el mismo dispositivo
                System.out.println("No es el mismo dispositivo");
                // Si no es el mismo dispositivo revisar las plazas
                for (int plaza : dispositivo.getPlazas()) { // Itero sobre las plazas del nuevo dispositivo
                    if(d.getPlazas().contains(plaza)) { // Si el dispositivo actual tiene alguna de las plazas del nuevo dispositivo
                        return true;
                    }
                }
            }else{
                System.out.println("Es el mismo dispositivo");
            }
        }
        return false;
    }

    // ALTA
    @Override
    public DispositivoEstacionamientoModel insert(DispositivoEstacionamiento dispositivo) {
        if(existeDispositivoEnLaMismaZonaConAlgunaDeLasPlazas(dispositivo)){
            System.out.println("\n\n--- Ya existe un dispositivo en la misma zona con alguna de las plazas\n\n");
            return null;
        }
        System.out.println("\n\n--- Añadiendo nuevo dispositivo\n\n");
        return modelMapper.map(repository.save(dispositivo), DispositivoEstacionamientoModel.class);
    }


    // BAJA LOGICA
    @Override
    public boolean remove(int idDispositivo){
        DispositivoEstacionamiento dispositivo = repository.findByIdDispositivo(idDispositivo);
        if(dispositivo != null){
            dispositivo.setBaja(true);
            repository.save(dispositivo);
            return true;
        }
        return false;
    }

    // MODIFICACION
    @Override
    public DispositivoEstacionamientoModel update(int idDispositivo, DispositivoEstacionamiento dispositivoActualizado){
        dispositivoActualizado.setIdDispositivo(idDispositivo);
        System.out.println("Dispositivo actualizado:\n" + dispositivoActualizado);

        if(existeDispositivoEnLaMismaZonaConAlgunaDeLasPlazas(dispositivoActualizado)){
            System.out.println("\n\n--- Ya existe un dispositivo en la misma zona con alguna de las plazas y no es el que se esta actualizando\n\n");
            return null;
        }

        DispositivoEstacionamiento dispositivo = repository.findByIdDispositivo(idDispositivo);
        if(dispositivo == null) return null; // No existe el dispositivo
        dispositivo.setNombre(dispositivoActualizado.getNombre());
        dispositivo.setZona(dispositivoActualizado.getZona());
        dispositivo.setPlazas(dispositivoActualizado.getPlazas());
        return modelMapper.map(repository.save(dispositivo), DispositivoEstacionamientoModel.class);
    }


    // OBTENER TODOS
    @Override
    public List<DispositivoEstacionamientoModel> getAll() {
        List<DispositivoEstacionamiento> dispositivos = repository.findAll();
        if(dispositivos.isEmpty()) return new ArrayList<DispositivoEstacionamientoModel>();

        // TODO FIX - No entiendo porque no me deja pasar la zona directamente utilizando el modelMapper
        // Ver si se puede hacer algo con el modelMapper para que no sea necesario hacer esto
        // Actualmente uso este codigo en otras partes de este archivo´
        // Si no hay alguna forma de hacerlo con el modelMapper, crear un metodo privado que haga esto para reducir codigo
        List<DispositivoEstacionamientoModel> dispositivosModel = new ArrayList<DispositivoEstacionamientoModel>();
        for (DispositivoEstacionamiento d : dispositivos) {
            Zona zona = new Zona(d.getZona().getNombre());
            zona.setIdZona(d.getZona().getIdZona());
            DispositivoEstacionamientoModel model = new DispositivoEstacionamientoModel(d.getIdDispositivo(), d.getNombre(), zona , d.getPlazas()); 
            dispositivosModel.add(model);
        }

        return dispositivosModel;
    }


    // OBTENER POR ID
    @Override
    public DispositivoEstacionamientoModel findByIdDispositivo(int id) {
        DispositivoEstacionamiento d = repository.findByIdDispositivo(id);
        // CODIGO IMPLEMENTADO POR FALLA - en el modelMapper
        Zona zona = new Zona(d.getZona().getNombre());
        zona.setIdZona(d.getZona().getIdZona());
        return new DispositivoEstacionamientoModel(d.getIdDispositivo(), d.getNombre(), zona , d.getPlazas());
    }


    // OBTENER POR ID DE ZONA
    @Override
    public List<DispositivoEstacionamientoModelNoZona> findByZona(int idZona) {
        List<DispositivoEstacionamiento> dispositivos = repository.findByZona(idZona);
        if(dispositivos.isEmpty()) return new ArrayList<DispositivoEstacionamientoModelNoZona>();

        // CODIGO REPETIDO POR FALLA - en el modelMapper
        List<DispositivoEstacionamientoModelNoZona> dispositivosModel = new ArrayList<DispositivoEstacionamientoModelNoZona>();
        for (DispositivoEstacionamiento d : dispositivos) {
            // dispositivosModel.add(new DispositivoEstacionamientoModelNoZona(d.getIdDispositivo(), d.getNombre(), d.getPlazas()));
            dispositivosModel.add(modelMapper.map(d, DispositivoEstacionamientoModelNoZona.class));
        }

        return dispositivosModel;
    }

}
