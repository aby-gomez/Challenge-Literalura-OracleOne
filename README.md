# Literalura - Catálogo de Libros

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker">
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven">
</p>

## Descripción del Proyecto
Este proyecto es un sistema de gestión y consulta de libros que consume datos de la API Gutendex. Permite realizar búsquedas de obras literarias, registrar autores y libros en una base de datos persistente, y generar estadísticas personalizadas mediante una interfaz de consola interactiva.

## Descripción de la API
La aplicación utiliza la API de **Gutendex**, un catálogo de libros electrónicos de dominio público.
* **URL Base:** `https://gutendex.com/books/`
* **Funcionalidad:** Se realizan consultas mediante parámetros de búsqueda para obtener información detallada sobre títulos, autores, idiomas y estadísticas de descarga.

## Arquitectura y Patrones
La aplicación sigue principios de diseño orientados a la mantenibilidad:

* **Patrón MVC (Model-View-Controller):** * **Modelo:** Entidades JPA (`Libro`, `Autor`) que representan el esquema de datos.
    * **Repositorio:** Interfaces que extienden `JpaRepository` para la abstracción de persistencia.
    * **Servicio:** Clase `LibroService` que encapsula la lógica de negocio y transformación.
    * **Controlador/Vista:** Clase `Main` que gestiona la interacción con el usuario.
* **Java Records:** Implementación de `Records` (`DatosDTO`, `LibroDTO`, `AutorDTO`) para el mapeo de JSON inmutable mediante Jackson y `@JsonAlias`.
* **Enumeraciones (Enum):** Uso de la clase `Idioma` para la normalización y validación de las entradas de lenguaje.



## Infraestructura y Persistencia
Para el entorno de base de datos, se ha optado por la containerización para asegurar la portabilidad:

* **Docker:** La base de datos **PostgreSQL** y la herramienta de administración **pgAdmin** se ejecutan en contenedores independientes.
* **Variables de Entorno:** Se destaca el uso de variables de entorno para la protección de credenciales sensibles:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
    spring.datasource.username=${DB_USER}
    spring.datasource.password=${DB_PASSWORD}
    ```



## Guía de Funcionamiento
El sistema presenta las siguientes funcionalidades obligatorias:

1.  **Búsqueda de libros por título:** Consulta a la API y persistencia automática del libro y su autor si no existen previamente.
2.  **Listado de libros registrados:** Muestra todas las obras almacenadas en la base de datos local.
3.  **Listado de autores registrados:** Visualización de los autores con sus respectivos periodos de vida.
4.  **Autores vivos en determinado año:** Filtro mediante consultas JPQL personalizadas para identificar autores activos en un año específico.
5.  **Estadísticas por idioma:** Filtrado de libros por siglas (ES, EN, etc.) y generación de métricas de descarga utilizando `IntSummaryStatistics` de Java Streams.

## Características Técnicas Destacadas

### 1. Gestión de Persistencia y Consultas (Spring Data JPA)
Se implementaron dos tipos de consultas para la recuperación de datos desde PostgreSQL:

* **Derived Queries:** Utilizadas en `LibroRepository` para búsquedas simples y directas.
    * `findByTitulo(String titulo)`
    * `findByLenguaje(String idioma)`
* **Consultas JPQL con @Query:** Para lógica más compleja, como la filtración de autores vivos en un periodo específico:
    ```java
    @Query("SELECT a FROM Autor a WHERE a.añoNacimiento <= :año AND (a.añoFallecimiento >= :año OR a.añoFallecimiento IS NULL)")
    ```

### 2. Procesamiento de Datos con Java Streams
Se hizo un uso intensivo de la API de **Streams** para:
* **Transformación de Objetos:** Conversión de Entidades a DTOs y viceversa para mantener la integridad de la arquitectura.
* **Estadísticas:** Uso de `IntSummaryStatistics` para obtener métricas sobre el volumen de descargas por idioma.
    ```java
    IntSummaryStatistics est = libros.stream()
        .collect(Collectors.summarizingInt(LibroDTO::descargas));
    ```



### 3. Mapeo de Datos (Jackson & DTOs)
El consumo del JSON de la API Gutendex se realiza mediante **Java Records**, aprovechando la inmutabilidad y concisión. Se utilizaron las anotaciones:
* `@JsonAlias`: Para mapear campos específicos del JSON a variables de Java.
* `@JsonIgnoreProperties`: Para descartar datos no requeridos en la lógica de negocio.

### 4. Modelo de Datos (Relacional)
Se estableció una relación **Muchos a Muchos (@ManyToMany)** entre Libros y Autores, gestionada mediante una tabla intermedia para reflejar la realidad literaria donde una obra puede tener múltiples colaboradores.

---

### Requisitos para la Ejecución
1.  Contenedor de Docker con PostgreSQL activo.
2.  Configuración de variables de entorno `DB_USER` y `DB_PASSWORD`.
3.  JDK 17 o superior.
