package com.revature.caliber.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.caliber.model.Note;
import com.revature.caliber.service.NoteCompositionService;

@RestController
//@PreAuthorize("isAuthenticated()")
@CrossOrigin("http://localhost:8090")
public class NoteController {
	private static final Logger log = Logger.getLogger(NoteController.class);
	@Autowired
	private NoteCompositionService noteService;
	
	private static final String FINDING_WEEK = "Finding week ";
	
	/**
	 * Create note
	 *
	 * @param note
	 * @return
	 */
	@RequestMapping(value = "/note/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER','PANEL')")
	public ResponseEntity<Note> createNote(@Valid @RequestBody Note note) {
		log.info("Creating note: " + note);
		noteService.save(note);
		return new ResponseEntity<>(note, HttpStatus.CREATED);
	}

	/**
	 * Update note
	 * 
	 * @param note
	 * @return
	 */
	@RequestMapping(value = "/note/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER','PANEL')")
	public ResponseEntity<Note> updateNote(@Valid @RequestBody Note note) {
		log.info("Updating note: " + note);
		noteService.update(note);
		return new ResponseEntity<>(note, HttpStatus.CREATED);
	}

	/*
	 *******************************************************
	 * TODO TRAINER NOTE SERVICES
	 *
	 *******************************************************
	 */
	/**
	 * FIND WEEKLY BATCH NOTES (TRAINER/PUBLIC)
	 * 
	 * @param batch
	 * @param week
	 * @return
	 */
	@RequestMapping(value = "/trainer/note/batch/{batchId}/{week}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'STAGING','PANEL')")
	public ResponseEntity<List<Note>> findBatchNotes(@PathVariable Integer batchId, @PathVariable Integer week) {
		log.info(FINDING_WEEK + week + " batch notes for batch: " + batchId);
		return new ResponseEntity<>(noteService.findBatchNotes(batchId, week.shortValue()), HttpStatus.OK);
	}

	/**
	 * FIND WEEKLY INDIVIDUAL NOTES (TRAINER/PUBLIC)
	 * 
	 * @param trainee
	 * @param week
	 * @return
	 */
	@RequestMapping(value = "/trainer/note/trainee/{batchId}/{week}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'STAGING','PANEL')")
	public ResponseEntity<List<Note>> findIndividualNotes(@PathVariable Integer batchId, @PathVariable Integer week) {
		log.info(FINDING_WEEK + week + " individual notes for trainee: " + batchId);
		return new ResponseEntity<>(noteService.findIndividualNotes(batchId, week.shortValue()), HttpStatus.OK);
	}

	/**
	 * FIND TRAINEE NOTE FOR THE WEEK
	 * 
	 * @param trainee
	 * @param week
	 * @return
	 */
	@RequestMapping(value = "/trainer/note/trainee/{traineeId}/for/{week}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'STAGING','PANEL')")
	public ResponseEntity<Note> findTraineeNote(@PathVariable Integer traineeId, @PathVariable Integer week) {
		return new ResponseEntity<>(noteService.findTraineeNote(traineeId, week.shortValue()), HttpStatus.OK);
	}

	/**
	 * FIND TRAINEE NOTE FOR THE WEEK(Michael)
	 * 
	 * @param QCtrainee
	 * @param week
	 * @return
	 */
	@RequestMapping(value = "/qc/note/trainee/{traineeId}/for/{week}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('VP', 'QC', 'STAGING','PANEL')")
	public ResponseEntity<Note> findQCTraineeNote(@PathVariable Integer traineeId, @PathVariable Integer week) {
		return new ResponseEntity<>(noteService.findQCTraineeNote(traineeId, week.shortValue()), HttpStatus.OK);
	}

	/**
	 * FIND THE WEEKLY QC BATCH NOTE FOR THE WEEK
	 * 
	 * @param batch
	 * @param week
	 * @return
	 */
	@RequestMapping(value = "/qc/note/batch/{batchId}/{week}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'STAGING','PANEL')")
	public ResponseEntity<Note> findQCBatchNotes(@PathVariable Integer batchId, @PathVariable Integer week) {
		log.info(FINDING_WEEK + week + " QC batch notes for batch: " + batchId);
		return new ResponseEntity<>(noteService.findQCBatchNotes(batchId, week.shortValue()), HttpStatus.OK);
	}

	/**
	 * Find all QC trainee notes in a batch for the week
	 * 
	 * @return
	 */
	@RequestMapping(value = "/qc/note/trainee/{batchId}/{week}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'STAGING','PANEL')")
	public ResponseEntity<List<Note>> getAllQCTraineeNotes(@PathVariable Integer batchId, @PathVariable Integer week) {
		log.info("Getting all trainee notes by QC");
		return new ResponseEntity<>(noteService.findAllQCTraineeNotes(batchId, week.shortValue()), HttpStatus.OK);
	}

	/**
	 * Find all QC trainee notes in a batch
	 * 
	 * @return
	 */
	@RequestMapping(value = "/qc/note/trainee/{traineeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'STAGING','PANEL')")
	public ResponseEntity<List<Note>> getAllQCTraineeOverallNotes(@PathVariable Integer traineeId) {
		log.info("Getting all trainee notes by QC for that trainee");
		return new ResponseEntity<>(noteService.findAllQCTraineeOverallNotes(traineeId), HttpStatus.OK);
	}

	/*
	 *******************************************************
	 * TODO VP NOTE SERVICES
	 *
	 *******************************************************
	 */

	/**
	 * FIND ALL WEEKLY BATCH NOTES 
	 * 
	 * @param batch
	 * @param week
	 * @return
	 */
	@RequestMapping(value = "/vp/note/batch/{batchId}/{week}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'STAGING','PANEL')")
	public ResponseEntity<List<Note>> findAllBatchNotes(@PathVariable Integer batchId, @PathVariable Integer week) {
		log.info(FINDING_WEEK + week + " batch notes for batch: " + batchId);
		return new ResponseEntity<>(noteService.findAllBatchNotes(batchId, week.shortValue()), HttpStatus.OK);
	}


	@RequestMapping(value = "/all/notes/trainee/{traineeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'STAGING','PANEL')")
	public ResponseEntity<List<Note>> findAllTraineeNotes(@PathVariable Integer traineeId) {
		return new ResponseEntity<>(noteService.findAllPublicIndividualNotes(traineeId), HttpStatus.OK);
	}
}
