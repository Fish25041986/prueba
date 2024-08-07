package com.thomasgregsons.ecommerce.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.RandomStringUtils;


@Component
public class Util {
	
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	/**
	 * Este metodo comprime los bytes de la imagen para se guardados en la base de datos
	 * 
	 * @param data
	 * @return
	 */
	public  byte[] compressZLib(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
	
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
	
		return outputStream.toByteArray();
	}
	
	/**
	 * Este metodo descomprime los bytes de laimagen para ser utilizadaa en el front 
	 * 
	 * @param data
	 * @return
	 */
	public byte[] decompressZLib(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
	
	/**
	 * Este metodo genera el usuario de forma automatica a partir del 
	 * nombre, apellido y un numero aleatorio
	 * 
	 * @param nombre
	 * @param apellido
	 * @return la unión de nombre, apellido y un numero aleatorio
	 */
	public  String generarNombreUsuario(String nombre, String apellido) {
		 Random random = new Random();
		 int numeroAleatorio = random.nextInt(100) + 10;
		
		 return nombre + "." + apellido +numeroAleatorio;
	}

    /**
     * Este metodo genera una contraseña de forma automatica para el usuario nuevo
     * 
     * @param null
     * @return String contraseña
     */
	public String generadorContraseña() {
        String contraseña = RandomStringUtils.randomAlphanumeric(7, 9);
        return contraseña;
    }
	
	public String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
	
	/**
	 * Determina si una fecha dada es anterior a la fecha actual.
	 *
	 * @param fecha La fecha a comparar.
	 * @return `true` si la fecha es anterior a la fecha actual, `false` en caso contrario.
	 */
	public static boolean esAnteriorAHoy(Date fecha) {
        LocalDate fechaLocalDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate hoy = LocalDate.now();
        return fechaLocalDate.isBefore(hoy);
    }
	
	/**
	 * Metodo solo para retornar fechas actuales en formato Localdate
	 * Nota: solo para mostrar el manejo que tengo en difrentes formatos defecchas
	 * 
	 * @return
	 */
	public static LocalDate fechaActualLocalDate() {
		LocalDate fechaActualLocalDate = LocalDate.now();
		return fechaActualLocalDate;
	}
	
	/**
	 * Metodo solo para retornar fechas actuales en formato Date
	 * Nota: solo para mostrar el manejo que tengo en difrentes formatos defecchas
	 * 
	 * @return
	 */
	public static Date fechaActualDate() {
		Date fechaActualDate = new Date(System.currentTimeMillis());
		return fechaActualDate;
	}
	/**
	 * Metodo que utilizo para gestionar el descuento del 50% a la orden
	 * 
	 * @return
	 */
	public static boolean obtenerDescuento() {
        Random random = new Random();
        return random.nextInt(2) == 1;
    }
	
}

