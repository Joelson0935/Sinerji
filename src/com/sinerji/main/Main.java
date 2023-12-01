package com.sinerji.main;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.sinerji.funcionarios.Funcionario;
import com.sinerji.vendas.Venda;

public class Main {

	public static String valorTotalPagoComBonus(List<Funcionario> funcionarios, LocalDate data) {
		funcionarios.forEach(funcionario -> {
			calcularSalario(funcionario, data);
		});

		Double totalPago = 0.0;
		for (Funcionario funcionario : funcionarios) {
			totalPago += funcionario.getValorRecebido();
		}
		return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(totalPago);
	}

	public static String valorTotalPagoSemBeneficios(List<Funcionario> funcionarios, LocalDate data) {
		funcionarios.forEach(funcionario -> {
			calcularSalarioSemBeneficio(funcionario, data);
		});

		Double totalPago = 0.0;
		for (Funcionario funcionario : funcionarios) {
			totalPago += funcionario.getValorRecebido();
		}
		return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(totalPago);
	}

	public static String valorTotalBeneficiosPagos(List<Funcionario> funcionarios, LocalDate data) {
		funcionarios.forEach(funcionario -> {
			calcularSalarioSemBonus(funcionario, data);
		});

		Double totalPago = 0.0;
		Double salarioBase = 0.0;
		Double valorRecebido = 0.0;

		for (Funcionario funcionario : funcionarios) {
			if (!funcionario.getCargo().equalsIgnoreCase("gerente")) {
				salarioBase += funcionario.getSalarioBase();
				valorRecebido += funcionario.getValorRecebido();
			}
		}
		totalPago = valorRecebido - salarioBase;
		return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(totalPago);
	}

	public static String salarioMaisAltoDoMes(List<Funcionario> funcionarios, LocalDate data) {
		funcionarios.forEach(funcionario -> {
			calcularSalario(funcionario, data);
		});

		Double totalPago = 0.0;

		for (Funcionario funcionario : funcionarios) {
			totalPago += funcionario.getValorRecebido();
		}

		List<Double> valores = funcionarios.stream().map(func -> func.getValorRecebido()).collect(Collectors.toList());

		Double maiorValor = valores.stream().reduce(0.0, (acc, valor) -> {
			if (acc < valor) {
				acc = valor;
			}
			return acc;
		});

		totalPago = maiorValor;

		return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(totalPago);
	}

	public static String nomeFuncionarioMaiorBeneficioMes(List<Funcionario> funcionarios, LocalDate data) {
		funcionarios.forEach(funcionario -> {
			calcularSalarioSemBonus(funcionario, data);
		});

		List<Funcionario> funcionariosComBeneficio = new ArrayList<>();

		funcionarios.stream().filter(f -> !f.getCargo().equalsIgnoreCase("gerente")).forEach(f -> {
			funcionariosComBeneficio.add(f);
		});

		funcionariosComBeneficio.stream()
				.forEach(func -> func.setValorRecebido(func.getValorRecebido() - func.getSalarioBase()));

		List<Double> valores = funcionariosComBeneficio.stream().map(func -> func.getValorRecebido())
				.collect(Collectors.toList());

		Double maiorValor = valores.stream().reduce(0.0, (acc, valor) -> {
			if (acc < valor) {
				acc = valor;
			}
			return acc;
		});

		String nome = funcionarios.stream().map(func -> {
			if (func.getValorRecebido() != maiorValor) {
				return "";
			}
			return func.getNome();
		}).reduce("", (acc, func) -> func + acc);

		return nome;
	}

	public static Funcionario vendedorQueMaisVendeu(List<Funcionario> funcionarios, LocalDate data) {
		funcionarios.forEach(funcionario -> {
			calcularSalario(funcionario, data);
		});

		List<Funcionario> vendedores = funcionarios.stream()
				.filter(func -> func.getCargo().equalsIgnoreCase("vendedor")).toList();

		List<Venda> vendas = new ArrayList<>();
		for (Funcionario vendedor : vendedores) {
			vendedor.getVendas().stream().filter(v -> v.getMes().getMonthValue() == data.getMonthValue())
					.filter(v -> v.getMes().getYear() == data.getYear()).forEach(v -> vendas.add(v));
		}

		Double resultado = vendas.stream().map(venda -> venda.getValor()).reduce(0.0,
				(acc, valor) -> Double.max(acc, valor));

		List<Venda> maioresVendas = vendas.stream().filter(v -> v.getValor().equals(resultado)).toList();

		Funcionario funcionario = new Funcionario();
		for (Funcionario vendedor : vendedores) {
			for (Venda v : maioresVendas) {
				if (vendedor.getVendas().contains(v)) {
					funcionario = vendedor;
				}
			}
		}
		return funcionario;
	}

