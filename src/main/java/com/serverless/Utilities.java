package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe contenente metodi di utilità
 **/
public class Utilities {

	private Utilities() {

	}

	/**
	 * Metodo per la creazione degli headers per la response
	 **/
	public static Map<String,String> createHeaders(){
		Map<String,String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("X-amazon-author", "Riccardo");
		headers.put("X-amazon-apiVersion", "v1");
		return headers;
	}

	/**
	 * Metodo per la conversione di un Object in una stringa
	 **/
	public static String convertObjectToString(User user, Context context){
		String jsonBody = null;
		try {
			jsonBody = new ObjectMapper().writeValueAsString(user);
		} catch (JsonProcessingException e) {
			context.getLogger().log("Errore nella conversione da oggetto a stringa. Messaggio: " + e.getMessage());
		}
		return jsonBody;
	}

	/**
	 * Metodo per la conversione di una stringa in formato JSON in una stringa
	 **/
	public static User convertStringToObject(String jsonBody,Context context){
		User user = null;
		try {
			user = new ObjectMapper().readValue(jsonBody,User.class);
		} catch (JsonProcessingException e) {
			context.getLogger().log("Errore nella conversione da stringa ad oggetto. Messaggio:" + e.getMessage());
		}
		return user;
	}

	/**
	 * Metodo per la conversione di una lista di oggetti in una stringa
	 **/
	public static String convertListOfObjectToString(List<User> users, Context context){
		String jsonBody = null;
		try {
			jsonBody =   new ObjectMapper().writeValueAsString(users);
		} catch (JsonProcessingException e) {
			context.getLogger().log("Errore nella conversione da oggetto a stringa. Messaggio:" + e.getMessage());
		}
		return jsonBody;
	}
}