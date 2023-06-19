package com.unla.grupo19.grupo19OO22023.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.grupo19.grupo19OO22023.entities.Evento;
import com.unla.grupo19.grupo19OO22023.models.Evento_Info_Model;
import com.unla.grupo19.grupo19OO22023.service.IEventoService;
import com.unla.grupo19.grupo19OO22023.service.implementation.EventoService;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")

@RequestMapping("/api/eventos")
public class EventoRestController {

    @Autowired
    @Qualifier("serviceEvento")
    private IEventoService service;

    // ALTA
    // POST [server]/api/eventos
    // @PostMapping("")
    // public ResponseEntity<?> newEvento(@RequestBody Evento evento){
    //     try {
    //         Evento e = service.insert(evento);
    //         if(e != null) return ResponseEntity.status(HttpStatus.CREATED).body(e);
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo agregar el evento");
    //     } catch (Exception e) {
    //         String error = e.getMessage();

    //         if(error.equals(EventoService.CodigoError.EVENTO_INVALIDO.name())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El evento es invalido");
    //         if(error.equals(EventoService.CodigoError.DESCRIPCION_INVALIDA.name())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La descripcion es invalida");
    //         if(error.equals(EventoService.CodigoError.DISPOSITIVO_INVALIDO.name())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El dispositivo es invalido");
    //         if(error.equals(EventoService.CodigoError.MEDICION_INVALIDA.name())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La medicion es invalida");
    //     }
    //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
    // }

    private List<Evento_Info_Model> extraerInfoEventos(List<Evento> eventos){
        List<Evento_Info_Model> eventosInfo = new ArrayList<Evento_Info_Model>();
        for(Evento e : eventos){
            eventosInfo.add(new Evento_Info_Model(e.getIdEvento(), e.getDescripcion(), e.getMedicion().getIdMedicion(), e.getDispositivo().getIdDispositivo(), e.getFechaHoraRegistro()));
        }
        return eventosInfo;
    }
    
    // OBTENER TODAS
    // GET [server]/api/eventos
    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR')")
    public ResponseEntity<?> getAll(){
        try {
            List<Evento> eventos = service.getAll();
            if(eventos.isEmpty()) return ResponseEntity.ok().body("No se encontraron eventos");

            return ResponseEntity.ok().body(extraerInfoEventos(eventos));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
        }
    }

    // OBTENER POR ID
    // GET [server]/api/eventos/1
    @GetMapping("/{idEvento}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR')")
    public ResponseEntity<?> getById(@PathVariable("idEvento") int idEvento){
        try {
            Evento e = service.findByIdEvento(idEvento);
            if(e != null) return ResponseEntity.ok().body(new Evento_Info_Model(idEvento, e.getDescripcion(), e.getMedicion().getIdMedicion(), e.getDispositivo().getIdDispositivo(), e.getFechaHoraRegistro()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el evento");
        } catch (Exception e) {
            String error = e.getMessage();

            if(error.equals(EventoService.CodigoError.ID_INVALIDO.name())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El id es invalido");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
    }

    // OBTENER POR DISPOSITIVO
    // GET [server]/api/eventos/dispositivo/1
    @GetMapping("/dispositivo/{idDispositivo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR')")
    public ResponseEntity<?> getByDispositivo(@PathVariable("idDispositivo") int idDispositivo){
        try {
            List<Evento> eventos  = service.findAllByDispositivoIdDispositivo(idDispositivo);
            if(!eventos.isEmpty()) return ResponseEntity.ok().body(extraerInfoEventos(eventos));
            return ResponseEntity.ok().body("No se encontraron eventos para el dispositivo ID: " + idDispositivo);
        } catch (Exception e) {
            String error = e.getMessage();

            if(error.equals(EventoService.CodigoError.ID_INVALIDO.name())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El id es invalido");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
    }

    // OBTENER POR RANGO DE FECHAS
    // GET [server]/api/eventos/fecha/2020-01-01T00:00:00/2020-01-01T23:59:59
    // aaaa-MM-ddThh:mm:ss
    @GetMapping("/fecha/{fechaHoraDesde}/{fechaHoraHasta}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR')")
    public ResponseEntity<?> getByFechaHoraRegistroBetween(@PathVariable("fechaHoraDesde") LocalDateTime fechaHoraDesde, @PathVariable("fechaHoraHasta") LocalDateTime fechaHoraHasta){
        System.out.println("fechaHoraDesde: " + fechaHoraDesde + " fechaHoraHasta: " + fechaHoraHasta);

        try {
            List<Evento> eventos = service.findByFechaHoraRegistroBetween(fechaHoraDesde, fechaHoraHasta);
            if(!eventos.isEmpty()) return ResponseEntity.ok().body(extraerInfoEventos(eventos));
            return ResponseEntity.ok().body("No se encontraron eventos entre las fechas " + fechaHoraDesde + " y " + fechaHoraHasta);
        } catch (Exception e) {
            String error = e.getMessage();

            if(error.equals(EventoService.CodigoError.DESDE_HASTA_INVALIDO.name())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La fechaHoraDesde o fechaHoraHasta es invalida");
            if(error.equals(EventoService.CodigoError.DESDE_HASTA_INVERTIDO.name())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La fechaHoraDesde es posterior a la fechaHoraHasta");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
    }
}
