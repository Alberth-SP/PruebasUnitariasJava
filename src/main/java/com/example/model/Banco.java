package com.example.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Banco {
	
	private String nombre;
	
	private List<Cuenta> cuentas;
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	
	public void addCuenta(Cuenta cuenta) {
		this.cuentas.add(cuenta);
		cuenta.setBanco(this);
	}

	public Banco(String nombre) {
		this.nombre = nombre;
		this.cuentas = new ArrayList<Cuenta>();
	}
	
	public void transferir(Cuenta origen, Cuenta destino, BigDecimal monto) {
		origen.debido(monto);
		destino.credito(monto);
	}
	
	
	
	

}
