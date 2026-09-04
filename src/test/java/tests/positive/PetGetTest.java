package tests.positive;

//importamos clients.PetClient, config.BaseApiConfig, io.restassured.response.Response, models.Pet, org.junit.jupiter.api.DisplayName y org.junit.jupiter.api.Test para poder realizar pruebas positivas de consulta (GET) sobre el recurso Pet utilizando el cliente de la API y el modelo Pet en lugar de llamadas HTTP directas o JsonPath.
import clients.PetClient;
import config.BaseApiConfig;
import io.restassured.response.Response;
import models.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// importamos java.util.Arrays, static org.junit.jupiter.api.Assertions.assertAll, static org.junit.jupiter.api.Assertions.assertEquals y static org.junit.jupiter.api.Assertions.assertTrue para poder utilizar métodos de Rest Assured y realizar aserciones en las pruebas.
import java.util.Arrays;

// importamos java.util.Arrays, static org.junit.jupiter.api.Assertions.assertAll, static org.junit.jupiter.api.Assertions.assertEquals y static org.junit.jupiter.api.Assertions.assertTrue para poder utilizar métodos de Rest Assured y realizar aserciones en las pruebas.
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Pruebas positivas de consulta (GET) sobre el recurso Pet, utilizando
// PetClient y el modelo Pet en lugar de llamadas HTTP directas o JsonPath.
public class PetGetTest extends BaseApiConfig {

    private final PetClient petClient = new PetClient();

    @DisplayName("Debe obtener correctamente una mascota existente por su ID")
    @Test
    public void deberiaObtenerMascotaExistentePorId() {
        // Arrange
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Firulais");
        pet.setStatus("available");
        petClient.crearMascota(pet);

        // Act
        Response response = petClient.obtenerMascotaPorId(pet.getId());
        Pet mascotaObtenida = response.as(Pet.class);

        // Assert
        assertAll(
                () -> assertEquals(200, response.getStatusCode()),
                () -> assertEquals(pet.getId(), mascotaObtenida.getId()));
    }

    @DisplayName("Debe obtener mascotas filtradas por estado disponible")
    @Test
    public void deberiaObtenerMascotasPorEstadoDisponible() {
        // Arrange
        String estado = "available";

        // Act
        Response response = petClient.obtenerMascotasPorEstado(estado);
        Pet[] mascotas = response.as(Pet[].class);

        // Assert
        assertAll(
                () -> assertEquals(200, response.getStatusCode()),
                () -> assertTrue(mascotas.length > 0),
                () -> assertTrue(Arrays.stream(mascotas).allMatch(p -> estado.equals(p.getStatus()))));
    }
}
