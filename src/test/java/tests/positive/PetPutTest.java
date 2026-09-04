package tests.positive;

// importamos clients.PetClient, com.github.javafaker.Faker, config.BaseApiConfig, io.restassured.response.Response, models.Pet, org.junit.jupiter.api.DisplayName y org.junit.jupiter.api.Test para poder realizar pruebas positivas de actualización (PUT) sobre el recurso Pet utilizando el cliente de la API y el modelo Pet.
import clients.PetClient;
import com.github.javafaker.Faker;
import config.BaseApiConfig;
import io.restassured.response.Response;
import models.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// importamos static org.junit.jupiter.api.Assertions.assertAll y static org.junit.jupiter.api.Assertions.assertEquals para poder realizar aserciones en las pruebas.
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Pruebas positivas de actualización (PUT) sobre el recurso Pet, utilizando
// PetClient y el modelo Pet.
public class PetPutTest extends BaseApiConfig {

    private final PetClient petClient = new PetClient();

    @DisplayName("Debe actualizar correctamente los datos de una mascota existente")
    @Test
    public void deberiaActualizarMascotaExistente() {
        // Arrange
        Faker faker = new Faker();
        Pet pet = new Pet();
        pet.setId(faker.number().numberBetween(1000, 999999));
        pet.setName(faker.animal().name());
        pet.setStatus("available");
        petClient.crearMascota(pet);

        pet.setName(faker.animal().name());
        pet.setStatus("sold");

        // Act
        Response updateResponse = petClient.actualizarMascota(pet);
        Response getResponse = petClient.obtenerMascotaPorId(pet.getId());
        Pet mascotaActualizada = getResponse.as(Pet.class);

        // Assert
        assertAll(
                () -> assertEquals(200, updateResponse.getStatusCode()),
                () -> assertEquals(200, getResponse.getStatusCode()),
                () -> assertEquals(pet.getName(), mascotaActualizada.getName()),
                () -> assertEquals(pet.getStatus(), mascotaActualizada.getStatus()));
    }
}
