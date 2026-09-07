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
