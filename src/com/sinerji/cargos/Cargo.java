package com.sinerji.cargos;

public abstract class Cargo {

	private String nome;
	private Double salario;
	private Double bonusAnual;

	public Cargo() {
		super();
	}

	public Cargo(String nome, Double salario, Double bonusAnual) {
		super();
		this.nome = nome;
		this.salario = salario;
		this.bonusAnual = bonusAnual;
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

	public Double getBonusAnual() {
		return bonusAnual;
	}

	public void setBonusAnual(Double bonusAnual) {
		this.bonusAnual = bonusAnual;
	}

	public abstract double beneficio();

	@Override
	public String toString() {
		return "Cargo [nome=" + nome + ", salario=" + salario + ", bonusAnual=" + bonusAnual + "]";
	}

}
