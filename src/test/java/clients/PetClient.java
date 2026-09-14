package clients;

// importamos io.restassured.response.Response y el modelo Pet para poder trabajar con las respuestas de la API y los objetos Pet
import io.restassured.response.Response;
import models.Pet;

// importamos static io.restassured.RestAssured.given para poder utilizar el método given() de Rest Assured sin necesidad de escribir la clase completa cada vez
import static io.restassured.RestAssured.given;

// Esta clase representa un cliente de la API de Swagger Petstore para realizar operaciones CRUD sobre el recurso Pet. Proporciona métodos para crear, obtener, actualizar y eliminar mascotas, encapsulando las llamadas HTTP correspondientes y facilitando su uso en pruebas automatizadas.
public class PetClient {
// Método para crear una nueva mascota en la API. Recibe un objeto Pet y realiza una solicitud POST al endpoint /pet, enviando el objeto como cuerpo de la solicitud en formato JSON.
    public Response crearMascota(Pet pet) {
        return given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet");
    }
// Método para obtener una mascota existente por su ID. Recibe el ID de la mascota y realiza una solicitud GET al endpoint /pet/{petId}, devolviendo la respuesta de la API.
    public Response obtenerMascotaPorId(long petId) {
        return given()
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}");
    }
// Método para obtener mascotas filtradas por estado. Recibe un estado (por ejemplo, "available", "pending", "sold") y realiza una solicitud GET al endpoint /pet/findByStatus, pasando el estado como parámetro de consulta.
    public Response obtenerMascotasPorEstado(String estado) {
        return given()
                .queryParam("status", estado)
                .when()
                .get("/pet/findByStatus");
    }
//  Método para actualizar una mascota existente. Recibe un objeto Pet y realiza una solicitud PUT al endpoint /pet, enviando el objeto como cuerpo de la solicitud en formato JSON. Esto permite modificar los atributos de la mascota en la API.
    public Response actualizarMascota(Pet pet) {
        return given()
                .contentType("application/json")
                .body(pet)
                .when()
                .put("/pet");
    }
// Método para eliminar una mascota existente por su ID. Recibe el ID de la mascota y realiza una solicitud DELETE al endpoint /pet/{petId}, eliminando la mascota de la API.
    public Response eliminarMascotaPorId(long petId) {
        return given()
                .pathParam("petId", petId)
                .when()
                .delete("/pet/{petId}");
    }
}