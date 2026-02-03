package com.alura.literalura.main;

import com.alura.literalura.service.ConsumoAPI;

import java.util.Scanner;

public class Main {
    private final String URL = "https://gutendex.com/books?languages=es";
    private final String BUSCAR = "&search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    Scanner ingreso = new Scanner(System.in);

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


    private void buscarLibrosPorTitulo() {
        System.out.println("Ingrese titulo");
        String titulo = ingreso.nextLine();
        String json = consumoAPI.pedirDatos(URL+BUSCAR+titulo.replace(" ", "+"));
        System.out.println(json);
    }

}
