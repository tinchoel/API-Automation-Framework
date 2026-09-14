package runners;
//  Esta clase es el punto de entrada para ejecutar las pruebas de Cucumber. Configura el entorno de prueba, especifica la ubicación de los archivos de características y define los paquetes que contienen los pasos y hooks necesarios para ejecutar los escenarios de prueba.
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
// Importamos las anotaciones y clases necesarias de JUnit y Cucumber para configurar la ejecución de pruebas, incluyendo la selección de recursos de clase, la inclusión de motores de prueba y la configuración de parámetros.
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
// La anotación @Suite indica que esta clase es una suite de pruebas que agrupa múltiples escenarios de prueba de Cucumber. La anotación @IncludeEngines("cucumber") especifica que se debe utilizar el motor de prueba de Cucumber para ejecutar los escenarios. La anotación @SelectClasspathResource("features") indica la ubicación de los archivos de características (features) que contienen los escenarios de prueba escritos en lenguaje Gherkin. Los parámetros de configuración adicionales, como GLUE_PROPERTY_NAME y PLUGIN_PROPERTY_NAME, se utilizan para definir los paquetes que contienen los pasos y hooks necesarios para ejecutar los escenarios, así como para configurar la salida del reporte.
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "steps,hooks")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
public class CucumberTestRunner {
}