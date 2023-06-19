package com.unla.grupo19.grupo19OO22023.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.grupo19.grupo19OO22023.models.DispositivoDeRiegoDTO;
import com.unla.grupo19.grupo19OO22023.service.DispositivoDeRiegoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/dispositivos/riego")
@CrossOrigin(origins = "*")
public class DispositivoDeRiegoController {

	@Autowired
	DispositivoDeRiegoService dispositivoDeRiegoService;
	
	@GetMapping("/all")
	public ResponseEntity<List<DispositivoDeRiegoDTO>> findAllDispositivoDeRiego(){
		
		List<DispositivoDeRiegoDTO> listDispositivoDeRiegoDTO = this.dispositivoDeRiegoService.findAllActiveDispositivoDeRiego();
		return new ResponseEntity<List<DispositivoDeRiegoDTO>>(listDispositivoDeRiegoDTO,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{dispositivoId}")
	public ResponseEntity<String> deleteDispositivoDeRiego(@PathVariable Long dispositivoId){
		
		this.dispositivoDeRiegoService.deleteDispositivoDeRiego(dispositivoId);
		String response = "Se ha borrado con exito";
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<DispositivoDeRiegoDTO> saveDispositivoDeRiego(@Valid @RequestBody DispositivoDeRiegoDTO dispositivoDeRiegoDTO){
		
		DispositivoDeRiegoDTO savedDispositivoDeRiego = this.dispositivoDeRiegoService.createDispositivoDeRiego(dispositivoDeRiegoDTO);
		return new ResponseEntity<DispositivoDeRiegoDTO>(savedDispositivoDeRiego,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{dispositivoId}")
	public ResponseEntity<DispositivoDeRiegoDTO> updateDispositivoDeRiego(@Valid @RequestBody DispositivoDeRiegoDTO dispositivoDeRiegoDTO, @PathVariable Long dispositivoId){
		
		DispositivoDeRiegoDTO updatedDispositivoDeRiego = this.dispositivoDeRiegoService.updateDispositivoDeRiego(dispositivoDeRiegoDTO, dispositivoId);
		return new ResponseEntity<DispositivoDeRiegoDTO>(updatedDispositivoDeRiego, HttpStatus.OK);
	}
	
}
