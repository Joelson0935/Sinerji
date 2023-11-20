package com.sinerji.main;

import com.sinerji.cargos.Cargo;
import com.sinerji.cargos.Secretario;

public class Main {
	public static void main(String[] args) {

		Cargo cargo = new Secretario();
		cargo.setNome("Secretário");
		cargo.setSalario(7000.0);
		cargo.setBonusAnual(1000.0);

		System.out.println(cargo + " Benefício: " + cargo.beneficio());

	}
}
