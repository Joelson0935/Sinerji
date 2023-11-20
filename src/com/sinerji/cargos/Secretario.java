package com.sinerji.cargos;

public class Secretario extends Cargo {

	public Secretario() {
		super();
	}

	public Secretario(String nome, Double salario, Double bonusAnual) {
		super(nome, salario, bonusAnual);
	}

	@Override
	public double beneficio() {
		double beneficio = super.getSalario() * 0.2;
		return beneficio;
	}

}
