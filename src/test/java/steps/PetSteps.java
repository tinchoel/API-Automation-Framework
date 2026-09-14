package steps;
// Esta clase contiene los pasos de Cucumber que definen el comportamiento de los escenarios de prueba relacionados con la gestión de mascotas en la API de Swagger Petstore. Cada método está anotado con las anotaciones de Cucumber (@Given, @When, @Then, @And) para vincularlos con los pasos definidos en los archivos de características (features). Los métodos interactúan con la API a través del PetClient y utilizan Faker para generar datos dinámicos, mientras que ScenarioContext se encarga de almacenar y compartir información entre los distintos pasos de un escenario.
import clients.PetClient;
import com.github.javafaker.Faker;
import utils.ScenarioContext;
// Importamos las clases necesarias de Cucumber para definir los pasos de los escenarios de prueba, así como las clases de RestAssured y el modelo Pet para interactuar con la API y manejar las respuestas.
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
// Importamos las clases necesarias de RestAssured y el modelo Pet para interactuar con la API y manejar las respuestas.
import io.restassured.response.Response;
import models.Pet;
// Importamos las clases de aserciones de JUnit para realizar verificaciones en los resultados de las pruebas.
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
// La clase PetSteps define los pasos de Cucumber para los escenarios de prueba relacionados con la gestión de mascotas en la API de Swagger Petstore. Utiliza PetClient para interactuar con la API, Faker para generar datos dinámicos y ScenarioContext para almacenar información compartida entre los pasos.
public class PetSteps {
    // Instancia de PetClient para realizar operaciones CRUD sobre el recurso Pet
    private final PetClient petClient = new PetClient();
    private final Faker faker = new Faker();
    private final ScenarioContext context = new ScenarioContext();

    // ------------------------------------------------------------------
    // Escenario: Registrar una mascota exitosamente (pet.feature)
    // ------------------------------------------------------------------
    // Paso que establece los datos válidos de una mascota utilizando Faker para generar un ID aleatorio, un nombre de animal aleatorio y un estado "available". Los datos se almacenan en el contexto del escenario para su uso posterior.
    @Given("que tengo los datos válidos de una mascota")
    public void queTengoLosDatosValidosDeUnaMascota() {
        Pet pet = new Pet();
        pet.setId(faker.number().numberBetween(1000, 999999));
        pet.setName(faker.animal().name());
        pet.setStatus("available");
        context.setPet(pet);
    }
    // Paso que envía una solicitud para crear la mascota utilizando el método crearMascota del PetClient. La respuesta de la API se almacena en el contexto del escenario, junto con el ID de la mascota creada.
    @When("envío una solicitud para crear la mascota")
    public void envioUnaSolicitudParaCrearLaMascota() {
        Response response = petClient.crearMascota(context.getPet());
        context.setResponse(response);
        context.setPetId(response.jsonPath().getLong("id"));
    }
    // Paso que verifica que la API responde con el código de estado esperado. Se compara el código de estado de la respuesta almacenada en el contexto del escenario con el valor proporcionado en el paso.
    @Then("la API responde con código {int}")
    public void laApiRespondeConCodigo(Integer statusCode) {
        assertEquals(statusCode, context.getResponse().getStatusCode());
    }
    // Paso que verifica que la mascota queda registrada correctamente. Se comprueba que la respuesta no sea nula y que el ID de la mascota creada coincida con el ID almacenado en el contexto del escenario.
    @And("la mascota queda registrada correctamente")
    public void laMascotaQuedaRegistradaCorrectamente() {
        assertNotNull(context.getResponse());
        assertEquals(context.getPet().getId(), context.getPetId());
    }

    // ------------------------------------------------------------------
    // Background: mascota ya existente (pet_management.feature)
    // ------------------------------------------------------------------
    // Paso que asegura que existe una mascota registrada antes de ejecutar los escenarios de prueba. Se crea una mascota con un ID aleatorio, un nombre de animal aleatorio y un estado "available", y se utiliza el método crearMascota del PetClient para registrarla en la API. La información de la mascota y la respuesta se almacenan en el contexto del escenario.
    @Given("que existe una mascota registrada")
    public void queExisteUnaMascotaRegistrada() {
        Pet pet = new Pet();
        pet.setId(faker.number().numberBetween(1000, 999999));
        pet.setName(faker.animal().name());
        pet.setStatus("available");
        context.setPet(pet);

        Response response = petClient.crearMascota(pet);
        context.setResponse(response);
        context.setPetId(response.jsonPath().getLong("id"));
    }

