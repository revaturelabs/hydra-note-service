package com.revature.caliber.service;

import java.util.LinkedList;
import java.util.List;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.detDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.caliber.model.Batch;
import com.revature.caliber.model.Note;
import com.revature.caliber.model.NoteType;
import com.revature.caliber.model.SimpleBatch;
import com.revature.caliber.model.SimpleNote;
import com.revature.caliber.model.SimpleTrainee;
import com.revature.caliber.model.Trainee;
import com.revature.caliber.model.TrainingStatus;
import com.revature.caliber.repository.NoteRepository;

@Service
public class NoteCompositionService {
	@Autowired
	private NoteRepository noteRepository;
	@Autowired
	private NoteCompositionMessagingService noteCompositionMessagingService;
	
	/**
	 * Saves a Note by instantiating a new SimpleNote from the Note
	 * and calling the NoteRepository save method.
	 * @param note The Note that will be saved 
	 */
	public void save(Note note) {
		SimpleNote simpleNote = new SimpleNote(note);
		
		if(note.getNoteId() == 0) simpleNote.setNoteId(null);
		
		noteRepository.save(simpleNote);
	}
	
	/**
	 * Updates a Note. Save also handles update, so just call the save method.
	 * @param note The Note that will be updated
	 */
	public void update(Note note) {
		save(note);
	}
	
	/**
	 * Deletes a Note using its ID. Calls the NoteRepository method for deletion,
	 * gives the ID as parameter
	 * @param note The Note to be deleted
	 */
	public void delete(Note note) {
		noteRepository.delete(note.getNoteId());
	}
	
	/**
	 * Returns the Note that is associated with the given noteId
	 * @param noteId The ID that identifies the Note
	 * @return The Note constructed from the SimpleNote saved in the database
	 */	
	public Note findOne(Integer noteId) {
		SimpleNote basis = noteRepository.findOne(noteId);
		Note result = composeNote(basis);
		
		return result;
	}
	
	/**
	 * Returns all Trainer-written, Batch-level Notes for a given week.
	 * @param batchId The batchId that identifies the Batch
	 * @param week The week for which the Batch's note will be retrieved
	 * @return The List of batch-level Notes written by Trainers for the given week
	 */
	public List<Note> findBatchNotes(Integer batchId, Short week) {
		List<SimpleNote> basis = noteRepository.findAllByBatchIdAndWeekAndQcFeedbackAndType(batchId, week, false, NoteType.BATCH);
		List<Note> result = composeListOfNotes(basis, false);
		
		return result;
	}
	
	
	/**
	 * Returns Trainer-written Individual Notes for a Batch for a given week
	 * @param batchId The batchId that identifies the Batch
	 * @param week The week for which the Batch's Individual Notes will be retrieved
	 * @return The List of Trainer-written Individual Notes for the Batch for the given week
	 */
	public List<Note> findIndividualNotes(Integer batchId, Short week) {
		List<SimpleNote> basis = noteRepository.findAllByBatchIdAndWeekAndQcFeedbackAndType(batchId, week, false, NoteType.TRAINEE);
		List<Note> result = composeListOfNotes(basis, false);
		
		return result;
	}
	
	/**
	 * Returns the note for a Trainee identified by traineeId for a specified week.
	 * Additionally, the NoteType should be TRAINEE and the qcFeedback should be false.
	 * 
	 * @param traineeId The traineeId that identifies the Trainee
	 * @param week The week for which the Trainee's note will be retrieved
	 * @return A note for a Trainee identified by traineeId for the given week
	 */
	public Note findTraineeNote(Integer traineeId, Short week) {
		SimpleNote basis = noteRepository.findOneByTraineeIdAndWeekAndQcFeedbackAndType(traineeId, week, false, NoteType.TRAINEE);
		Note result = composeNote(basis);
		
		return result;
	}
	
