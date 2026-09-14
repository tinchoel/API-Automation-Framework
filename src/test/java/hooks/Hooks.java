package hooks;
// Esta clase contiene hooks de Cucumber que se ejecutan antes de cada escenario de prueba. Se utiliza para configurar la API antes de ejecutar los tests, asegurando que la configuración base (URI, path, puerto) esté establecida correctamente.
import config.BaseApiConfig;
import io.cucumber.java.Before;

// La clase Hooks se encarga de configurar la API antes de ejecutar los escenarios de prueba. Utiliza un hook @Before para asegurarse de que la configuración base (URI, path, puerto) esté establecida correctamente antes de cada escenario. Esto garantiza que todas las pruebas se ejecuten en un entorno consistente y evita problemas relacionados con la configuración de la API.
public class Hooks {
    // Variable estática para asegurarse de que la configuración de la API solo se realice una vez, evitando configuraciones repetidas antes de cada escenario.
    private static boolean apiConfigurada = false;
    // Hook que se ejecuta antes de cada escenario de prueba. Verifica si la API ya ha sido configurada; si no, llama al método setup() de BaseApiConfig para establecer la configuración base de la API (URI, path, puerto) y marca la variable apiConfigurada como true para evitar configuraciones repetidas.
    @Before
    public void configurarApi() {
        if (!apiConfigurada) {
            BaseApiConfig.setup();
            apiConfigurada = true;
        }
    }
}