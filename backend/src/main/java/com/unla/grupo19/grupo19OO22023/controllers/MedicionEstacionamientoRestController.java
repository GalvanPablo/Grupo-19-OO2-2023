package com.unla.grupo19.grupo19OO22023.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.grupo19.grupo19OO22023.entities.DispositivoEstacionamiento;
import com.unla.grupo19.grupo19OO22023.entities.MedicionEstacionamiento;
import com.unla.grupo19.grupo19OO22023.models.MedicionEstacionamiento_Info_Model;
import com.unla.grupo19.grupo19OO22023.models.MedicionEstacionamiento_New_Model;
import com.unla.grupo19.grupo19OO22023.service.IDispositivoEstacionamientoService;
import com.unla.grupo19.grupo19OO22023.service.IMedicionEstacionamientoService;
import com.unla.grupo19.grupo19OO22023.service.implementation.MedicionEstacionamientoService;

@RestController

@RequestMapping("/api/mediciones/estacionamiento")
public class MedicionEstacionamientoRestController {
    
    @Autowired
    @Qualifier("serviceMedicionEstacionamiento")
    private IMedicionEstacionamientoService service;

    @Autowired
    @Qualifier("serviceDispositivoEstacionamiento")
    private IDispositivoEstacionamientoService serviceDispositivoEstacionamiento;

    // CODIGO MERAMENTE ESTETICO PARA DAR COLOR AL TEXTO EN LA CONSOLA //
    private void imprimir(String text) {
        System.out.println("\n\n\u001B[36m" + text + "\u001B[0m\n");
    }
    /////////////////////////////////////////////////////////////////////

    // ALTA DE MEDICION - ## ADMINISTRADOR / DISPOSITIVO ##
    // POST [server]/api/mediciones/estacionamiento
    // body (JSON){
    //     "idDispositivo": 1
    //     "nroPlaza": 1,
    //     "ocupado": true
    // }
    @PostMapping("")
    public ResponseEntity<?> newMedicionEstacionamiento(@RequestBody MedicionEstacionamiento_New_Model medicion){
        imprimir("NUEVA MEDICION DE ESTACIONAMIENTO");

        try {
            // Obtengo el dispositivo por id
            DispositivoEstacionamiento dispositivo = serviceDispositivoEstacionamiento.findByIdDispositivoEntity(medicion.getIdDispositivo());
            if(dispositivo == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe el dispositivo con id: " + medicion.getIdDispositivo());

            MedicionEstacionamiento medicionNueva = new MedicionEstacionamiento(dispositivo, medicion.getNroPlaza(), medicion.isOcupado());
            MedicionEstacionamiento_Info_Model medicionAgregada = service.insert(medicionNueva);
            if(medicionAgregada != null) {
                imprimir("MEDICION AGREGADA CORRECTAMENTE");
                return ResponseEntity.status(HttpStatus.CREATED).body(medicionAgregada);
            }
        } catch (Exception e) {
            // MANEJO DE ERRORES
            String error = e.getMessage();

            if(error.equals(MedicionEstacionamientoService.CodigoError.MEDICION_INVALIDA.name())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La medicion es invalida");
            }
        }
        // Llegados a este punto es porque se produjo un error desconocido
        System.out.println("Error interno del servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se produjo un error interno del servidor al intentar añadir la medicion de estacionamiento.");
    }

    // BAJA
    // MODIFICACION
    // No son nesscesarios ya que las mediciones no se pueden modificar ni eliminar

    // OBTENER TODAS LAS MEDICIONES DE ESTACIONAMIENTO
    // GET [server]/api/mediciones/estacionamiento
    @GetMapping("")
    public ResponseEntity<?> getAllMedicionesEstacionamiento(){
        imprimir("LISTA DE MEDICIONES DE ESTACIONAMIENTO");

        try {
            List<MedicionEstacionamiento_Info_Model> mediciones = service.getAll();
            if(mediciones.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay mediciones de estacionamiento");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(mediciones);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se produjo un error interno del servidor al intentar obtener las mediciones de estacionamiento.");
        }
    }


    // OBTENER MEDICION DE ESTACIONAMIENTO POR ID
    // GET [server]/api/mediciones/estacionamiento/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getMedicionEstacionamientoById(@PathVariable("id") int id){
        imprimir("MEDICION DE ESTACIONAMIENTO CON ID: " + id);

        try {
            MedicionEstacionamiento_Info_Model medicion = service.findByIdMedicion(id);
            if(medicion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe la medicion de estacionamiento con id: " + id);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(medicion);
            }
        } catch (Exception e) {
            // MANEJO DE ERRORES
            String error = e.getMessage();

            if(error.equals(MedicionEstacionamientoService.CodigoError.ID_INVALIDO.name())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El id de la medicion es invalido");
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se produjo un error interno del servidor al intentar obtener la medicion de estacionamiento.");
    }

}
