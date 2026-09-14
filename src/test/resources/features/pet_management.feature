Feature: Gestión de mascotas existentes
  Como usuario de Swagger Petstore
  Quiero consultar, actualizar y eliminar una mascota
  Para mantener actualizada la información de mis mascotas

  Background:
    Given que existe una mascota registrada

  Scenario: Consultar una mascota
    When consulto la mascota por su ID
    Then obtengo la información correctamente

  Scenario: Actualizar una mascota
    When modifico los datos de la mascota
    Then la información queda actualizada correctamente

  Scenario: Eliminar una mascota
    When elimino la mascota
    Then la mascota deja de existir

  Scenario Outline: Consultar una mascota según su existencia
    When consulto una mascota "<situacion>"
    Then la API responde con código <statusCode>

    Examples:
      | situacion   | statusCode |
      | existente   |        200 |
      | inexistente |        404 |

  Scenario: Buscar mascotas por estado disponible
    When busco mascotas con estado "available"
    Then la API responde con código 200
    And obtengo al menos una mascota en la lista
