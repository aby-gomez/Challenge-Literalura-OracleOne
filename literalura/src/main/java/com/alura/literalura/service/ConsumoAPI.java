package com.alura.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    public String pedirDatos(String url){
        String json = "";
        try{
            //si no coloco redirect , puedo recbirir un 301 y java se detiene ahi
            HttpClient cliente = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            //htt response el el contenedor, trae los headers, body y codigos(200,400)
            HttpResponse<String> respuesta = cliente.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(respuesta);
            json = respuesta.body();

        }catch (IOException  | InterruptedException e){
            System.out.println("Error"+ e.getMessage());
        }

        return json;
    }

}
