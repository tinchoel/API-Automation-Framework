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
    // Instancia de PetClient para realizar operaciones CRUD sobre el recurso Pet
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
    // Método auxiliar para crear una mascota de prueba antes de cada prueba. Se utiliza un ID fijo (1) para la mascota y se establece su nombre y estado. La mascota se crea utilizando el método crearMascota del PetClient.
    private void crearMascotaDePrueba(int petId) {
        Pet pet = new Pet();
        pet.setId(petId);
        pet.setName("Firulais");
        pet.setStatus("available");
        petClient.crearMascota(pet);
    }
    // Prueba que valida la creación de una mascota y la obtención de su información. Se crea una mascota de prueba, se obtiene su información mediante el método obtenerMascotaPorId del PetClient y se realizan aserciones para verificar que el código de estado sea 200 y que el ID de la mascota obtenida coincida con el ID esperado.
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
    // Prueba que valida la obtención de una mascota y la verificación de múltiples atributos de la respuesta. Se crea una mascota de prueba, se obtiene su información mediante el método obtenerMascotaPorId del PetClient y se realizan aserciones agrupadas con assertAll para verificar que el código de estado sea 200, que el ID de la mascota obtenida coincida con el ID esperado, y que el nombre y estado de la mascota no sean nulos.
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