    // ------------------------------------------------------------------
    // Escenario: Consultar una mascota
    // ------------------------------------------------------------------
    // Paso que envía una solicitud para obtener la información de la mascota registrada utilizando el método obtenerMascotaPorId del PetClient. La respuesta de la API se almacena en el contexto del escenario.
    @When("consulto la mascota por su ID")
    public void consultoLaMascotaPorSuId() {
        context.setResponse(petClient.obtenerMascotaPorId(context.getPetId()));
    }
    // Paso que verifica que la información de la mascota se obtiene correctamente. Se convierte la respuesta almacenada en el contexto del escenario a un objeto Pet y se realizan aserciones para comprobar que el código de estado sea 200 y que el ID de la mascota obtenida coincida con el ID esperado.
    @Then("obtengo la información correctamente")
    public void obtengoLaInformacionCorrectamente() {
        Pet mascotaObtenida = context.getResponse().as(Pet.class);
        assertEquals(200, context.getResponse().getStatusCode());
        assertEquals(context.getPetId(), mascotaObtenida.getId());
    }
    
    @When("consulto una mascota {string}")
    public void consultoUnaMascota(String situacion) {
        long idAConsultar = "inexistente".equals(situacion)
                ? faker.number().numberBetween(900000000, 999999999)
                : context.getPetId();
        context.setResponse(petClient.obtenerMascotaPorId(idAConsultar));
    }
    // Paso que verifica que la API responde con un código de estado 404 al consultar una mascota inexistente. Se comprueba que el código de estado de la respuesta almacenada en el contexto del escenario sea 404.
    @When("busco mascotas con estado {string}")
    public void buscoMascotasConEstado(String estado) {
        context.setResponse(petClient.obtenerMascotasPorEstado(estado));
    }
    //  Paso que verifica que se obtiene al menos una mascota en la lista de mascotas filtradas por estado. Se convierte la respuesta almacenada en el contexto del escenario a un arreglo de objetos Pet y se realiza una aserción para comprobar que la longitud del arreglo sea mayor a cero.
    @And("obtengo al menos una mascota en la lista")
    public void obtengoAlMenosUnaMascotaEnLaLista() {
        Pet[] mascotas = context.getResponse().as(Pet[].class);
        assertTrue(mascotas.length > 0);
    }

    // ------------------------------------------------------------------
    // Escenario: Actualizar una mascota
    // ------------------------------------------------------------------
    // Paso que modifica los datos de la mascota registrada. Se almacena el nombre original de la mascota en el contexto del escenario, se actualizan el nombre y el estado de la mascota, y se envía una solicitud de actualización utilizando el método actualizarMascota del PetClient. La respuesta de la API se almacena en el contexto del escenario.
    @When("modifico los datos de la mascota")
    public void modificoLosDatosDeLaMascota() {
        context.setNombreOriginal(context.getPet().getName());
        context.getPet().setName(faker.animal().name());
        context.getPet().setStatus("sold");
        context.setResponse(petClient.actualizarMascota(context.getPet()));
    }
    // Paso que verifica que la información de la mascota queda actualizada correctamente. Se obtiene la información actualizada de la mascota utilizando el método obtenerMascotaPorId del PetClient, se convierte la respuesta a un objeto Pet y se realizan aserciones para comprobar que el código de estado sea 200, que el nombre de la mascota haya cambiado y que el estado coincida con el valor esperado.
    @Then("la información queda actualizada correctamente")
    public void laInformacionQuedaActualizadaCorrectamente() {
        Response getResponse = petClient.obtenerMascotaPorId(context.getPetId());
        Pet mascotaActualizada = getResponse.as(Pet.class);

        assertEquals(200, context.getResponse().getStatusCode());
        assertNotEquals(context.getNombreOriginal(), mascotaActualizada.getName());
        assertEquals(context.getPet().getStatus(), mascotaActualizada.getStatus());
    }

    // ------------------------------------------------------------------
    // Escenario: Eliminar una mascota
    // ------------------------------------------------------------------
    // Paso que envía una solicitud para eliminar la mascota registrada utilizando el método eliminarMascotaPorId del PetClient. La respuesta de la API se almacena en el contexto del escenario.
    @When("elimino la mascota")
    public void eliminoLaMascota() {
        context.setResponse(petClient.eliminarMascotaPorId(context.getPetId()));
    }
    // Paso que verifica que la mascota deja de existir después de ser eliminada. Se realiza una solicitud para obtener la información de la mascota utilizando el método obtenerMascotaPorId del PetClient y se realizan aserciones para comprobar que el código de estado de la respuesta de eliminación sea 200 y que el código de estado de la respuesta de obtención sea 404.
    @Then("la mascota deja de existir")
    public void laMascotaDejaDeExistir() {
        Response getResponse = petClient.obtenerMascotaPorId(context.getPetId());

        assertEquals(200, context.getResponse().getStatusCode());
        assertEquals(404, getResponse.getStatusCode());
    }

    // ------------------------------------------------------------------
    // Limpieza de datos después de cada escenario
    // ------------------------------------------------------------------
    // Método que se ejecuta después de cada escenario para limpiar los datos creados durante la prueba. Si el ID de la mascota almacenado en el contexto del escenario no es cero, se envía una solicitud para eliminar la mascota utilizando el método eliminarMascotaPorId del PetClient.
    @After
    public void limpiarDatos() {
        if (context.getPetId() != 0) {
            petClient.eliminarMascotaPorId(context.getPetId());
        }
    }
}