package tests.positive;

//importamos clients.PetClient, config.BaseApiConfig, io.restassured.response.Response, models.Pet, org.junit.jupiter.api.DisplayName, org.junit.jupiter.api.Test y utils.PetCsvReader para poder realizar pruebas positivas sobre el recurso Pet utilizando el cliente de la API y consumir datos de prueba externos (pets.csv).
import clients.PetClient;
import config.BaseApiConfig;
import io.restassured.response.Response;
import models.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.PetCsvReader;

//importamos static org.junit.jupiter.api.Assertions.assertAll y static org.junit.jupiter.api.Assertions.assertEquals para poder realizar aserciones en las pruebas.
import java.util.List;

//importamos static org.junit.jupiter.api.Assertions.assertAll y static org.junit.jupiter.api.Assertions.assertEquals para poder realizar aserciones en las pruebas.
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Prueba que consume datos de prueba externos (pets.csv) en lugar de
// valores hardcodeados, desacoplando los datos de la lógica de
// automatización.
public class PetCsvDataTest extends BaseApiConfig {

    private final PetClient petClient = new PetClient();

    @DisplayName("Debe crear y validar cada mascota definida en pets.csv")
    @Test
    public void deberiaCrearMascotasDesdeArchivoCsv() {
        // Arrange
        List<Pet> mascotas = PetCsvReader.leerMascotas("pets.csv");

        for (Pet pet : mascotas) {
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
}
