package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;
@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToMany
    @JoinTable(
            name = "libros_y_autores",
            joinColumns = @JoinColumn(name = "libro_id"),//conecta al lado del dueño de la relacion, en many to many no hay dueño hay que definirlo igual para que jpa no cree 2 tablas intermedias
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private List<Autor> autores;

    private  String sinopsis;

    public Libro(LibroDTO libro) {
        this.sinopsis = libro.sinopsis();
        this.titulo = libro.titulo();
        this.autores = libro.autores().stream()
                .map(a -> new Autor(a.nombre(),a.añoFallecimiento(),a.añoNacimiento()))
                .collect(Collectors.toList());
    }


}
