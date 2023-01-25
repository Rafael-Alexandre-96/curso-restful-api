package br.com.curso.exceptions;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class ExceptionResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private OffsetDateTime timestamp;
	private String message;
	private String details;

	public ExceptionResponse(OffsetDateTime timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	
	public OffsetDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
}
