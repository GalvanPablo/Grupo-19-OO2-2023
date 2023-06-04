package com.unla.grupo19.grupo19OO22023.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

@RestController

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
    public DispositivoEstacionamientoModel newDispositivoEstacionamiento(@RequestBody DispositivoEstacionamiento dispositivo){
        imprimir("NUEVO DISPOSITIVO DE ESTACIONAMIENTO");
        System.out.println(dispositivo);
        return service.insert(dispositivo);
    }


    // BAJA LOGICA DE UN DISPOSITIVO DE ESTACIONAMIENTO - ## ADMINISTRADOR ##
    // PUT [server]/api/dispositivos/estacionamiento/{id}/baja
    @PutMapping("/{id}/baja")
    public boolean removeDispositivoEstacionamiento(@PathVariable("id") int idDispositivo){
        imprimir("DAR DE BAJA LOGICA UN DISPOSITIVO DE ESTACIONAMIENTO");
        return service.remove(idDispositivo);
    }


    // MODIFICAR UN DISPOSITIVO DE ESTACIONAMIENTO - ## ADMINISTRADOR ##
    // PUT [server]/api/dispositivos/estacionamiento/{id}
    // body (json){
    //     "nombre": "E_V1",
    //     "zona": {"idZona": 1, "nombre": "Norte"},
    //     "plazas": [1, 2, 3, 4, 5]
    // }
    @PutMapping("/{id}")
    public DispositivoEstacionamientoModel updateDispositivoEstacionamiento(@PathVariable("id") int idDispositivo, @RequestBody DispositivoEstacionamiento dispositivoActualizado){
        // Creo que que es mejor que el id del dispositivo a actualizar vaya en el path y no en el body
        // ya que por tema seguridad solo tendra acceso el administrador y no el auditor
        imprimir("MODIFICAR UN DISPOSITIVO DE ESTACIONAMIENTO");
        return service.update(idDispositivo, dispositivoActualizado);
    }


    // OBTENER TODOS LOS DISPOSITIVOS DE ESTACIONAMIENTO - ## ADMINISTRADOR y AUDITOR ##
    // GET [server]/api/dispositivos/estacionamiento
    @GetMapping("")
    public List<DispositivoEstacionamientoModel> getAllDispositivoEstacionamiento() {
        imprimir("OBTENER TODOS LOS DISPOSITIVOS DE ESTACIONAMIENTO");
        return service.getAll();
    }
    

    // OBTENER UN DISPOSITIVO DE ESTACIONAMIENTO POR ID - ## ADMINISTRADOR y AUDITOR ##
    // GET [server]/api/dispositivos/estacionamiento/{id}
    @GetMapping("/{id}")
    public DispositivoEstacionamientoModel getDispositivoEstacionamientoById(@PathVariable("id") int id){
        imprimir("OBTENER UN DISPOSITIVO DE ESTACIONAMIENTO POR ID");
        return service.findByIdDispositivo(id);
    }


    // OBTENER UN DISPOSITIVO DE ESTACIONAMIENTO POR ID DE ZONA - ## ADMINISTRADOR y AUDITOR ##
    // GET [server]/api/dispositivos/estacionamiento/zona/{idZona}
    @GetMapping("/zona/{idZona}")
    public List<DispositivoEstacionamientoModelNoZona> getDispositivoEstacionamientoByZona(@PathVariable("idZona") int idZona){
        imprimir("OBTENER UN DISPOSITIVO DE ESTACIONAMIENTO POR ID DE ZONA");
        return service.findByZona(idZona);
    }

}
