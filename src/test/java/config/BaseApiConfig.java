package config;

//importamos io.restassured.RestAssured, RequestLoggingFilter y ResponseLoggingFilter para configurar la base URI, base path y puerto de la API, así como para habilitar el registro de solicitudes y respuestas en las pruebas.
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;

//Esta clase configura los parámetros base para las llamadas a la API de Swagger Petstore
public class BaseApiConfig {

    // Método de configuración que se ejecuta antes de todas las pruebas
    @BeforeAll
    // Configura la base URI, base path y puerto de la API, y habilita el registro
    // de solicitudes y respuestas.
    public static void setup() {
        RestAssured.baseURI = ConfigReader.get("base.uri");
        RestAssured.basePath = ConfigReader.get("base.path");
        RestAssured.port = ConfigReader.getInt("base.port");

        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter());
    }
}