package com.alura.literalura.service;

public interface IConvierteDatos {
    <T> T convertir(String json, Class<T> clase);
}
