package com.alura.literalura.model;

public enum Idioma {
    ES("español"),
    EN("ingles");

    private String idioma;

   Idioma(String idioma){
        this.idioma= idioma;
    }

    public static Idioma fromString(String idioma){
       for(Idioma i : Idioma.values()){
           if(i.idioma.equalsIgnoreCase(idioma)){
               return i;
           }
       }
        throw new IllegalArgumentException("Ninguna lenguaje encontrado: " + idioma);
    }

}
