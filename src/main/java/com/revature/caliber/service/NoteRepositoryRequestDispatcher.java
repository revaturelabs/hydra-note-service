package com.revature.caliber.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.revature.caliber.model.Note;
import com.revature.caliber.model.SimpleNote;
import com.revature.caliber.repository.NoteRepository;

@Service
public class NoteRepositoryRequestDispatcher {
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private NoteCompositionService noteCompositionService;
	
	/**
	 * Handle a messaging request for a simple note.
	 * @param request The JsonObject that defines the parameters for the simple note to be returned
	 * @return A simple note according to the parameters in the request
	 */
	public SimpleNote processSingleSimpleNoteRequest(JsonObject request) {
		SimpleNote result = null;
		String methodName = request.get("methodName").getAsString();
		
		if(methodName.equals("findOne")) {
			result = noteRepository.findOne(request.get("noteId").getAsInt());
		} else if(methodName.equals("delete")) {
			noteRepository.deleteByTraineeId(request.get("traineeId").getAsInt());
		}
		
		return result;
	}
	
	/**
	 * Handle a messaging request for a list of simple notes.
	 * @param request The JsonObject that defines the parameters for the list of simple notes to be returned
	 * @return A list of simple notes according to the parameters in the request
	 */
	public List<SimpleNote> processListSimpleNoteRequest(JsonObject request) {
		List<SimpleNote> result = null;
		String methodName = request.get("methodName").getAsString();
		
		if(methodName.equals("findAll")) {
			result = noteRepository.findAll();
		} else if(methodName.equals("findAllByTraineeId")) {
			result = noteRepository.findAllByTraineeId(request.get("traineeId").getAsInt());
		} else if(methodName.equals("findAllByBatchId")) {
			result = noteRepository.findAllByBatchId(request.get("batchId").getAsInt());
		}
		
		return result;
	}
	
	/**
	 * Handle a messaging request for a note.
	 * @param request The JsonObject that defines the parameters for the note to be returned
	 * @return A note according to the parameters in the request
	 */
	public Note processNoteRequest(JsonObject request) {
		Note result = null;
		String methodName = request.get("methodName").getAsString();
		
		if(methodName.equals("findQCBatchNotes")) {
			result = noteCompositionService.findQCBatchNotes(request.get("batchId").getAsInt(),	request.get("week").getAsShort());
		}
		
		return result;
	}
	
	/**
	 * Handle a messaging request for a list of notes.
	 * @param request The JsonObject that defines the parameters for the list of notes to be returned
	 * @return A list of notes according to the parameters in the request
	 */
	public List<Note> processListNoteRequest(JsonObject request) {
		List<Note> result = null;
		String methodName = request.get("methodName").getAsString();
		
		if(methodName.equals("findAllQCTraineeNotes")) {
			result = noteCompositionService.findAllQCTraineeNotes(request.get("batchId").getAsInt(), request.get("week").getAsShort());
		}
		return result;
	}
}