	/**
	 *  Returns the QC Note for a Trainee for a given week
	 *  @param traineeId The traineeId that identifies the Trainee
	 *  @param week The week for which a Trainee's QC Note will be retrieved
	 *  @return The QC Note for a Trainee for the given week
	 */
	public Note findQCTraineeNote(Integer traineeId, Short week) {
		SimpleNote basis = noteRepository.findOneByTraineeIdAndWeekAndQcFeedbackAndType(traineeId, week, true, NoteType.QC_TRAINEE);
		Note result = composeNote(basis);
		
		return result;
	}
	
	/**
	 * Returns the Batch Note for a Batch for a given week
	 * @param batchId The batchId that identifies the Batch
	 * @param week The week for which a Batch's Note will be retrieved
	 * @return The Batch Note for a Batch for the given week
	 */
	public Note findQCBatchNotes(Integer batchId, Short week) {
		SimpleNote basis = noteRepository.findOneByBatchIdAndWeekAndQcFeedbackAndType(batchId, week, true, NoteType.QC_BATCH);
		Note result = composeNote(basis);
		
		return result;
	}
	
	/**
	 * Returns all Batch-level QC Notes for a given Batch
	 * @param batchId The batchId that identifies the Batch
	 * @return The List of Batch-level QC Notes for the given Batch
	 */
	public List<Note> findAllBatchQCNotes(Integer batchId) {
		List<SimpleNote> basis = noteRepository.findAllByBatchIdAndType(batchId, NoteType.QC_BATCH);
		List<Note> result = composeListOfNotes(basis, false);
		
		return result;
	}
	
	/**
	 * Returns the QC Notes for a Trainee for a given week
	 * @param traineeId The traineeId that identifies the Trainee
	 * @param week The week for which a Trainee's QC Notes will be retrieved
	 * @return The List of QC Notes for a Trainee for the given week 
	 */
	public List<Note> findQCIndividualNotes(Integer traineeId, Short week) {
		List<SimpleNote> basis = noteRepository.findAllByTraineeIdAndWeekAndQcFeedbackAndType(traineeId, week, true, NoteType.QC_TRAINEE);
		List<Note> result = composeListOfNotes(basis, false);
		
		return result;
	}
	
	/**
	 * Returns all Batch-level Notes for a given week
	 * @param batchId The batchId that identifies the Batch
	 * @param week The week for which a Batch's Notes will be retrieved
	 * @return The List of Batch-level Notes for the given week
	 */
	public List<Note> findAllBatchNotes(Integer batchId, Short week) {
		List<SimpleNote> basis = noteRepository.findAllByBatchIdAndWeekAndType(batchId, week, NoteType.BATCH);
		List<Note> result = composeListOfNotes(basis, false);
		
		return result;
	}
	
	/**
	 * Returns all Individual Notes for a given week
	 * @param traineeId The traineeId that identifies the Trainee
	 * @param week The week for which an Individual's Note will be retrieved
	 * @return The List of Individual Notes for the given week
	 */
	public List<Note> findAllIndividualNotes(Integer traineeId, Short week) {
		List<SimpleNote> basis = noteRepository.findAllByTraineeIdAndWeekAndType(traineeId, week, NoteType.TRAINEE);
		List<Note> result = composeListOfNotes(basis, false);
		
		return result;
	}
	
	/**
	 * Returns a list of Notes for a Trainee identified by traineeId
	 * @param traineeId The traineeId that identifies the Trainee
	 * @return The List of Notes for the Trainee identified by traineeId
	 */
	public List<Note> findAllPublicIndividualNotes(Integer traineeId) {
		List<SimpleNote> basis = noteRepository.findAllByTraineeIdAndTypeOrderByWeekAsc(traineeId, NoteType.TRAINEE);
		List<Note> result = composeListOfNotes(basis, false);
		
		return result;
	}
	
	/**
	 * Returns all Batch-level QC Notes
	 * @param batchId The batchId that identifies the Batch
	 * @return The List of Batch-level QC Notes for the given Batch 
	 */
	public List<Note> findAllQCBatchNotes(Integer batchId) {
		List<SimpleNote> basis = noteRepository.findAllByBatchIdAndQcFeedbackAndTypeOrderByWeekAsc(batchId, true, NoteType.QC_BATCH);
		List<Note> result = composeListOfNotes(basis, false);
		
		return result;
	}
	
