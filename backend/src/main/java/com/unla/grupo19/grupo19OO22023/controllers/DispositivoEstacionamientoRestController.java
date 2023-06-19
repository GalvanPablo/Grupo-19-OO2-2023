package com.unla.grupo19.grupo19OO22023.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.grupo19.grupo19OO22023.entities.DispositivoEstacionamiento;
import com.unla.grupo19.grupo19OO22023.models.DispositivoEstacionamientoModel;
import com.unla.grupo19.grupo19OO22023.models.DispositivoEstacionamientoModelNoZona;
import com.unla.grupo19.grupo19OO22023.service.IDispositivoEstacionamientoService;
import com.unla.grupo19.grupo19OO22023.service.implementation.DispositivoEstacionamientoService;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")

@RequestMapping("/api/dispositivos/estacionamiento")
public class DispositivoEstacionamientoRestController {
    
    @Autowired
    @Qualifier("serviceDispositivoEstacionamiento")
    private IDispositivoEstacionamientoService service;

    // CODIGO MERAMENTE ESTETICO PARA DAR COLOR AL TEXTO EN LA CONSOLA //
    private void imprimir(String text) {
        System.out.println("\n\n\u001B[36m" + text + "\u001B[0m\n");
    }
    /////////////////////////////////////////////////////////////////////

    // ALTA DE UN DISPOSITIVO DE ESTACIONAMIENTO - ## ADMINISTRADOR ##
    // POST [server]/api/dispositivos/estacionamiento
    // body (json){
    //     "nombre": "E_V1",
    //     "zona": {"idZona": 1, "nombre": "Norte"},
    //     "plazas": [1, 2, 3, 4, 5]
    // }
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> newDispositivoEstacionamiento(@RequestBody DispositivoEstacionamiento dispositivo) {
        try {
            // EJECUCION
            DispositivoEstacionamientoModel dispositivoModel = service.insert(dispositivo);
            if(dispositivoModel != null){
                System.out.println("Dispositivo añadido con exito");
                System.out.println(dispositivoModel);
                return ResponseEntity.status(HttpStatus.CREATED).body(dispositivoModel);
            }
        } catch (Exception e) {
            // MANEJO DE ERRORES
            String error = e.getMessage();

            // Dispositivo invalido
            if(error.equals(DispositivoEstacionamientoService.CodigoError.DISPOSITIVO_INVALIDO.name())){
                System.out.println(error);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La solicitud de inserción es incorrecta. Verifique los datos proporcionados del nuevo dispositivo de estacionamiento");
            }

            // Dispositivo sin plazas
            if(error.equals(DispositivoEstacionamientoService.CodigoError.DISPOSITIVO_SIN_PLAZAS.name())){
                System.out.println(error);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El dispositivo de estacionamiento debe tener al menos una plaza.");
            }

            // Conflicto con otro dispositivo en la misma zona
            if(error.equals(DispositivoEstacionamientoService.CodigoError.CONLFICTO_CON_OTRO_DISPOSITIVO_EN_LA_MISMA_ZONA.name())){
                System.out.println(error);
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un dispositivo de estacionamiento en la misma zona con alguna de las plazas proporcionadas.");
            }
            System.out.println(error);
        }
        // Llegados a este punto es porque se produjo un error desconocido
        System.out.println("Error interno del servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se produjo un error interno del servidor al intentar añadir el dispositivo de estacionamiento.");
    }