	public static void calcularSalario(Funcionario funcionario, LocalDate data) {

		if (funcionario.getCargo().equalsIgnoreCase("Secretario")) {
			Period anosServico = funcionario.getDataContrato().until(data);
			int bonus = anosServico.getYears() * 1000;
			double beneficio = funcionario.getSalarioBase() * 0.2;
			Double salario = funcionario.getSalarioBase() + bonus + beneficio;
			funcionario.setValorRecebido(salario);
		}

		if (funcionario.getCargo().equalsIgnoreCase("Gerente")) {
			Period anosServico = funcionario.getDataContrato().until(data);
			int bonus = anosServico.getYears() * 3000;
			Double salario = funcionario.getSalarioBase() + bonus;
			funcionario.setValorRecebido(salario);
		}

		if (funcionario.getCargo().equalsIgnoreCase("Vendedor")) {
			Period anosServico = funcionario.getDataContrato().until(data);
			int bonus = anosServico.getYears() * 1800;

			funcionario.getVendas().stream().filter(venda -> venda.getMes().getYear() == data.getYear())
					.filter(venda -> venda.getMes().getMonthValue() == data.getMonthValue()).forEach(venda -> {
						double beneficio = venda.getValor() * 0.3;
						double salario = funcionario.getSalarioBase() + beneficio + bonus;
						funcionario.setValorRecebido(salario);
					});
		}

	}

	public static void calcularSalarioSemBeneficio(Funcionario funcionario, LocalDate data) {

		if (funcionario.getCargo().equalsIgnoreCase("Secretario")) {
			Period anosServico = funcionario.getDataContrato().until(data);
			int bonus = anosServico.getYears() * 1000;
			Double salario = funcionario.getSalarioBase() + bonus;
			funcionario.setValorRecebido(salario);
		}

		if (funcionario.getCargo().equalsIgnoreCase("Gerente")) {
			Period anosServico = funcionario.getDataContrato().until(data);
			int bonus = anosServico.getYears() * 3000;
			Double salario = funcionario.getSalarioBase() + bonus;
			funcionario.setValorRecebido(salario);
		}

		if (funcionario.getCargo().equalsIgnoreCase("Vendedor")) {
			Period anosServico = funcionario.getDataContrato().until(data);
			int bonus = anosServico.getYears() * 1800;

			funcionario.getVendas().stream().filter(venda -> venda.getMes().getYear() == data.getYear())
					.filter(venda -> venda.getMes().getMonthValue() == data.getMonthValue()).forEach(venda -> {
						double salario = funcionario.getSalarioBase() + bonus;
						funcionario.setValorRecebido(salario);
					});
		}

	}

	public static void calcularSalarioSemBonus(Funcionario funcionario, LocalDate data) {

		if (funcionario.getCargo().equalsIgnoreCase("Secretario")) {
			double beneficio = funcionario.getSalarioBase() * 0.2;
			Double salario = funcionario.getSalarioBase() + beneficio;
			funcionario.setValorRecebido(salario);
		}

		if (funcionario.getCargo().equalsIgnoreCase("Vendedor")) {
			funcionario.getVendas().stream().filter(venda -> venda.getMes().getYear() == data.getYear())
					.filter(venda -> venda.getMes().getMonthValue() == data.getMonthValue()).forEach(venda -> {
						double beneficio = venda.getValor() * 0.3;
						double salario = funcionario.getSalarioBase() + beneficio;
						funcionario.setValorRecebido(salario);
					});
		}

	}