	/**
	 * Returns all QC Notes for all Trainees in a Batch for a given week
	 * @param batchId The batchId that identifies the Batch
	 * @param week The week for which all QC Notes for all Trainees in the Batch will be retrieved
	 * @return The List of all QC Notes for all Trainees in the Batch for the given week 
	 */
	public List<Note> findAllQCTraineeNotes(Integer batchId, Short week) {
		
		List<SimpleNote> basis = noteRepository.findAllByBatchIdAndWeekAndQcFeedbackAndTypeOrderByWeekAsc(batchId, week, true, NoteType.QC_TRAINEE);
		System.out.println(basis);
		List<Note> result = composeListOfNotes(basis, false);
		System.out.println(result);
		return result;
	}
	
	/**
	 * Returns all QC Notes for a Trainee in a Batch
	 * @param traineeId The traineeId that identifies the Trainee
	 * @return The List of all QC Notes for the given Trainee 
	 */
	public List<Note> findAllQCTraineeOverallNotes(Integer traineeId) {
		List<SimpleNote> basis = noteRepository.findAllByTraineeIdAndQcFeedbackAndTypeOrderByWeekAsc(traineeId, true, NoteType.QC_TRAINEE);
		List<Note> result = composeListOfNotes(basis, false);
		
		return result;
	}
	
	/**
	 * Composes a list of Notes from a list of SimpleNotes
	 * Filters out dropped trainees depending on the includeDropped parameter
	 * @param src The list of SimpleNotes to convert to Notes
	 * @param includeDropped Whether or not to include notes from dropped trainees
	 * @return List of Notes converted from a List of SimpleNotes
	 */
	private List<Note> composeListOfNotes(List<SimpleNote> src, boolean includeDropped) {
		List<Note> dest = new LinkedList<Note>();
		System.out.println(dest);
		for(SimpleNote curr : src) {
			Note note = composeNote(curr);
			
			if(note.getTrainee() == null || (note.getType().equals(NoteType.BATCH) || note.getType().equals(NoteType.QC_BATCH)))
				dest.add(note);
			else {
				if(!includeDropped && note.getTrainee().getTrainingStatus() != TrainingStatus.Dropped)
					dest.add(note);
				else if(includeDropped)
					dest.add(note);
			}
		}
		
		return dest;
	}
	
	/**
	 * Composes a Note from a SimpleNote by messaging the Batch and
	 * Trainee services for a SimpleBatch and SimpleTrainee according to the
	 * batchId and traineeId stored in the SimpleNote.
	 * @param src The SimpleNote to be converted
	 * @return The Note composed from information in the SimpleNote
	 */
	private Note composeNote(SimpleNote src) {
		SimpleBatch simpleBatch = null;
		SimpleTrainee simpleTrainee = null;
		Batch batch = null;
		Trainee trainee = null;
		Note dest = null;
		
		if(src == null) return null;
		dest = new Note(src);

		System.out.println("SimpleNote: " + src);
		System.out.println("Note: " + dest);
		
		if(src.getBatchId() != null) {
			simpleBatch =  noteCompositionMessagingService.sendSingleSimpleBatchRequest(src.getBatchId());
			batch = new Batch(simpleBatch);
			dest.setBatch(batch);
		}
			
		if(src.getTraineeId() != null) {
			simpleTrainee = noteCompositionMessagingService.sendSingleSimpleTraineeRequest(src.getTraineeId());
			trainee = new Trainee(simpleTrainee);
			
			if(simpleBatch == null && simpleTrainee.getBatchId() != null) {
				simpleBatch = noteCompositionMessagingService.sendSingleSimpleBatchRequest(simpleTrainee.getBatchId());
				if(simpleBatch != null) batch = new Batch(simpleBatch);
			}
			
			dest.setTrainee(trainee);
		}
		
		if(trainee != null && batch != null) trainee.setBatch(batch);
		
		return dest;
	}
}
