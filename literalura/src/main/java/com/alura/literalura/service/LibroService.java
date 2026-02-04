package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.AutorDTO;
import com.alura.literalura.model.DatosDTO;
import com.alura.literalura.model.Libro;
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



    public void guardarDatos(DatosDTO datos){
        List<Libro> libros = datos.libros().stream()
                .map(l -> {
                    Libro libro = new Libro(l);
                    libro.setAutores(validarAutor(l.autores()));
                    return libro;
                })
                .collect(Collectors.toList());
        libroRepository.saveAll(libros);
        System.out.println("Libros guardados en la base de datos.");
    }

    public List<Autor> validarAutor(List<AutorDTO> autoresDTO){
        List<Autor> autores = new ArrayList<>();
        Autor autor1;
        for(AutorDTO a : autoresDTO){
            String nombre = a.nombre();
            Optional<Autor> autor = autorRepository.findByNombre(nombre);
            if(autor.isEmpty()){
                autor1 = new Autor(a);
                autorRepository.save(autor1);
            }else{
                autor1 = autor.get();
            }
            autores.add(autor1);

        }
        return autores;
    }


}