    // BAJA LOGICA DE UN DISPOSITIVO DE ESTACIONAMIENTO - ## ADMINISTRADOR ##
    // PUT [server]/api/dispositivos/estacionamiento/{id}/baja
    @PutMapping("/{id}/baja")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeDispositivoEstacionamiento(@PathVariable("id") int idDispositivo) {
        imprimir("DAR DE BAJA LOGICA UN DISPOSITIVO DE ESTACIONAMIENTO");

        try {
            // EJECUCION
            if(service.remove(idDispositivo)){
                System.out.println("Dispositivo dado de baja con exito");
                return ResponseEntity.status(HttpStatus.OK).body("El dispositivo de estacionamiento fue dado de baja con exito");
            }
        } catch (Exception e) {
            // MANEJO DE ERRORES
            String error = e.getMessage();

            // ID Invalido
            if(error.equals(DispositivoEstacionamientoService.CodigoError.ID_INVALIDO.name())){
                System.out.println(error);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La solicitud de baja es incorrecta. Verifique que el ID sea correcto");
            }

            // No existe el dispositivo
            if(error.equals(DispositivoEstacionamientoService.CodigoError.NO_EXISTE_EL_DISPOSITIVO.name())){
                System.out.println(error);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el dispositivo de estacionamiento a dar de baja");
            }
        }

        // Llegados a este punto es porque se produjo un error desconocido
        System.out.println("Error interno del servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se produjo un error interno del servidor al intentar dar de baja el dispositivo de estacionamiento ID: " + idDispositivo + ".");
    }


    // MODIFICAR UN DISPOSITIVO DE ESTACIONAMIENTO - ## ADMINISTRADOR ##
    // PUT [server]/api/dispositivos/estacionamiento/{id}
    // body (json){
    //     "nombre": "E_V1",
    //     "zona": {"idZona": 1, "nombre": "Norte"},
    //     "plazas": [1, 2, 3, 4, 5]
    // }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateDispositivoEstacionamiento(@PathVariable("id") int idDispositivo, @RequestBody DispositivoEstacionamiento dispositivoActualizado) {
        imprimir("MODIFICAR UN DISPOSITIVO DE ESTACIONAMIENTO");

        try {
            // EJECUCION
            DispositivoEstacionamientoModel dispositivoModel = service.update(idDispositivo, dispositivoActualizado);
            if(dispositivoModel != null){
                System.out.println("Dispositivo modificado con exito");
                return ResponseEntity.status(HttpStatus.OK).body(dispositivoModel);
            }
            System.out.println("No se pudo modificar el dispositivo");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el dispositivo de estacionamiento a modificar");
        } catch (Exception e) {
            // MANEJO DE ERRORES
            String error = e.getMessage();

            // ID Invalido
            if(error.equals(DispositivoEstacionamientoService.CodigoError.ID_INVALIDO.name())){
                System.out.println(error);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La solicitud de modificación es incorrecta. Verifique que el ID sea correcto");
            }

            // Dispositivo invalido
            if(error.equals(DispositivoEstacionamientoService.CodigoError.DISPOSITIVO_INVALIDO.name())){
                System.out.println(error);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La solicitud de modificación es incorrecta. Verifique los datos proporcionados del dispositivo de estacionamiento");
            }

            // Dispositivo sin plazas
            if(error.equals(DispositivoEstacionamientoService.CodigoError.DISPOSITIVO_SIN_PLAZAS.name())){
                System.out.println(error);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El dispositivo de estacionamiento debe tener al menos una plaza.");
            }

            // No existe el dispositivo a modificar
            if(error.equals(DispositivoEstacionamientoService.CodigoError.NO_EXISTE_EL_DISPOSITIVO.name())){
                System.out.println(error);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el dispositivo de estacionamiento a modificar");
            }

            // El dispositivo esta dado de baja
            if(error.equals(DispositivoEstacionamientoService.CodigoError.DISPOSITIVO_DADO_DE_BAJA.name())){
                System.out.println(error);
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El dispositivo de estacionamiento esta dado de baja");
            }

            // Conflicto con otro dispositivo en la misma zona
            if(error.equals(DispositivoEstacionamientoService.CodigoError.CONLFICTO_CON_OTRO_DISPOSITIVO_EN_LA_MISMA_ZONA.name())){
                System.out.println(error);
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un dispositivo de estacionamiento en la misma zona con alguna de las plazas proporcionadas.");
            }
        }

