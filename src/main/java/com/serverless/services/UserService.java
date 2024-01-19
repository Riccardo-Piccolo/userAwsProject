package com.serverless.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.serverless.Utilities;
import com.serverless.entity.User;

import java.util.Map;

/**
 * Service che contiene le varie implementazioni dei metodi per effettuare le richieste e
 * restituirne i risultati.
 * Inoltre contiene metodi per l'inizializzazione del DB e la creazione delle risposte.
 **/
public class UserService {

	private DynamoDBMapper dynamoDBMapper;
	private String jsonBody;

	/**
	 * Metodo per eseguire la funzionalità di creazione utente partendo dalla request
	 **/
	public APIGatewayProxyResponseEvent createUser(APIGatewayProxyRequestEvent apiGatewayRequest, Context context) {
		// inizializzo il database
		initializeDatabase();

		// ottenimento dell'utente dalla request
		User user = Utilities.convertStringToObject(apiGatewayRequest.getBody(), context);

		// conteggio dei record in tabella ed incremento consequenziale dell'id del nuovo record
		// in modo tale da simulare un auto-increment
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		int itemCount = dynamoDBMapper.count(User.class, scanExpression);
		user.setId(itemCount + 1);

		// salvataggio dell'utente a DB
		dynamoDBMapper.save(user);

		// conversione dell'oggetto utente in una stringa per essere stampato nella risposta
		jsonBody = Utilities.convertObjectToString(user, context);

		// scrittura nel log
		context.getLogger().log("Operazione di salvataggio utente effettuata con successo. Risultato: " + jsonBody);

		// risposta creata dal metodo
		return createAPIResponse(jsonBody, 201, Utilities.createHeaders());
	}

	/**
	 * Metodo per eseguire la funzionalità di ricerca utente tramite id
	 **/

	public APIGatewayProxyResponseEvent getUserById(APIGatewayProxyRequestEvent apiGatewayRequest, Context context) {
		// inizializzazione del DB
		initializeDatabase();

		// ottenimento dell'id da ricercare dalla request
		Integer userId = Integer.valueOf(apiGatewayRequest.getPathParameters().get("userId"));

		// ottenimento dell'utente tramite id
		User user = dynamoDBMapper.load(User.class, userId);

		// se l'utente è stato trovato, si restituisce il json corrispondente
		// altrimenti si ritorna una stringa di errore

		if (user != null) {
			jsonBody = Utilities.convertObjectToString(user, context);
			context.getLogger().log("Utente trovato con successo. Risultato: " + jsonBody);
			return createAPIResponse(jsonBody, 200, Utilities.createHeaders());
		} else {
			jsonBody = "Utente con id " + userId + " non trovato";
			return createAPIResponse(jsonBody, 400, Utilities.createHeaders());
		}

	}

	/**
	 * Metodo per l'inizializzazione del database
	 **/
	private void initializeDatabase() {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
		dynamoDBMapper = new DynamoDBMapper(client);
	}

	/**
	 * Crea la risposta da restituire all'utente
	 **/
	private APIGatewayProxyResponseEvent createAPIResponse(String body, int statusCode, Map<String, String> headers) {
		APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
		responseEvent.setBody(body);
		responseEvent.setHeaders(headers);
		responseEvent.setStatusCode(statusCode);
		return responseEvent;
	}
}
