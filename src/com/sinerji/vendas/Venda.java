package com.sinerji.vendas;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class Venda {

	private String nomeVendedor;
	private Double valor;
	private LocalDate mes;

	public Venda() {
		super();
	}

	public Venda(String nomeVendedor, Double valor, LocalDate mes) {
		super();
		this.nomeVendedor = nomeVendedor;
		this.valor = valor;
		this.mes = mes;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public LocalDate getMes() {
		return mes;
	}

	public void setMes(LocalDate mes) {
		this.mes = mes;
	}

	@Override
	public String toString() {
		return "Venda [nomeVendedor=" + nomeVendedor + ", valor="
				+ NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor) + ", mes=" + mes + "]";
	}

}
