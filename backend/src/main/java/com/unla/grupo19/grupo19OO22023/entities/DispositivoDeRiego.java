package com.unla.grupo19.grupo19OO22023.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "idDispositivo")
@Setter
@Getter
@NoArgsConstructor
public class DispositivoDeRiego extends Dispositivo {

	@Column(nullable = false)
	private float humedad;

	@Column(nullable = false)
	private float temperatura;

	@Column(nullable = false)
	private boolean activo;

	@Override
	public String toString() {
		return "DispositivoDeRiego [humedad=" + humedad + ", temperatura=" + temperatura + ", activo=" + activo + "]";
	}

	public boolean cambioDeEstado(MedicionDeRiego medicion) {

		if (this.activo && medicion.getHumedadActual() < this.humedad
				&& medicion.getTemperaturaActual() < this.temperatura) {
			this.activo = false;

			return true;
		} else if (!this.activo && medicion.getHumedadActual() > this.humedad
				&& medicion.getTemperaturaActual() > this.temperatura) {
			this.activo = true;

			return true;
		}

		return false;
	}

}
