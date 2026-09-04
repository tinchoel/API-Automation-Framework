package tests.positive;

//importamos clients.PetClient, com.github.javafaker.Faker, config.BaseApiConfig, io.restassured.response.Response, models.Pet, org.junit.jupiter.api.DisplayName y org.junit.jupiter.api.Test para poder realizar pruebas positivas de creación (POST) sobre el recurso Pet utilizando el cliente de la API y el modelo Pet.
import clients.PetClient;
import com.github.javafaker.Faker;
import config.BaseApiConfig;
import io.restassured.response.Response;
import models.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//importamos static org.junit.jupiter.api.Assertions.assertAll y static org.junit.jupiter.api.Assertions.assertEquals para poder realizar aserciones en las pruebas.
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Pruebas positivas de creación (POST) sobre el recurso Pet, utilizando
// PetClient y el modelo Pet.
public class PetPostTest extends BaseApiConfig {

    private final PetClient petClient = new PetClient();

    @DisplayName("Debe crear correctamente una mascota utilizando PetClient y el modelo Pet")
    @Test
    public void deberiaCrearMascotaConDatosDinamicos() {
        // Arrange
        Faker faker = new Faker();
        Pet pet = new Pet();
        pet.setId(faker.number().numberBetween(1000, 999999));
        pet.setName(faker.animal().name());
        pet.setStatus("available");

        // Act
        Response response = petClient.crearMascota(pet);
        Pet mascotaCreada = response.as(Pet.class);

        // Assert
        assertAll(
                () -> assertEquals(200, response.getStatusCode()),
                () -> assertEquals(pet.getId(), mascotaCreada.getId()),
                () -> assertEquals(pet.getName(), mascotaCreada.getName()),
                () -> assertEquals(pet.getStatus(), mascotaCreada.getStatus()));
    }
}
