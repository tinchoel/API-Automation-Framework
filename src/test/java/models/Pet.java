package models;

//importamos com.fasterxml.jackson.annotation.JsonIgnoreProperties para poder ignorar propiedades desconocidas al deserializar JSON en objetos Java, evitando errores si la API devuelve campos adicionales que no están mapeados en el modelo Pet.
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Modelo POJO que representa la estructura básica del recurso Pet de Swagger Petstore. Permite serializar/deserializar automáticamente el JSON intercambiado con la API (vía Jackson, integrado con REST Assured).@JsonIgnoreProperties(ignoreUnknown = true): el modelo solo representa los atributos utilizados por el framework (id, name, status). La API responde con campos adicionales (photoUrls, tags, category); sin esta anotación, Jacksonfallaría al encontrar campos no mapeados.
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pet {

    // Se utiliza "long" en lugar de "int": al ser un servidor público y
    // compartido, es posible recibir mascotas creadas por otros usuarios
    // con IDs que exceden el rango de un int.
    private long id;
    private String name;
    private String status;
// Constructor vacío requerido por Jackson para deserializar JSON en objetos Java. Jackson necesita un constructor sin argumentos para crear instancias de la clase y luego establecer los valores de los campos a partir del JSON.
    public Pet() {
    }
// Constructor con parámetros para facilitar la creación de objetos Pet en pruebas. Permite inicializar los atributos id, name y status al crear una instancia de Pet.  
    public Pet(long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}