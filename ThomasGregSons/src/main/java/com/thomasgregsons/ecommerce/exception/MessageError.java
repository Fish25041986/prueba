package com.thomasgregsons.ecommerce.exception;

public enum MessageError {
	 
	  FIND_ALL_FAIL("No existe información registrada en el momento"),
	  FIND_BY_ID_FAIL("No se encontro información relacionada al identificador proporcionado"),
	  SAVE_FAIL("No se pudo guardar la información en la base de datos"),
	  UPDATE_FAIL("No se pudo realizar la actualización"),
	  INFORMACION_DUPLICADA("Ya existe información registra con los mismo datos"),
	  DESCOMPRIMIR_IMAGEN_FAIL("Ocurrio un error al decomprimir imagenes"),
	  DELETE_FAIL_INFORMACION_RELACIONADA("No se puede eliminar ya que existe información relacinada"),
	  USUARIO_FAIL("No existe el usuario ingresado"),
	  ESTADO_INACTIVO_O_NO_EXISTE("Se encuentra inacticvo y/o no existe"),
	  RANGO_FECHA_EXISTENTE("en este momento existe un rango de fechas"),
	  RANGO_FECHA_FAIL("No se puede crear un rango de fecha inferior o igual a la  fecha: ");

	  private final String mensaje;

	  MessageError(String mensaje) {
	        this.mensaje = mensaje;
	  }

	  public String getMensaje() {
	        return mensaje;
	  }
	  
}
