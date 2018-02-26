package com.revature.caliber.service;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.caliber.model.Note;
import com.revature.caliber.model.SimpleNote;

@Service
public class NoteRepositoryMessagingService {
	
	@Autowired
	private NoteRepositoryRequestDispatcher noteRepositoryRequestDispatcher;
	
	/**
	 * Receives a message from the single SimpleNote RabbitMQ queue, parses the message string as a JsonObject,
	 * and passes it to the request dispatcher.
	 * @param message The message received from the messaging queue
	 * @return The simple note returned by the request dispatcher
	 */
	@RabbitListener(queues = "revature.caliber.repos.note")
	public SimpleNote receiveSingleSimpleNoteRequest(String message) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(message);
		JsonObject request = element.getAsJsonObject();
		
		return noteRepositoryRequestDispatcher.processSingleSimpleNoteRequest(request);
	}
	
	/**
	 * Receives a message from the list SimpleNote RabbitMQ queue, parses the message string as a JsonObject,
	 * and passes it to the request dispatcher.
	 * @param message The message received from the messaging queue
	 * @return The list of simple notes returned by the request dispatcher
	 */
	@RabbitListener(queues = "revature.caliber.repos.note.list")
	public List<SimpleNote> receiveListSimpleNoteRequest(String message) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(message);
		JsonObject request = element.getAsJsonObject();
		
		return noteRepositoryRequestDispatcher.processListSimpleNoteRequest(request);
	}
	
	/**
	 * Receives a message from the single Note RabbitMQ queue, parses the message string as a JsonObject,
	 * and passes it to the request dispatcher.
	 * @param message The message received from the messaging queue
	 * @return The note returned by the request dispatcher
	 */
	@RabbitListener(queues = "revature.caliber.service.note.list")
//	@RabbitListener(queues = "revature.caliber.service.test.list")
	public List<Note> receiveListNoteRequest(String message) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(message);
		JsonObject request = element.getAsJsonObject();
		System.out.println(message);
		return noteRepositoryRequestDispatcher.processListNoteRequest(request);
	}
	/**
	 * Receives a message from the single Note RabbitMQ queue, parses the message string as a JsonObject,
	 * and passes it to the request dispatcher.
	 * @param message The message received from the messaging queue
	 * @return The note returned by the request dispatcher
	 */
	@RabbitListener(queues = "revature.caliber.service.note")
//	@RabbitListener(queues = "revature.caliber.service.test.list")
	public Note receiveNoteRequest(String message) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(message);
		JsonObject request = element.getAsJsonObject();
		System.out.println(message);
		return noteRepositoryRequestDispatcher.processNoteRequest(request);
	}
}
