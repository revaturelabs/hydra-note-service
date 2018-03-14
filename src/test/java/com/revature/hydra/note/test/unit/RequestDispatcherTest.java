package com.revature.hydra.note.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.JsonObject;
import com.revature.beans.NoteType;
import com.revature.beans.SimpleNote;
import com.revature.beans.TrainerRole;
import com.revature.hydra.note.service.NoteRepositoryRequestDispatcher;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestDispatcherTest {

	@Autowired
	NoteRepositoryRequestDispatcher nrrd;
	
	@Test
	public void processSingleSimpleNoteRequestTest() {
		JsonObject json = new JsonObject();
		json.addProperty("methodName", "findOne");
		json.addProperty("noteId", 5061);
		
		SimpleNote result = nrrd.processSingleSimpleNoteRequest(json);
		SimpleNote expected = new SimpleNote(5061, 
				"Associate cannot keep up with the pace of coding his project and learning in class. No confidence during the interview and did not answer most SQL questions correctly. Associate will be dropped.", 
				(short) 2, 2050, 5356, TrainerRole.ROLE_QC, NoteType.TRAINEE, false, null);
		assertNotNull(result);
		assertEquals(true, result.equals(expected));
	}
}
