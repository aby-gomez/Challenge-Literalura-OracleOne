package com.alura.literalura.main;

import com.alura.literalura.model.DatosDTO;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.LibroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private final String URL = "https://gutendex.com/books?languages=es";
    private final String BUSCAR = "&search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    Scanner ingreso = new Scanner(System.in);
    ConvierteDatos conversor = new ConvierteDatos();
    private LibroService service;


    public Main(LibroService service) {
        this.service = service;
    }

    public  void correrPrograma() {
        String presentacion = """
     
                ¡Bienvenido/a al buscador de libros Literalura!
                Elija cualquier opción: 
                
                1 - Buscar libros por título
                2 - Mostrar los libros registrados
                3 - Mostrar autores registrados
                4 - Mostrar autores vivos en un determinado año
                5 - Mostrar libros por idioma
                0 - Salir
                
                """;
        int opcion = -1;


        while(opcion != 0){
            System.out.println(presentacion);
            opcion = ingreso.nextInt();
            ingreso.nextLine();

            switch(opcion){
                case 1 : buscarLibrosPorTitulo();
                break;
                case 0 :
                    System.out.println("Cerrando aplicación");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private String request(String url){
        return consumoAPI.pedirDatos(url);
    }

    private void buscarLibrosPorTitulo() {

        System.out.println("Ingrese titulo");
        String titulo = ingreso.nextLine();
        String json = request(URL+BUSCAR+titulo.replace(" ", "+"));
        DatosDTO datos = conversor.convertir(json, DatosDTO.class);

        if(datos.libros().isEmpty()){
            System.out.println("Libro no encontrado");
        }else {
            service.guardarDatos(datos);
        }
    }

}
