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

    public static enum CodigoError {
        ID_INVALIDO,
        DISPOSITIVO_INVALIDO,
        NO_EXISTE_EL_DISPOSITIVO,
        DISPOSITIVO_DADO_DE_BAJA,
        CONLFICTO_CON_OTRO_DISPOSITIVO_EN_LA_MISMA_ZONA,
        DISPOSITIVO_SIN_PLAZAS,
    }

    private boolean existeDispositivoEnLaMismaZonaConAlgunaDeLasPlazas(DispositivoEstacionamiento dispositivo) {
        List<DispositivoEstacionamiento> dispositivos = repository.findByZona(dispositivo.getZona().getIdZona()); // Obtengo todos los dispositivos de la zona que no esten dados de baja
        for (DispositivoEstacionamiento d : dispositivos) { // Los itero
            if(d.getIdDispositivo() != dispositivo.getIdDispositivo()) { // En caso que se este actualizando ¿para que revisar las plazas si es el mismo dispositivo?
                // Si no es el mismo dispositivo revisar las plazas
                for (int plaza : dispositivo.getPlazas()) { // Itero sobre las plazas del nuevo dispositivo
                    if(d.getPlazas().contains(plaza)) { // Si el dispositivo actual tiene alguna de las plazas del nuevo dispositivo
                        return true;
                    }
                }
            }
        }
        return false; // Si termina de iterar y no encontro ningun dispositivo con alguna de las plazas del nuevo dispositivo ---> No existe ningun dispositivo en la misma zona con alguna de las plazas
    }

    // ALTA
    @Override
    public DispositivoEstacionamientoModel insert(DispositivoEstacionamiento dispositivo) throws Exception {
        // VALIDACIONES INICIALES
        if(dispositivo == null) throw new Exception(CodigoError.DISPOSITIVO_INVALIDO.name());
        if(dispositivo.getPlazas().isEmpty()) throw new Exception(CodigoError.DISPOSITIVO_SIN_PLAZAS.name());
        if(existeDispositivoEnLaMismaZonaConAlgunaDeLasPlazas(dispositivo)) throw new Exception(CodigoError.CONLFICTO_CON_OTRO_DISPOSITIVO_EN_LA_MISMA_ZONA.name());

        // RETORNO
        return modelMapper.map(repository.save(dispositivo), DispositivoEstacionamientoModel.class);
    }


    // BAJA LOGICA
    @Override
    public boolean remove(int idDispositivo) throws Exception{
        // VALIDACION INICIAL
        if(!(idDispositivo > 0)) throw new Exception(CodigoError.ID_INVALIDO.name());

        // BUSQUEDA
        DispositivoEstacionamiento dispositivo = repository.findByIdDispositivo(idDispositivo);
        if(dispositivo == null) throw new Exception(CodigoError.NO_EXISTE_EL_DISPOSITIVO.name()); // Valido que exista el dispositivo

        // MODIFICACION
        dispositivo.setBaja(true);
        DispositivoEstacionamiento dispositivoActualizado = repository.save(dispositivo);

        // RETORNO
        if(dispositivoActualizado == null) return false; // Si no me devuelve el objeto dado de baja ---> No se pudo dar de baja
        return true;
    }

    // MODIFICACION
    @Override
    public DispositivoEstacionamientoModel update(int idDispositivo, DispositivoEstacionamiento dispositivoActualizado) throws Exception{
        // VALIDACIONES INICIALES
        if(!(idDispositivo > 0)) throw new Exception(CodigoError.ID_INVALIDO.name());
        if(dispositivoActualizado == null) throw new Exception(CodigoError.DISPOSITIVO_INVALIDO.name());
        if(dispositivoActualizado.getPlazas().isEmpty()) throw new Exception(CodigoError.DISPOSITIVO_SIN_PLAZAS.name());
        dispositivoActualizado.setIdDispositivo(idDispositivo);

        // Busco el dispositivo a actualizar
        DispositivoEstacionamiento dispositivo = repository.findByIdDispositivo(idDispositivo);
        if(dispositivo == null) throw new Exception(CodigoError.NO_EXISTE_EL_DISPOSITIVO.name()); // Valido que exista el dispositivo

        if(dispositivo.isBaja()) throw new Exception(CodigoError.DISPOSITIVO_DADO_DE_BAJA.name()); // Valido que el dispositivo no este dado de baja


        // Valido si existe otro dispositivo en la misma zona con alguna de las plazas del nuevo dispositivo
        if(existeDispositivoEnLaMismaZonaConAlgunaDeLasPlazas(dispositivoActualizado)) throw new Exception(CodigoError.CONLFICTO_CON_OTRO_DISPOSITIVO_EN_LA_MISMA_ZONA.name());

        // MODIFICACION
        dispositivo.setNombre(dispositivoActualizado.getNombre());
        dispositivo.setZona(dispositivoActualizado.getZona());
        dispositivo.setPlazas(dispositivoActualizado.getPlazas());
        DispositivoEstacionamiento dispositivoActualizadoFinal = repository.save(dispositivo);

        // RETORNO
        return modelMapper.map(dispositivoActualizadoFinal, DispositivoEstacionamientoModel.class);
    }


    // OBTENER TODOS
    @Override
    public List<DispositivoEstacionamientoModel> getAll() {// EJECUCION DE LA CONSULTA
        List<DispositivoEstacionamiento> dispositivos = repository.findByBajaFalse(); // Obtengo todos los dispositivos que no esten dados de baja
        List<DispositivoEstacionamientoModel> dispositivosModel = new ArrayList<DispositivoEstacionamientoModel>(); // Creo una lista de dispositivosModel

        for (DispositivoEstacionamiento d : dispositivos) { // Itero sobre los dispositivos y los agrego a la lista de dispositivosModel transformandolos SIN EL MODEL MAPPER
            Zona zona = new Zona(d.getZona().getNombre());
            zona.setIdZona(d.getZona().getIdZona());
            DispositivoEstacionamientoModel model = new DispositivoEstacionamientoModel(d.getIdDispositivo(), d.getNombre(), zona , d.getPlazas()); 
            dispositivosModel.add(model);
        }

        // RETORNO
        return dispositivosModel; // Si no habia ningun dispositivo en la base de datos, devuelve una lista vacia (pero del model), sino devuelve la iteracion de los dispositivos transformados a model
    }


    // OBTENER POR ID
    @Override
    public DispositivoEstacionamientoModel findByIdDispositivo(int id) throws Exception {
        // VALIDACION INICIAL
        if(!(id > 0)) throw new Exception(CodigoError.ID_INVALIDO.name());

        // EJECUCION DE LA CONSULTA
        DispositivoEstacionamiento d = repository.findByIdDispositivo(id);

        // RETORNO
        if(d == null) return null;
        // CODIGO IMPLEMENTADO POR FALLA - en el modelMapper
        Zona zona = new Zona(d.getZona().getNombre());
        zona.setIdZona(d.getZona().getIdZona());
        return new DispositivoEstacionamientoModel(d.getIdDispositivo(), d.getNombre(), zona , d.getPlazas());
    }

    // OBTENER POR ID completo
    @Override
    public DispositivoEstacionamiento findByIdDispositivoEntity(int id) throws Exception {
        // VALIDACION INICIAL
        if(!(id > 0)) throw new Exception(CodigoError.ID_INVALIDO.name());

        // EJECUCION DE LA CONSULTA
        return repository.findByIdDispositivo(id);
    }


    // OBTENER POR ID DE ZONA
    @Override
    public List<DispositivoEstacionamientoModelNoZona> findByZona(int idZona) throws Exception {
        // VALIDACION INICIAL
        if(!(idZona > 0)) throw new Exception(CodigoError.ID_INVALIDO.name());

        // EJECCUCION DE LA CONSULTA
        List<DispositivoEstacionamiento> dispositivos = repository.findByZona(idZona);

        List<DispositivoEstacionamientoModelNoZona> dispositivosModel = new ArrayList<DispositivoEstacionamientoModelNoZona>();
        for (DispositivoEstacionamiento d : dispositivos) {
            dispositivosModel.add(modelMapper.map(d, DispositivoEstacionamientoModelNoZona.class));
        }

        // RETORNO
        return dispositivosModel; // Si no habia ningun dispositivo en la base de datos, devuelve una lista vacia (pero del model), sino devuelve la iteracion de los dispositivos transformados a model
    }

}
