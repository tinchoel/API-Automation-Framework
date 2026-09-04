package utils;

//importamos models.Pet, java.io.BufferedReader, java.io.IOException, java.io.InputStream, java.io.InputStreamReader, java.nio.charset.StandardCharsets, java.util.ArrayList y java.util.List para poder leer datos de prueba de mascotas desde un archivo CSV externo (src/test/resources), desacoplando los datos de prueba de la lógica de automatización.
import models.Pet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// Clase utilitaria encargada de leer datos de prueba de mascotas desde un
// archivo CSV externo (src/test/resources), desacoplando los datos de
// prueba de la lógica de automatización.
public class PetCsvReader {

    public static List<Pet> leerMascotas(String nombreArchivo) {
        List<Pet> mascotas = new ArrayList<>();

        try (InputStream input = PetCsvReader.class.getClassLoader().getResourceAsStream(nombreArchivo)) {
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo " + nombreArchivo + " en el classpath.");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
                String linea;
                boolean esEncabezado = true;

                while ((linea = reader.readLine()) != null) {
                    if (esEncabezado) {
                        esEncabezado = false;
                        continue;
                    }
                    if (linea.isBlank()) {
                        continue;
                    }

                    String[] campos = linea.split(",");
                    Pet pet = new Pet();
                    pet.setId(Integer.parseInt(campos[0].trim()));
                    pet.setName(campos[1].trim());
                    pet.setStatus(campos[2].trim());

                    mascotas.add(pet);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo " + nombreArchivo, e);
        }

        return mascotas;
    }
}
