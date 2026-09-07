Feature: Gestión de mascotas
  Como usuario de Swagger Petstore
  Quiero registrar una mascota
  Para poder consultarla posteriormente

  @severity=critical
  Scenario: Registrar una mascota exitosamente
    Given que tengo los datos válidos de una mascota
    When envío una solicitud para crear la mascota
    Then la API responde con código 200
    And la mascota queda registrada correctamente
