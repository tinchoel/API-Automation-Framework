package steps;
// Esta clase contiene los pasos de Cucumber que definen el comportamiento esperado de la API de mascotas (Petstore). Cada método está anotado con @Given, @When, @Then o @And para mapear los pasos del archivo de características (feature) a la implementación en Java. Se utilizan aserciones para validar las respuestas de la API y garantizar que el comportamiento sea el esperado.
import clients.PetClient;
import com.github.javafaker.Faker;
// Importamos las clases necesarias de Cucumber para definir los pasos de prueba, así como las clases de Rest Assured y el modelo Pet para interactuar con la API y validar las respuestas.
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
// Importamos las clases de aserción de JUnit para validar los resultados de las pruebas y garantizar que la API se comporte según lo esperado.
import io.restassured.response.Response;
import models.Pet;
// Importamos las clases de aserción de JUnit para validar los resultados de las pruebas y garantizar que la API se comporte según lo esperado.
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
// La clase PetSteps contiene los pasos de Cucumber que definen el comportamiento esperado de la API de mascotas (Petstore). Cada método está anotado con @Given, @When, @Then o @And para mapear los pasos del archivo de características (feature) a la implementación en Java. Se utilizan aserciones para validar las respuestas de la API y garantizar que el comportamiento sea el esperado.
public class PetSteps {
    // Instancia de PetClient para realizar operaciones CRUD sobre el recurso Pet
    private final PetClient petClient = new PetClient();
    private final Faker faker = new Faker();
    // Variables para almacenar la mascota, la respuesta de la API, el ID de la mascota y el nombre original antes de una actualización. Estas variables se utilizan a lo largo de los pasos de Cucumber para mantener el estado entre los diferentes métodos y permitir la validación de las respuestas de la API.
    private Pet pet;
    private Response response;
    private long petId;
    private String nombreOriginal;
    // Hook que se ejecuta después de cada escenario de prueba. Si se ha creado una mascota (petId != 0), se elimina para limpiar los datos y evitar que queden registros residuales en la API después de la ejecución de las pruebas.
    @After
    public void limpiarDatos() {
    if (petId != 0) {
        petClient.eliminarMascotaPorId(petId);
    }
}

    // ---------- pet.feature: Registrar una mascota ----------
    // Paso que define los datos válidos de una mascota. Se utiliza la biblioteca Faker para generar un ID aleatorio, un nombre de animal y establecer el estado como "available". Esto permite crear mascotas únicas para cada escenario de prueba sin depender de datos preexistentes.
    @Given("que tengo los datos válidos de una mascota")
    public void queTengoLosDatosValidosDeUnaMascota() {
        pet = new Pet();
        pet.setId(faker.number().numberBetween(1000, 999999));
        pet.setName(faker.animal().name());
        pet.setStatus("available");
    }
    // Paso que envía una solicitud para crear la mascota utilizando el método crearMascota del PetClient. La respuesta de la API se almacena en la variable response y se extrae el ID de la mascota creada para su posterior validación y limpieza de datos.
    @When("envío una solicitud para crear la mascota")
    public void envioUnaSolicitudParaCrearLaMascota() {
        response = petClient.crearMascota(pet);
        petId = response.jsonPath().getLong("id");
    }
    // Paso que valida que la API responde con el código de estado esperado. Se utiliza una aserción para comparar el código de estado de la respuesta con el valor proporcionado en el escenario de prueba, asegurando que la operación se haya realizado correctamente.
    @Then("la API responde con código {int}")
    public void laApiRespondeConCodigo(Integer statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }
    // Paso que valida que la mascota ha sido registrada correctamente. Se verifica que la respuesta no sea nula y que el ID de la mascota creada coincida con el ID esperado, asegurando que la operación de creación se haya realizado con éxito.
    @And("la mascota queda registrada correctamente")
    public void laMascotaQuedaRegistradaCorrectamente() {
        assertNotNull(response);
        assertEquals(pet.getId(), petId);
    }

    // ---------- pet_management.feature: Background ----------
    // Paso que asegura que existe una mascota registrada antes de ejecutar los escenarios de prueba. Se crea una nueva mascota utilizando datos aleatorios generados por Faker y se envía una solicitud para registrarla en la API. El ID de la mascota creada se almacena para su posterior uso en los pasos de consulta, actualización y eliminación.
    @Given("que existe una mascota registrada")
    public void queExisteUnaMascotaRegistrada() {
        pet = new Pet();
        pet.setId(faker.number().numberBetween(1000, 999999));
        pet.setName(faker.animal().name());
        pet.setStatus("available");

        response = petClient.crearMascota(pet);
        petId = response.jsonPath().getLong("id");
    }

