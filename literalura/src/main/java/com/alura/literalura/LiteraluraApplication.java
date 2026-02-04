package com.alura.literalura;

import com.alura.literalura.main.Main;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	//JVM busca esta firma para ejecutar la app
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}
	@Autowired
	LibroService service;

	//luego ejecuta esto
	@Override
	public void run(String... args) throws Exception {
			Main main = new Main(service);
			main.correrPrograma();


	}
}
