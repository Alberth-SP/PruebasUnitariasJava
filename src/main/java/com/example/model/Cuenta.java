package com.example.model;

import java.math.BigDecimal;

import com.example.exceptions.SaldoInsuficienteException;

public class Cuenta {
	private String persona;
	
	private BigDecimal saldo;
	
	private Banco banco;
	
	public Cuenta(String persona, BigDecimal saldo) {
		this.persona = persona;
		this.saldo = saldo;
	}

	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
	

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof Cuenta))
			return false;
		Cuenta c = (Cuenta) obj;
		if(c.getPersona() == null || c.getPersona() == null)
			return false;
		return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
	}
	
	
	public void debido(BigDecimal monto) {
		
		
		BigDecimal nuevoSaldo = saldo.subtract(monto);
		if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
			throw new SaldoInsuficienteException("No hay saldo suficiente");
		}
		this.saldo = nuevoSaldo;
		
	}
	
	public void credito(BigDecimal monto) {
		this.saldo = saldo.add(monto);
	}
	
	
	
	
	
	

}
