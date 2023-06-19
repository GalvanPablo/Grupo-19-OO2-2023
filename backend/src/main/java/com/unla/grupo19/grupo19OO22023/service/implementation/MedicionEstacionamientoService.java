package com.unla.grupo19.grupo19OO22023.service.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.grupo19.grupo19OO22023.dao.IMedicionEstacionamientoRepository;
import com.unla.grupo19.grupo19OO22023.entities.DispositivoEstacionamiento;
import com.unla.grupo19.grupo19OO22023.entities.Evento;
import com.unla.grupo19.grupo19OO22023.entities.MedicionEstacionamiento;
import com.unla.grupo19.grupo19OO22023.models.MedicionEstacionamiento_Info_Model;
import com.unla.grupo19.grupo19OO22023.service.IEventoService;
import com.unla.grupo19.grupo19OO22023.service.IMedicionEstacionamientoService;

import jakarta.transaction.Transactional;

@Service("serviceMedicionEstacionamiento")
public class MedicionEstacionamientoService implements IMedicionEstacionamientoService {

    @Autowired
    @Qualifier("repositoryMedicionEstacionamiento")
    private IMedicionEstacionamientoRepository repository;

    // Uso del repository de DispositivoEstaionamiento para obtener el dispositivo por id y setearlo en la medicion
    @Autowired
    @Qualifier("serviceDispositivoEstacionamiento")
    private DispositivoEstacionamientoService serviceDispositivoEstacionamiento;

    @Autowired
    @Qualifier("serviceEvento")
    private IEventoService serviceEvento;



    public static enum CodigoError {
        ID_INVALIDO,
        DISPOSITIVO_INVALIDO,
        MEDICION_INVALIDA,
    }

    // ALTA
    @Override
    @Transactional()
    public MedicionEstacionamiento_Info_Model insert(MedicionEstacionamiento medicion) throws Exception {
        if(medicion == null) throw new Exception(CodigoError.MEDICION_INVALIDA.name()); // La medicion es null
        if(medicion.getDispositivo() == null) throw new Exception(CodigoError.DISPOSITIVO_INVALIDO.name()); // La medicion no tiene dispositivo

        Set<Integer> plazas = ((DispositivoEstacionamiento)medicion.getDispositivo()).getPlazas();
        if(!plazas.contains(medicion.getNroPlaza())) throw new Exception(CodigoError.MEDICION_INVALIDA.name()); // La plaza medida no existe en el dispositivo

        List<MedicionEstacionamiento_Info_Model> ultimasMediciones = getLastMeasurements();

        MedicionEstacionamiento mA = repository.save(medicion);
        if(mA == null) return null;

        // VERIFICACION DE EVENTO
        System.out.println("\n\n\n-- Verificacion de evento --");
        boolean plazaEncontrada = false;
        for (MedicionEstacionamiento_Info_Model mE : ultimasMediciones) { // Recorro las ultimas mediciones
            if(mE.getIdZona() == mA.getDispositivo().getZona().getIdZona() && mE.getNroPlaza() == mA.getNroPlaza()){ // Si es de la misma zona y plaza
                System.out.println("Se encontro medicion previa de la plaza");
                plazaEncontrada = true;
                if(mE.isOcupado() != mA.isOcupado()){ // Si el estado es distinto
                    System.out.println("Evento agregado: " + addEvento(mA));
                    break;
                } else {
                    System.out.println("No se agrega evento");
                    break;
                }
            }
        }
        if(!plazaEncontrada){
            System.out.println("No se encontro medicion previa de la plaza");
            System.out.println("Evento agregado: " + addEvento(mA));
        }

        return new MedicionEstacionamiento_Info_Model(mA.getIdMedicion(), mA.getDispositivo().getIdDispositivo(), mA.getDispositivo().getZona().getIdZona(), mA.getNroPlaza(), mA.isOcupado(), mA.getFechaHoraRegistro());
    }

    private boolean addEvento(MedicionEstacionamiento mA) throws Exception{
        System.out.println("Añadiendo evento");
        String descripcion = "La plaza " + mA.getNroPlaza() + " de la zona " + mA.getDispositivo().getZona().getIdZona();
        if(mA.isOcupado()){
            descripcion += " se ha ocupado";
        } else {
            descripcion += " se ha liberado";
        }
        Evento e = serviceEvento.insert(new Evento(mA.getDispositivo(), mA, descripcion));
        return (e != null);
    }


    // OBTENER TODOS
    @Override
    public List<MedicionEstacionamiento_Info_Model> getAll() {
        List<MedicionEstacionamiento> mediciones = repository.findAll();
        if(mediciones == null || mediciones.isEmpty()) return new ArrayList<MedicionEstacionamiento_Info_Model>();

        List<MedicionEstacionamiento_Info_Model> medicionesInfo = new ArrayList<MedicionEstacionamiento_Info_Model>();
        for (MedicionEstacionamiento mE : mediciones) {
            medicionesInfo.add(new MedicionEstacionamiento_Info_Model(mE.getIdMedicion(), mE.getDispositivo().getIdDispositivo(), mE.getDispositivo().getZona().getIdZona(), mE.getNroPlaza(), mE.isOcupado(), mE.getFechaHoraRegistro()));
        }

        return medicionesInfo;
    }


    // OBTENER POR ID
    @Override
    public MedicionEstacionamiento_Info_Model findByIdMedicion(int idMedicion) throws Exception {
        if(idMedicion <= 0) throw new Exception(CodigoError.ID_INVALIDO.name());

        MedicionEstacionamiento m = repository.findByIdMedicion(idMedicion);
        if(m == null) return null;
        return new MedicionEstacionamiento_Info_Model(m.getIdMedicion(), m.getDispositivo().getIdDispositivo(), m.getDispositivo().getZona().getIdZona(), m.getNroPlaza(), m.isOcupado(), m.getFechaHoraRegistro());
    }

    private boolean plazaExistente(List<MedicionEstacionamiento_Info_Model> mediciones, MedicionEstacionamiento_Info_Model mE) {
        for (MedicionEstacionamiento_Info_Model m : mediciones) {
            if( m.getIdZona() == mE.getIdZona() && m.getNroPlaza() == mE.getNroPlaza()) return true;
        }
        return false;
    }

    // El ultimo registro de cada plaza por zona de todas las zonas, ordenados por zona y plaza // Estado actual de las plazas
    @Override
    public List<MedicionEstacionamiento_Info_Model> getLastMeasurements() {
        List<MedicionEstacionamiento_Info_Model> mediciones = getAll();
        if(mediciones == null || mediciones.isEmpty()) return new ArrayList<MedicionEstacionamiento_Info_Model>();

        Collections.reverse(mediciones);

        List<MedicionEstacionamiento_Info_Model> ultimasMediciones = new ArrayList<MedicionEstacionamiento_Info_Model>();
        for (MedicionEstacionamiento_Info_Model mE : mediciones) {
            if(!plazaExistente(ultimasMediciones, mE)) ultimasMediciones.add(mE);
        }

        return ultimasMediciones;
    }

}