	public static void main(String[] args) {

		List<Funcionario> funcionarios = new ArrayList<>();

		Funcionario jorge = new Funcionario();
		jorge.setNome("Jorge Carvalho");
		jorge.setCargo("secretario");
		jorge.setDataContrato(LocalDate.parse("2018-01-01"));
		jorge.setSalarioBase(7000.0);

		Funcionario maria = new Funcionario();
		maria.setNome("Maria Souza");
		maria.setCargo("secretario");
		maria.setDataContrato(LocalDate.parse("2015-12-01"));
		maria.setSalarioBase(7000.0);

		Funcionario ana = new Funcionario();
		ana.setNome("Ana Silva");
		ana.setCargo("vendedor");
		ana.setDataContrato(LocalDate.parse("2021-12-01"));
		ana.setSalarioBase(12000.0);

		Funcionario joao = new Funcionario();
		joao.setNome("João Mendes");
		joao.setCargo("vendedor");
		joao.setDataContrato(LocalDate.parse("2021-12-01"));
		joao.setSalarioBase(12000.0);

		Funcionario juliana = new Funcionario();
		juliana.setNome("Juliana Alves");
		juliana.setCargo("gerente");
		juliana.setDataContrato(LocalDate.parse("2017-07-01"));
		juliana.setSalarioBase(20000.0);

		Funcionario bento = new Funcionario();
		bento.setNome("Bento Albino");
		bento.setCargo("gerente");
		bento.setDataContrato(LocalDate.parse("2014-03-01"));
		bento.setSalarioBase(20000.0);

		Venda joao1 = new Venda("João", 3400.0, LocalDate.parse("2021-12-01"));
		Venda joao2 = new Venda("João", 7700.0, LocalDate.parse("2022-01-01"));
		Venda joao3 = new Venda("João", 5000.0, LocalDate.parse("2022-02-01"));
		Venda joao4 = new Venda("João", 5900.0, LocalDate.parse("2022-03-01"));
		Venda joao5 = new Venda("João", 6500.0, LocalDate.parse("2022-04-01"));

		Venda ana1 = new Venda("Ana", 5200.0, LocalDate.parse("2021-12-01"));
		Venda ana2 = new Venda("Ana", 4000.0, LocalDate.parse("2022-01-01"));
		Venda ana3 = new Venda("Ana", 4200.0, LocalDate.parse("2022-02-01"));
		Venda ana4 = new Venda("Ana", 5850.0, LocalDate.parse("2022-03-01"));
		Venda ana5 = new Venda("Ana", 7000.0, LocalDate.parse("2022-04-01"));

		joao.getVendas().add(joao1);
		joao.getVendas().add(joao2);
		joao.getVendas().add(joao3);
		joao.getVendas().add(joao4);
		joao.getVendas().add(joao5);

		ana.getVendas().add(ana1);
		ana.getVendas().add(ana2);
		ana.getVendas().add(ana3);
		ana.getVendas().add(ana4);
		ana.getVendas().add(ana5);

		funcionarios.add(jorge);
		funcionarios.add(maria);
		funcionarios.add(ana);
		funcionarios.add(joao);
		funcionarios.add(juliana);
		funcionarios.add(bento);

		System.out.println("Valor total pago com todos os beneficios e bônus do mês à todos os funcionários: "
				+ valorTotalPagoComBonus(funcionarios, LocalDate.parse("2022-03-01")));

		System.out.println();
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
		System.out.println();

		System.out.println("Valor pago sem benefícios: "
				+ valorTotalPagoSemBeneficios(funcionarios, LocalDate.parse("2022-03-01")));

		System.out.println();
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
		System.out.println();

		System.out.println("Valor total de benefícios pagos no mês: "
				+ valorTotalBeneficiosPagos(funcionarios, LocalDate.parse("2022-03-01")));

		System.out.println();
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
		System.out.println();

		System.out.println(
				"Salário mais alto no mês: " + salarioMaisAltoDoMes(funcionarios, LocalDate.parse("2022-03-01")));

		System.out.println();
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
		System.out.println();

		System.out.println("Nome do Funcionário com o maior benefício: "
				+ nomeFuncionarioMaiorBeneficioMes(funcionarios, LocalDate.parse("2022-01-01")));

		System.out.println();
		funcionarios.stream().filter(funcionario -> !funcionario.getCargo().equalsIgnoreCase("gerente"))
				.forEach(funcionario -> System.out.println(funcionario));
		System.out.println();

		System.out.println("Vendedor que mais vendeu no mês: "
				+ vendedorQueMaisVendeu(funcionarios, LocalDate.parse("2022-04-01")));

		System.out.println();
		funcionarios.stream().filter(funcionario -> funcionario.getCargo().equalsIgnoreCase("Vendedor"))
				.forEach(funcionario -> System.out.println(funcionario));
		System.out.println();

	}
}
