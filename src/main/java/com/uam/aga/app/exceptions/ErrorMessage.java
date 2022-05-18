package com.uam.aga.app.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ErrorMessage {

	@JsonProperty("message")
	private String message;

	@JsonProperty("statusCode")
	private int statusCode;

	@JsonProperty("uri")
	private String uriRequested;

	public ErrorMessage(int statusCode, String message, String uriRequested) {
		this.message = message;
		this.statusCode = statusCode;
		this.uriRequested = uriRequested;
	}

}
