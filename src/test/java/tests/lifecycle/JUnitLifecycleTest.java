package tests.lifecycle;

//importamos config.BaseApiConfig, io.restassured.response.Response, org.junit.jupiter.api.AfterAll, org.junit.jupiter.api.AfterEach, org.junit.jupiter.api.BeforeAll, org.junit.jupiter.api.BeforeEach, org.junit.jupiter.api.Test para poder utilizar la configuración base de la API y los métodos del ciclo de vida de JUnit 5 en las pruebas.
import clients.PetClient;
import config.BaseApiConfig;
import io.restassured.response.Response;
import models.Pet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//importamos static io.restassured.RestAssured.given, static org.junit.jupiter.api.Assertions.assertAll, static org.junit.jupiter.api.Assertions.assertEquals y static org.junit.jupiter.api.Assertions.assertNotNull para poder utilizar métodos de Rest Assured y realizar aserciones en las pruebas.
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Avance 3.1 - Repaso de JUnit 5.
// Demuestra el ciclo de vida completo de JUnit 5 (@BeforeAll, @BeforeEach,
// @Test, @AfterEach, @AfterAll) y agrupa validaciones múltiples con
// assertAll, utilizando PetClient y el modelo Pet en lugar de llamadas
// HTTP directas. 
public class JUnitLifecycleTest extends BaseApiConfig {

    private final PetClient petClient = new PetClient();

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Iniciando la ejecución de la clase de pruebas...");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("Preparando una nueva prueba...");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("Finalizó la ejecución de la prueba.");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("Finalizó la ejecución de todas las pruebas.");
    }

    private void crearMascotaDePrueba(int petId) {
        Pet pet = new Pet();
        pet.setId(petId);
        pet.setName("Firulais");
        pet.setStatus("available");
        petClient.crearMascota(pet);
    }

    @DisplayName("Debe obtener correctamente una mascota luego de crearla")
    @Test
    public void deberiaObtenerMascotaExistentePorId() {
        // Arrange
        int petId = 1;
        crearMascotaDePrueba(petId);

        // Act
        Response response = petClient.obtenerMascotaPorId(petId);

        // Assert
        assertEquals(200, response.getStatusCode());
    }

    @DisplayName("Debe validar múltiples atributos de la respuesta con assertAll")
    @Test
    public void deberiaValidarRespuestaCompletaConAssertAll() {
        // Arrange
        int petId = 1;
        crearMascotaDePrueba(petId);

        // Act
        Response response = petClient.obtenerMascotaPorId(petId);
        Pet mascotaObtenida = response.as(Pet.class);

        // Assert
        assertAll(
                () -> assertEquals(200, response.getStatusCode()),
                () -> assertEquals(petId, mascotaObtenida.getId()),
                () -> assertNotNull(mascotaObtenida.getName()),
                () -> assertNotNull(mascotaObtenida.getStatus()));
    }
}
