package com.revature.caliber.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.revature.caliber.model.SimpleBatch;
import com.revature.caliber.model.SimpleTrainee;

@Service
public class NoteCompositionMessagingService {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	private static final String SINGLE_BATCH_ROUTING_KEY = "XLNbCWqQzFHr9JfZ";
	private static final String SINGLE_TRAINEE_ROUTING_KEY = "JyoH3uRmktGn9MnW";
	private static final String RABBIT_REPO_EXCHANGE = "revature.caliber.repos";
	
	/**
	 * Sends a request for a SimpleBatch to the Batch service identified by
	 * a batchId
	 * @param batchId The batchId that identifies the SimpleBatch
	 * @return The SimpleBatch returned by the Batch service
	 */
	public SimpleBatch sendSingleSimpleBatchRequest(Integer batchId) {
		JsonObject batchRequest = new JsonObject();
		
		batchRequest.addProperty("methodName", "findOne");
		batchRequest.addProperty("batchId", batchId);
		
		return (SimpleBatch) rabbitTemplate.convertSendAndReceive(RABBIT_REPO_EXCHANGE, SINGLE_BATCH_ROUTING_KEY, batchRequest.toString());
	}
	
	/**
	 * Sends a request for a SimpleTrainee to the Trainee service identified by
	 * a traineeId
	 * @param traineeId The traineeId that identifies the SimpleTrainee
	 * @return The SimpleTrainee returned by the Trainee service
	 */
	public SimpleTrainee sendSingleSimpleTraineeRequest(Integer traineeId) {
		JsonObject traineeRequest = new JsonObject();
		
		traineeRequest.addProperty("methodName", "findOne");
		traineeRequest.addProperty("traineeId", traineeId);
		
		return (SimpleTrainee) rabbitTemplate.convertSendAndReceive(RABBIT_REPO_EXCHANGE, SINGLE_TRAINEE_ROUTING_KEY, traineeRequest.toString());
	}
	
	/**
	 * Sends a save request for a SimpleBatch to the Batch service.
	 * The SimpleBatch is sent as a JsonObject.
	 * @param batch The batch to be saved
	 * @return The SimpleBatch that was saved
	 */
	public SimpleBatch sendSaveSimpleBatchRequest(SimpleBatch batch) {
		JsonObject batchRequest = new JsonObject();
		Gson gson = new GsonBuilder().setDateFormat("MM-dd-YYYY").create();
		batchRequest.addProperty("methodName", "save");
		batchRequest.addProperty("batch", gson.toJson(batch, SimpleBatch.class));
		
		return (SimpleBatch) rabbitTemplate.convertSendAndReceive(RABBIT_REPO_EXCHANGE, SINGLE_BATCH_ROUTING_KEY, batchRequest.toString());
	}
	
	/**
	 * Sends a save request for a SimpleTrainee to the Trainee service.
	 * The SimpleTrainee is sent as a JsonObject.
	 * @param trainee The trainee to be saved
	 * @return The SimpleTrainee that was saved
	 */
	public SimpleTrainee sendSaveSimpleTraineeRequest(SimpleTrainee trainee) {
		JsonObject traineeRequest = new JsonObject();
		Gson gson = new Gson();
		traineeRequest.addProperty("methodName", "save");
		traineeRequest.addProperty("trainee", gson.toJson(trainee, SimpleTrainee.class));
		
		return (SimpleTrainee) rabbitTemplate.convertSendAndReceive(RABBIT_REPO_EXCHANGE, SINGLE_TRAINEE_ROUTING_KEY, traineeRequest.toString());
	}
}
