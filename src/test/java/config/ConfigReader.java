package config;

// importamos java.io.IOException, InputStream y Properties para poder leer el archivo de configuración config.properties y manejar posibles excepciones de entrada/salida
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Clase utilitaria responsable de leer la configuración del proyecto desde
// el archivo config.properties (src/test/resources), evitando valores
// hardcodeados dentro del código.
public class ConfigReader {
// Creamos una instancia de Properties para almacenar las propiedades leídas del archivo config.properties
    private static final Properties properties = new Properties();
// Cargamos el archivo config.properties al inicializar la clase ConfigReader, para que los valores de configuración estén disponibles en toda la suite de pruebas.
    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontró config.properties en el classpath.");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer config.properties", e);
        }
    }
// Método para obtener un valor de configuración como String dado su clave
    public static String get(String key) {
        return properties.getProperty(key);
    }
// Método para obtener un valor de configuración como int dado su clave
    public static int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }
}
