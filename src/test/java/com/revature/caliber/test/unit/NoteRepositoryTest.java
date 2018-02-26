package com.revature.caliber.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.caliber.model.SimpleNote;
import com.revature.caliber.model.NoteType;
import com.revature.caliber.repository.NoteRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NoteRepositoryTest {
	private static final int TEST_TRAINEE_ID = 5529;
	private static final int TEST_BATCH_ID = 2100;
	private static final int TEST_QC_TRAINEE_ID = 5532;
	private static final int TEST_QC_BATCH_ID = 2201;
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Test
	public void findOneTest() {
		SimpleNote note = noteRepository.findOne(5061);
		short week = 2;
		
		System.out.println(note);
		
		assertNotNull(note);
		assertEquals("Associate cannot keep up with the pace of coding his project and learning in class. No confidence during the interview and did not answer most SQL questions correctly. Associate will be dropped.", note.getContent());
		assertEquals(NoteType.TRAINEE, note.getType());
		assertEquals(week, note.getWeek().shortValue());
	}
	
	@Test
	public void findAllTest() {
		List<SimpleNote> notes = noteRepository.findAll();
		
		System.out.println(notes.size());
		
		assertFalse(notes.isEmpty());
	}
	
	/**
	 * Test method. Tests the findByBatchIdAndWeekAndQcFeedbackAndType method for findBatchNotes
	 */
	@Test
	public void findBatchNotesTest() {
		short week = 2;
		List<SimpleNote> notes = noteRepository.findAllByBatchIdAndWeekAndQcFeedbackAndType(TEST_BATCH_ID, week, false, NoteType.BATCH);
		SimpleNote testNote = notes.get(0);
		
		System.out.println("Find batch notes:\n" + notes);
		
		assertFalse(notes.isEmpty());
		assertEquals(TEST_BATCH_ID, testNote.getBatchId().intValue());
		assertEquals(week, testNote.getWeek().shortValue());
		assertEquals(false, testNote.isQcFeedback());
		assertEquals(NoteType.BATCH, testNote.getType());
	}
	
	/**
	 * Test method. Tests the findByTraineeIdAndType method for findAllPublicIndividualNotes 
	 */
	@Test
	public void findAllPublicIndividualNotesTest() {
		short week = 1;
		List<SimpleNote> notes = noteRepository.findAllByTraineeIdAndType(TEST_TRAINEE_ID, NoteType.TRAINEE);
		SimpleNote testNote = notes.get(0);
		
		System.out.println("Find all public individual notes test:\n" + notes);
		
		assertFalse(notes.isEmpty());
		assertNotNull(testNote);
		assertEquals(week, testNote.getWeek().shortValue());
		assertEquals("Technically okay. Very nervous in interview. Conducts self professionally", testNote.getContent());
		assertEquals(TEST_TRAINEE_ID, testNote.getTraineeId().intValue());
		assertEquals(2201, testNote.getBatchId().intValue());
		assertEquals(NoteType.TRAINEE, testNote.getType());
	}
	
	/**
	 * Test method. Tests the findByTraineeIdAndWeekAndQcFeedbackAndType method for findTraineeNote 
	 */
	@Test
	public void findTraineeNoteTest() {
		short week = 2;
		SimpleNote testNote = noteRepository.findOneByTraineeIdAndWeekAndQcFeedbackAndType(TEST_TRAINEE_ID, week, false, NoteType.TRAINEE);
		
		System.out.println("Find trainee note:\n" + testNote);
		
		assertNotNull(testNote);
		assertEquals(week, testNote.getWeek().shortValue());
		assertEquals("Good communication. Not as confident and less use of technical terms", testNote.getContent());
		assertEquals(TEST_TRAINEE_ID, testNote.getTraineeId().intValue());
		assertEquals(false, testNote.isQcFeedback());
		assertEquals(NoteType.TRAINEE, testNote.getType());
	}
	
	/**
	 * Test method. Tests the findByBatchIdAndType method for findAllBatchQCNotes  
	 */
	@Test
	public void findAllBatchQCNotesTest() {
		List<SimpleNote> notes = noteRepository.findAllByBatchIdAndType(TEST_QC_BATCH_ID, NoteType.QC_BATCH);
		SimpleNote testNote = notes.get(0);
		
		System.out.println("Find all batch QC notes:\n" + testNote);
		
		assertFalse(notes.isEmpty());
		assertEquals(TEST_QC_BATCH_ID, testNote.getBatchId().intValue());
		assertEquals(NoteType.QC_BATCH, testNote.getType());
	}
	
	/**
	 * Test method. Tests the findByBatchIdAndWeekAndType method for findAllBatchNotes
	 */
	@Test
	public void findAllBatchNotesTest() {
		short week = 2;
		List<SimpleNote> notes = noteRepository.findAllByBatchIdAndWeekAndType(TEST_BATCH_ID, week, NoteType.BATCH);
		SimpleNote testNote = notes.get(0);
		
		System.out.println("Find all batch notes:\n" + notes);
		
		assertFalse(notes.isEmpty());
		assertEquals(NoteType.BATCH, testNote.getType());
		assertEquals(week, testNote.getWeek().shortValue());
		assertEquals(TEST_BATCH_ID, testNote.getBatchId().intValue());
	}
	
	/**
	 * Test method. Tests findByBatchIdAndWeekAndQcFeedbackAndType for findIndividualNotes
	 */
	public void findIndividualNotes() {
		short week = 2;
		List<SimpleNote> notes = noteRepository.findAllByBatchIdAndWeekAndQcFeedbackAndType(TEST_BATCH_ID, week, false, NoteType.TRAINEE);
		
		System.out.println("Find individual notes:\n" + notes);
		
		assertFalse(notes.isEmpty());
	}

}
