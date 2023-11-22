package com.sinerji.cargos;

public class Cargo {
	private String nome;
	private Double salario;

	public Cargo() {
		super();
	}

	public Cargo(String nome, Double salario) {
		super();
		this.nome = nome;
		this.salario = salario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

}
