package com.serverless.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

/**
 * Classe che riflette la tabella users.
 * Essa contiene l'id, il nome, il cognome e l'indirizzo email.
 * **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "users")
public class User {
	@DynamoDBHashKey(attributeName = "id")
	private Integer id;

	@DynamoDBAttribute(attributeName = "nome")
	private String nome;

	@DynamoDBAttribute(attributeName = "cognome")
	private String cognome;

	@DynamoDBAttribute(attributeName = "email")
	private String email;
}
