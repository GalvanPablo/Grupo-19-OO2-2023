package com.unla.grupo19.grupo19OO22023.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.grupo19.grupo19OO22023.entities.Zona;
import com.unla.grupo19.grupo19OO22023.service.IZonaService;
import com.unla.grupo19.grupo19OO22023.service.implementation.ZonaService;

@RestController
@CrossOrigin(origins = "*")

@RequestMapping("/api/zonas")
public class ZonaRestController {
    
    @Autowired
    @Qualifier("serviceZona")
    private IZonaService service;


    // ALTA
    // POST [server]/api/zonas
    // body (JSON){
    //     "nombre": "Zona 1"
    // }
    @PostMapping("")
    public ResponseEntity<?> newZona(@RequestBody Zona zona){
        // return ResponseEntity.ok().body("OK");
        try {
            Zona z = service.insert(zona);
            if(z != null) return ResponseEntity.status(HttpStatus.CREATED).body(z);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo agregar la zona");
        } catch (Exception e) {
            String error = e.getMessage();

            if(error.equals(ZonaService.CodigoError.ZONA_INVALIDA.name())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La zona es invalida");
            if(error.equals(ZonaService.CodigoError.NOMBRE_INVALIDO.name())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre es invalido");
            if(error.equals(ZonaService.CodigoError.NOMBRE_YA_REGISTRADO.name())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe una zona con ese nombre");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
    }


    // OBTENER TODAS
    // GET [server]/api/zonas
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        List<Zona> zonas = service.getAll();
        if(zonas != null && !zonas.isEmpty()) return ResponseEntity.ok().body(zonas);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron zonas");
    }

    // OBTENER POR ID
    // GET [server]/api/zonas/1
    @GetMapping("/{idZona}")
    public ResponseEntity<?> getById(@PathVariable("idZona") int idZona){
        try {
            Zona zona = service.findByIdZona(idZona);
            if(zona != null) return ResponseEntity.ok().body(zona);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la zona con id: " + idZona);
        } catch (Exception e) {
            String error = e.getMessage();

            if(error.equals(ZonaService.CodigoError.ID_INVALIDO.name())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El id es invalido");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
    }

    // OBTENER POR NOMBRE
    // GET [server]/api/zonas/nombre/Zona 1
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getByNombre(@PathVariable("nombre") String nombre){
        try {
            Zona zona = service.findByNombre(nombre);
            if(zona != null) return ResponseEntity.ok().body(zona);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la zona con nombre: " + nombre);
        } catch (Exception e) {
            String error = e.getMessage();

            if(error.equals(ZonaService.CodigoError.NOMBRE_INVALIDO.name())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre es invalido");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
    }
}
