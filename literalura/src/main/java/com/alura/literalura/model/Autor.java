package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer añoNacimiento;
    private  Integer añoFallecimiento;

    @ManyToMany(mappedBy = "autores") //la lista de autores en libro
    private List<Libro> libros;

    public Autor(String nombre, Integer añoFallecimiento, Integer añoNacimiento) {
        this.nombre = nombre;
        this.añoFallecimiento = añoFallecimiento;
        this.añoNacimiento = añoNacimiento;
    }
}