    // ---------- Consultar ----------
    // Paso que envía una solicitud para consultar la información de la mascota por su ID utilizando el método obtenerMascotaPorId del PetClient. La respuesta de la API se almacena en la variable response para su posterior validación.
    @When("consulto la mascota por su ID")
    public void consultoLaMascotaPorSuId() {
        response = petClient.obtenerMascotaPorId(petId);
    }
    // Paso que valida que la información de la mascota obtenida coincide con la esperada. Se convierte la respuesta de la API en un objeto Pet y se realizan aserciones para verificar que el código de estado sea 200 y que el ID de la mascota obtenida coincida con el ID esperado.
    @Then("obtengo la información correctamente")
    public void obtengoLaInformacionCorrectamente() {
        Pet mascotaObtenida = response.as(Pet.class);
        assertEquals(200, response.getStatusCode());
        assertEquals(petId, mascotaObtenida.getId());
    }
    // Paso que envía una solicitud para consultar una mascota específica según la situación proporcionada (existente o inexistente). Si la situación es "inexistente", se genera un ID aleatorio que no corresponde a ninguna mascota registrada; de lo contrario, se utiliza el ID de la mascota creada previamente. La respuesta de la API se almacena en la variable response para su posterior validación.
    @When("consulto una mascota {string}")
    public void consultoUnaMascota(String situacion) {
    long idAConsultar = "inexistente".equals(situacion)
            ? faker.number().numberBetween(900000000, 999999999)
            : petId;
    response = petClient.obtenerMascotaPorId(idAConsultar);
    }
    // Paso que valida que la API responde con el código de estado esperado al consultar una mascota inexistente. Se utiliza una aserción para comparar el código de estado de la respuesta con el valor proporcionado en el escenario de prueba, asegurando que la operación se haya manejado correctamente.
    @When("busco mascotas con estado {string}")
    public void buscoMascotasConEstado(String estado) {
    response = petClient.obtenerMascotasPorEstado(estado);
    }
    // Paso que valida que la API responde con el código de estado esperado al consultar mascotas por estado. Se utiliza una aserción para comparar el código de estado de la respuesta con el valor proporcionado en el escenario de prueba, asegurando que la operación se haya manejado correctamente.
    @And("obtengo al menos una mascota en la lista")
    public void obtengoAlMenosUnaMascotaEnLaLista() {
    Pet[] mascotas = response.as(Pet[].class);
    assertTrue(mascotas.length > 0);
    }

    // ---------- Actualizar ----------
    // Paso que modifica los datos de la mascota existente. Se almacena el nombre original antes de la actualización, se cambia el nombre a un nuevo valor generado por Faker y se actualiza el estado a "sold". Luego, se envía una solicitud para actualizar la mascota utilizando el método actualizarMascota del PetClient. La respuesta de la API se almacena en la variable response para su posterior validación.
    @When("modifico los datos de la mascota")
    public void modificoLosDatosDeLaMascota() {
        nombreOriginal = pet.getName();
        pet.setName(faker.animal().name());
        pet.setStatus("sold");
        response = petClient.actualizarMascota(pet);
    }
    // Paso que valida que la información de la mascota ha sido actualizada correctamente. Se envía una solicitud para obtener la mascota por su ID y se convierte la respuesta en un objeto Pet. Luego, se realizan aserciones para verificar que el código de estado sea 200, que el nombre de la mascota haya cambiado respecto al nombre original y que el estado coincida con el valor actualizado.
    @Then("la información queda actualizada correctamente")
    public void laInformacionQuedaActualizadaCorrectamente() {
        Response getResponse = petClient.obtenerMascotaPorId(petId);
        Pet mascotaActualizada = getResponse.as(Pet.class);

        assertEquals(200, response.getStatusCode());
        assertNotEquals(nombreOriginal, mascotaActualizada.getName());
        assertEquals(pet.getStatus(), mascotaActualizada.getStatus());
    }

    // ---------- Eliminar ----------
    // Paso que envía una solicitud para eliminar la mascota por su ID utilizando el método eliminarMascotaPorId del PetClient. La respuesta de la API se almacena en la variable response para su posterior validación.
    @When("elimino la mascota")
    public void eliminoLaMascota() {
        response = petClient.eliminarMascotaPorId(petId);
    }
    // Paso que valida que la mascota ha sido eliminada correctamente. Se envía una solicitud para obtener la mascota por su ID y se verifica que la API responda con el código de estado 404, indicando que la mascota ya no existe en la base de datos.
    @Then("la mascota deja de existir")
    public void laMascotaDejaDeExistir() {
        Response getResponse = petClient.obtenerMascotaPorId(petId);

        assertEquals(200, response.getStatusCode());
        assertEquals(404, getResponse.getStatusCode());
    }
}