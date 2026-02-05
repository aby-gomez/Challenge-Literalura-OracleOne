package com.alura.literalura.service;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;


    public List<LibroDTO> convertirALibroDTO(List<Libro> libros) {
        return libros.stream()
                .map(l -> {
                    //transformo autor en autor dto
                    List<AutorDTO> autores = convertirAutorDTO( l.getAutores());
                    //transformo en string ded sinopsis en una lista
                    List<String> lenguaje = new ArrayList<>();
                    lenguaje.add(l.getLenguaje());

                    return new LibroDTO(l.getTitulo(), autores, lenguaje, l.getDescargas());
                })
                .collect(Collectors.toList());

    }

    public List<AutorDTO> convertirAutorDTO(List<Autor> autores){
        return autores.stream()
                .map(a -> new AutorDTO(a.getNombre(), a.getAñoNacimiento(), a.getAñoFallecimiento()))
                .collect(Collectors.toList());

    }

    public List<Libro> convertirAListaDeLibros(DatosDTO datos){
        return datos.libros().stream()
                .map(l -> {
                    Libro libro = new Libro(l);
                    //valido que autor no este en la bd luego seteo la lista en Libro
                    libro.setAutores(validarAutor(l.autores()));
                    return libro;
                })
                .collect(Collectors.toList());
    }

    public void guardarDatos(DatosDTO datos) {
        List<Libro> libros = convertirAListaDeLibros(datos);

        for(Libro libro : libros){
            String titulo = libro.getTitulo();
            Optional<Libro> libroBd = libroRepository.findByTitulo(titulo);
            if(libroBd.isEmpty()){
                libroRepository.save(libro);
            }
        }
        System.out.println("Libros guardados en la base de datos.");
    }


    public List<Autor> validarAutor(List<AutorDTO> autoresDTO) {
        List<Autor> autores = new ArrayList<>();
        Autor autor1;
        for (AutorDTO a : autoresDTO) {
            String nombre = a.nombre();
            Optional<Autor> autor = autorRepository.findByNombre(nombre);
            if (autor.isEmpty()) {
                autor1 = new Autor(a);
                autorRepository.save(autor1);
            } else {
                autor1 = autor.get();
            }
            autores.add(autor1);

        }
        return autores;
    }

    public List<LibroDTO> mostrarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();

        return convertirALibroDTO(libros);
    }

    public List<AutorDTO> mostrarAutoresRegistrados(){
        List<Autor> autores = autorRepository.findAll();
        return convertirAutorDTO(autores);
    }

    public List<AutorDTO>  mostrarAutoresVivosPorAño(Integer año) {
            List<Autor> autoresBd = autorRepository.mostrarAutoresVivosPorAño(año);

            return convertirAutorDTO(autoresBd);

    }

    public List<LibroDTO> buscarLibrosPorIdioma(String idioma) {
        List<Libro> librosBd = libroRepository.findByLenguaje(idioma);
        return convertirALibroDTO(librosBd);
    }
}