        // Llegados a este punto es porque se produjo un error desconocido
        System.out.println("Error interno del servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se produjo un error interno del servidor al intentar modificar el dispositivo de estacionamiento ID: " + idDispositivo + ".");
    }


    // OBTENER TODOS LOS DISPOSITIVOS DE ESTACIONAMIENTO - ## ADMINISTRADOR y AUDITOR ##
    // GET [server]/api/dispositivos/estacionamiento
    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR')")
    public ResponseEntity<?> getAllDispositivoEstacionamiento() {
        imprimir("OBTENER TODOS LOS DISPOSITIVOS DE ESTACIONAMIENTO");

        try {
            // EJECUCION
            List<DispositivoEstacionamientoModel> dispositivos = service.getAll();
            if(!dispositivos.isEmpty()){
                System.out.println("Dispositivos de estacionamiento encontrados: " + dispositivos.size());
            } else {
                System.out.println("No se encontraron dispositivos de estacionamiento");
            }
            return ResponseEntity.status(HttpStatus.OK).body(dispositivos);    
        } catch (Exception e) {
            // MANEJO DE ERRORES
            System.out.println("Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se produjo un error interno del servidor al intentar obtener los dispositivos de estacionamiento.");
        }
    }
    

    // OBTENER UN DISPOSITIVO DE ESTACIONAMIENTO POR ID - ## ADMINISTRADOR y AUDITOR ##
    // GET [server]/api/dispositivos/estacionamiento/{id}
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR')")
    public ResponseEntity<?> getDispositivoEstacionamientoById(@PathVariable("id") int id) {
        imprimir("OBTENER UN DISPOSITIVO DE ESTACIONAMIENTO POR ID");

        try {
            DispositivoEstacionamientoModel dispositivo = service.findByIdDispositivo(id);
            if(dispositivo != null){
                System.out.println("Dispositivo ID: " + id + " encontrado");
            } else {
                System.out.println("No se encontro el dispositivo ID: " + id + ".");
            }
            return ResponseEntity.status(HttpStatus.OK).body(dispositivo);
        } catch (Exception e) {
            // MANEJO DE ERRORES

            // ID Invalido
            if(e.getMessage().equals(DispositivoEstacionamientoService.CodigoError.ID_INVALIDO.name())){
                System.out.println(e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La solicitud de obtención es incorrecta. Verifique que el ID sea correcto");
            }

            System.out.println("Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se produjo un error interno del servidor al intentar obtener el dispositivo de estacionamiento ID: " + id + ".");
        }
    }


    // OBTENER UN DISPOSITIVO DE ESTACIONAMIENTO POR ID DE ZONA - ## ADMINISTRADOR y AUDITOR ##
    // GET [server]/api/dispositivos/estacionamiento/zona/{idZona}
    @GetMapping("/zona/{idZona}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR')")
    public ResponseEntity<?> getDispositivoEstacionamientoByZona(@PathVariable("idZona") int idZona) {
        imprimir("OBTENER UN DISPOSITIVO DE ESTACIONAMIENTO POR ID DE ZONA");

        try {
            // EJECUCION
            List<DispositivoEstacionamientoModelNoZona> dispositivos = service.findByZona(idZona);
            if(!dispositivos.isEmpty()){
                System.out.println("Dispositivos de estacionamiento en la zona(" + idZona + ") encontrados: " + dispositivos.size());
            } else {
                System.out.println("No se encontraron dispositivo en la zona(" + idZona + ").");
            }
            return ResponseEntity.status(HttpStatus.OK).body(dispositivos);
        } catch (Exception e) {
            // MANEJO DE ERRORES

            // ID Invalido
            if(e.getMessage().equals(DispositivoEstacionamientoService.CodigoError.ID_INVALIDO.name())){
                System.out.println(e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La solicitud de obtención es incorrecta. Verifique que el ID sea correcto");
            }

            System.out.println("Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se produjo un error interno del servidor al intentar obtener los dispositivos de estacionamiento de la zona(" + idZona + ").");
        }
    }

}
