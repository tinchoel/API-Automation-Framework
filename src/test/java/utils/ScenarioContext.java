package utils;
// importamos io.restassured.response.Response y models.Pet para poder almacenar y recuperar información de la mascota y la respuesta de la API durante la ejecución de un escenario de prueba.
import io.restassured.response.Response;
import models.Pet;

// Clase de contexto del escenario: centraliza los datos que deben
// persistir entre los distintos steps de un mismo escenario (por
// ejemplo, el id de la mascota creada en el POST, reutilizado luego
// en el GET y el DELETE). PetSteps crea una instancia nueva de esta
// clase por cada escenario (porque Cucumber crea una instancia nueva
// de PetSteps por escenario), así que no hay estado compartido entre
// escenarios distintos.
public class ScenarioContext {
    // Atributos privados para almacenar la información de la mascota, la respuesta de la API, el ID de la mascota y el nombre original de la mascota. Estos atributos permiten que los distintos pasos de un escenario compartan y accedan a la misma información durante la ejecución de las pruebas.
    private Pet pet;
    private Response response;
    private long petId;
    private String nombreOriginal;
    // Métodos públicos para obtener y establecer los valores de los atributos privados. Estos métodos permiten que los distintos pasos de un escenario accedan y modifiquen la información compartida de manera controlada, manteniendo la encapsulación de los datos.
    public Pet getPet() {
        return pet;
    }
    // Método para establecer la información de la mascota en el contexto del escenario. Permite que los distintos pasos de un escenario compartan y accedan a la misma información de la mascota durante la ejecución de las pruebas.
    public void setPet(Pet pet) {
        this.pet = pet;
    }
    // Método para obtener la respuesta de la API en el contexto del escenario. Permite que los distintos pasos de un escenario compartan y accedan a la misma información de la respuesta durante la ejecución de las pruebas.
    public Response getResponse() {
        return response;
    }
    // Método para establecer la respuesta de la API en el contexto del escenario. Permite que los distintos pasos de un escenario compartan y accedan a la misma información de la respuesta durante la ejecución de las pruebas.
    public void setResponse(Response response) {
        this.response = response;
    }
    // Método para obtener el ID de la mascota en el contexto del escenario. Permite que los distintos pasos de un escenario compartan y accedan a la misma información del ID durante la ejecución de las pruebas.
    public long getPetId() {
        return petId;
    }
    // Método para establecer el ID de la mascota en el contexto del escenario. Permite que los distintos pasos de un escenario compartan y accedan a la misma información del ID durante la ejecución de las pruebas.
    public void setPetId(long petId) {
        this.petId = petId;
    }
    // Método para obtener el nombre original de la mascota en el contexto del escenario. Permite que los distintos pasos de un escenario compartan y accedan a la misma información del nombre original durante la ejecución de las pruebas.
    public String getNombreOriginal() {
        return nombreOriginal;
    }
    // Método para establecer el nombre original de la mascota en el contexto del escenario. Permite que los distintos pasos de un escenario compartan y accedan a la misma información del nombre original durante la ejecución de las pruebas.
    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }
}