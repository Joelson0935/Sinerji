package com.sinerji.funcionarios;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.sinerji.vendas.Venda;

public class Funcionario {

	private String nome;
	private LocalDate dataContrato;
	private String cargo;
	private Double salarioBase;
	private Double ValorRecebido;
	private List<Venda> vendas = new ArrayList<>();

	public Funcionario() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataContrato() {
		return dataContrato;
	}

	public void setDataContrato(LocalDate dataContrato) {
		this.dataContrato = dataContrato;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Double getSalarioBase() {
		return salarioBase;
	}

	public void setSalarioBase(Double salarioBase) {
		this.salarioBase = salarioBase;
	}

	public Double getValorRecebido() {
		return ValorRecebido;
	}

	public void setValorRecebido(Double valorRecebido) {
		ValorRecebido = valorRecebido;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	@Override
	public String toString() {
		return "Funcionario [nome = " + nome + ", dataContrato = " + dataContrato + ", cargo = " + cargo + ", salarioBase = "
				+ NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(salarioBase) + ", ValorRecebido = "
				+ NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(ValorRecebido) + ", vendas = " + vendas
				+ "]";
	}

}
