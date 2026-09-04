package tests.negative;

//importamos clients.PetClient, com.github.javafaker.Faker, config.BaseApiConfig, io.restassured.response.Response, org.junit.jupiter.api.DisplayName y org.junit.jupiter.api.Test para poder realizar pruebas negativas sobre el recurso Pet utilizando el cliente de la API y generar datos aleatorios.
import clients.PetClient;
import com.github.javafaker.Faker;
import config.BaseApiConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//importamos static org.junit.jupiter.api.Assertions.assertEquals para poder realizar aserciones en las pruebas.
import static org.junit.jupiter.api.Assertions.assertEquals;

// Pruebas negativas sobre el recurso Pet, utilizando PetClient: escenarios
//donde se espera que la API responda con un error controlado (404) por
// operar sobre un recurso inexistente.
public class PetNegativeTest extends BaseApiConfig {

    private final PetClient petClient = new PetClient();

    @DisplayName("Debe retornar 404 al buscar una mascota inexistente")
    @Test
    public void deberiaRetornar404AlBuscarMascotaInexistente() {
        // Arrange
        Faker faker = new Faker();
        int petIdInexistente = faker.number().numberBetween(900000000, 999999999);

        // Act
        Response response = petClient.obtenerMascotaPorId(petIdInexistente);

        // Assert
        assertEquals(404, response.getStatusCode());
    }

    @DisplayName("Debe retornar 404 al intentar eliminar una mascota inexistente")
    @Test
    public void deberiaRetornar404AlEliminarMascotaInexistente() {
        // Arrange
        Faker faker = new Faker();
        int petIdInexistente = faker.number().numberBetween(900000000, 999999999);

        // Act
        Response response = petClient.eliminarMascotaPorId(petIdInexistente);

        // Assert
        assertEquals(404, response.getStatusCode());
    }
}
