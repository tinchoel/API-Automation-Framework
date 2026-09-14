package steps;

import clients.PetClient;
import com.github.javafaker.Faker;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;

import io.restassured.response.Response;
import models.Pet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PetSteps {

    private final PetClient petClient = new PetClient();
    private final Faker faker = new Faker();

    private Pet pet;
    private Response response;
    private long petId;
    private String nombreOriginal;

    @After
    public void limpiarDatos() {
    if (petId != 0) {
        petClient.eliminarMascotaPorId(petId);
    }
}

    // ---------- pet.feature: Registrar una mascota ----------

    @Given("que tengo los datos válidos de una mascota")
    public void queTengoLosDatosValidosDeUnaMascota() {
        pet = new Pet();
        pet.setId(faker.number().numberBetween(1000, 999999));
        pet.setName(faker.animal().name());
        pet.setStatus("available");
    }

    @When("envío una solicitud para crear la mascota")
    public void envioUnaSolicitudParaCrearLaMascota() {
        response = petClient.crearMascota(pet);
        petId = response.jsonPath().getLong("id");
    }

    @Then("la API responde con código {int}")
    public void laApiRespondeConCodigo(Integer statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @And("la mascota queda registrada correctamente")
    public void laMascotaQuedaRegistradaCorrectamente() {
        assertNotNull(response);
        assertEquals(pet.getId(), petId);
    }

    // ---------- pet_management.feature: Background ----------

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

    @When("consulto la mascota por su ID")
    public void consultoLaMascotaPorSuId() {
        response = petClient.obtenerMascotaPorId(petId);
    }

    @Then("obtengo la información correctamente")
    public void obtengoLaInformacionCorrectamente() {
        Pet mascotaObtenida = response.as(Pet.class);
        assertEquals(200, response.getStatusCode());
        assertEquals(petId, mascotaObtenida.getId());
    }

    @When("consulto una mascota {string}")
    public void consultoUnaMascota(String situacion) {
    long idAConsultar = "inexistente".equals(situacion)
            ? faker.number().numberBetween(900000000, 999999999)
            : petId;
    response = petClient.obtenerMascotaPorId(idAConsultar);
    }

    @When("busco mascotas con estado {string}")
    public void buscoMascotasConEstado(String estado) {
    response = petClient.obtenerMascotasPorEstado(estado);
    }

    @And("obtengo al menos una mascota en la lista")
    public void obtengoAlMenosUnaMascotaEnLaLista() {
    Pet[] mascotas = response.as(Pet[].class);
    assertTrue(mascotas.length > 0);
    }

    // ---------- Actualizar ----------

    @When("modifico los datos de la mascota")
    public void modificoLosDatosDeLaMascota() {
        nombreOriginal = pet.getName();
        pet.setName(faker.animal().name());
        pet.setStatus("sold");
        response = petClient.actualizarMascota(pet);
    }

    @Then("la información queda actualizada correctamente")
    public void laInformacionQuedaActualizadaCorrectamente() {
        Response getResponse = petClient.obtenerMascotaPorId(petId);
        Pet mascotaActualizada = getResponse.as(Pet.class);

        assertEquals(200, response.getStatusCode());
        assertNotEquals(nombreOriginal, mascotaActualizada.getName());
        assertEquals(pet.getStatus(), mascotaActualizada.getStatus());
    }

    // ---------- Eliminar ----------

    @When("elimino la mascota")
    public void eliminoLaMascota() {
        response = petClient.eliminarMascotaPorId(petId);
    }

    @Then("la mascota deja de existir")
    public void laMascotaDejaDeExistir() {
        Response getResponse = petClient.obtenerMascotaPorId(petId);

        assertEquals(200, response.getStatusCode());
        assertEquals(404, getResponse.getStatusCode());
    }
}