package clients;

// importamos io.restassured.response.Response y el modelo Pet para poder trabajar con las respuestas de la API y los objetos Pet
import io.restassured.response.Response;
import models.Pet;

// importamos static io.restassured.RestAssured.given para poder utilizar el método given() de Rest Assured sin necesidad de escribir la clase completa cada vez
import static io.restassured.RestAssured.given;

// Esta clase representa un cliente de la API de Swagger Petstore para realizar operaciones CRUD sobre el recurso Pet. Proporciona métodos para crear, obtener, actualizar y eliminar mascotas, encapsulando las llamadas HTTP correspondientes y facilitando su uso en pruebas automatizadas.
public class PetClient {

    public Response crearMascota(Pet pet) {
        return given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet");
    }

    public Response obtenerMascotaPorId(long petId) {
        return given()
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}");
    }

    public Response obtenerMascotasPorEstado(String estado) {
        return given()
                .queryParam("status", estado)
                .when()
                .get("/pet/findByStatus");
    }

    public Response actualizarMascota(Pet pet) {
        return given()
                .contentType("application/json")
                .body(pet)
                .when()
                .put("/pet");
    }

    public Response eliminarMascotaPorId(long petId) {
        return given()
                .pathParam("petId", petId)
                .when()
                .delete("/pet/{petId}");
    }
}