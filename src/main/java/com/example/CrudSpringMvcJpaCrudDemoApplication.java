package com.example;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entities.Correo;
import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.entities.Genero;
import com.example.entities.Telefono;
import com.example.services.CorreoService;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;
import com.example.services.TelefonoService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor

public class CrudSpringMvcJpaCrudDemoApplication implements CommandLineRunner {

	private final EmpleadoService empleadoService;
	private final DepartamentoService departamentoService;
	private final TelefonoService telefonoService;
	private final CorreoService correoService;

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringMvcJpaCrudDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Primero creamos departamentos
		// por esto no creamos las entidades como Record, porq son inmutables

		Departamento dpt1 = Departamento.builder()
				.nombre("RRHH")
				.build();

		Departamento dpt2 = Departamento.builder()
				.nombre("INFORMATICA")
				.build();
		Departamento dpt3 = Departamento.builder()
				.nombre("CONTABILIDAD")
				.build();

		departamentoService.persistirDpto(dpt1);
		departamentoService.persistirDpto(dpt2);
		departamentoService.persistirDpto(dpt3);
		// Empleados

		Empleado emp1 = Empleado.builder()
		.nombre("Aurora")
		.primerApellido("Duque")
		.fechaAlta(LocalDate.of(2012, Month.APRIL, 24))
		.salario(2500.00)
		.departamento(departamentoService.dameunDepartamento(1))
		.genero(Genero.OTRO)
		.build();

		Empleado emp2 = Empleado.builder()
		.nombre("Rosa Aurora")
		.primerApellido("Duque")
		.fechaAlta(LocalDate.of(2012, Month.FEBRUARY, 24))
		.salario(2400.00)
		.departamento(departamentoService.dameunDepartamento(2))
		.genero(Genero.MUJER)
		.build();

		// Telefonos

		List<Telefono> telefonosEmpleado1 = new ArrayList<>();
		Telefono telefono1Empleado1 = Telefono.builder()
				.numero("374374657")
				.build();
		Telefono telefono2Empleado1 = Telefono.builder()
				.numero("293849059")
				.build();

		telefonosEmpleado1.add(telefono1Empleado1);
		telefonosEmpleado1.add(telefono2Empleado1);

		List<Telefono> telefonosEmpleado2 = new ArrayList<>();
		Telefono telefono1Empleado2 = Telefono.builder()
				.numero("374374867")
				.build();
		Telefono telefono2Empleado2 = Telefono.builder()
				.numero("293849579")
				.build();

		telefonosEmpleado2.add(telefono1Empleado1);
		telefonosEmpleado2.add(telefono2Empleado1);

		// Correo

		List<Correo> correosEmpleado1 = new ArrayList<>();
		Correo Correo1Empleado1 = Correo.builder()
				.correo("cueis@gmail.com")
				.build();
		Correo Correo2Empleado1 = Correo.builder()
				.correo("odli@gmail.com")
				.build();

				correosEmpleado1.add(Correo1Empleado1);
				correosEmpleado1.add(Correo2Empleado1);
		
		List<Correo> correosEmpleado2 = new ArrayList<>();
		Correo Correo1Empleado2 = Correo.builder()
				.correo("ijfi@gmail.com")
				.build();
		Correo Correo2Empleado2 = Correo.builder()
				.correo("idhhf@gmail.com")
				.build();

				correosEmpleado1.add(Correo1Empleado2);
				correosEmpleado1.add(Correo2Empleado2);

	}
}
