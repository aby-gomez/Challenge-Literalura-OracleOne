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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "libros_y_autores",
            joinColumns = @JoinColumn(name = "libro_id"),//conecta al lado del dueño de la relacion, en many to many no hay dueño hay que definirlo igual para que jpa no cree 2 tablas intermedias
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private List<Autor> autores;

    @Column(columnDefinition = "TEXT")//ya que la sinopsis supera los 255 caracteres
    private  String lenguaje;

    private Integer descargas;

    public Libro(){};

    public Libro(LibroDTO libro) {
        this.lenguaje =(libro.lenguaje() != null && !libro.lenguaje().isEmpty())
                ? libro.lenguaje().get(0)
                : "Desconocido";
        this.titulo = libro.titulo();
        this.descargas = libro.descargas();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }
}
