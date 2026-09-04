package tests.cleanup;

//importamos clients.PetClient, com.github.javafaker.Faker, config.BaseApiConfig, io.restassured.response.Response, models.Pet, org.junit.jupiter.api.DisplayName y org.junit.jupiter.api.Test para poder realizar pruebas de eliminación de mascotas utilizando el cliente de la API y generar datos aleatorios.
import clients.PetClient;
import com.github.javafaker.Faker;
import config.BaseApiConfig;
import io.restassured.response.Response;
import models.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//importamos static io.restassured.RestAssured.given, static org.hamcrest.Matchers.equalTo, static org.junit.jupiter.api.Assertions.assertAll y static org.junit.jupiter.api.Assertions.assertEquals para poder utilizar métodos de Rest Assured y realizar aserciones en las pruebas.
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Pruebas de eliminación (DELETE) y limpieza de datos sobre el recurso Pet, utilizando PetClient. Cada prueba crea su propio dato para no depender del estado dejado por otras clases.
public class PetDeleteTest extends BaseApiConfig {

    // Instancia de PetClient para realizar operaciones CRUD sobre el recurso Pet
    private final PetClient petClient = new PetClient();

    // Prueba que verifica la eliminación de una mascota existente y asegura que ya
    // no esté disponible en la API.
    @DisplayName("Debe eliminar una mascota existente y verificar que ya no esté disponible")
    @Test
    public void deberiaEliminarMascotaYVerificarQueNoExista() {
        // Arrange
        Faker faker = new Faker();
        Pet pet = new Pet();
        pet.setId(faker.number().numberBetween(1000, 999999));
        pet.setName(faker.animal().name());
        pet.setStatus("available");
        petClient.crearMascota(pet);

        // Act
        Response deleteResponse = petClient.eliminarMascotaPorId(pet.getId());
        Response getResponse = petClient.obtenerMascotaPorId(pet.getId());

        // Assert
        assertAll(
                () -> assertEquals(200, deleteResponse.getStatusCode()),
                () -> assertEquals(404, getResponse.getStatusCode()));
    }
}
