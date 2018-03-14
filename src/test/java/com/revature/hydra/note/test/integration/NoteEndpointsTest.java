package com.revature.hydra.note.test.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.revature.beans.Batch;
import com.revature.beans.Note;
import com.revature.beans.QCStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteEndpointsTest {
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mvc;
	
	private Gson gson;
	
	private static final String ADDRESS = "http://35.183.1.239";
	private static final String PORT = "8081";
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		gson = new Gson();
	}
	
	@Test
	public void createNoteTest() throws Exception {
		Note note = Note.qcBatchNote("Test", 8, new Batch(), QCStatus.Good);
		
		mvc.perform(post(ADDRESS + ":" + PORT + "/note/create")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(note, Note.class).toString()))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void updateNoteTest() throws Exception {
		Note note = Note.qcBatchNote("Test", 8, new Batch(), QCStatus.Good);
		
		mvc.perform(post(ADDRESS + ":" + PORT + "/note/create")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(note, Note.class).toString()))
				.andExpect(status().isCreated());
		
		note.setContent("Updated Test");
		
		mvc.perform(post(ADDRESS + ":" + PORT + "/note/update")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(note, Note.class).toString()))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void findBatchNotesTest() throws Exception {	
		mvc.perform(get(ADDRESS + ":" + PORT + "/note/trainer/note/batch/2100/2"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void findIndividualNotesTest() throws Exception {	
		mvc.perform(get(ADDRESS + ":" + PORT + "/note/trainer/note/trainee/2050/2"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void findTraineeNotesTest() throws Exception {	
		mvc.perform(get(ADDRESS + ":" + PORT + "/note/trainer/note/trainee/5356/for/2"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void findQCTraineeNotesTest() throws Exception {	
		mvc.perform(get(ADDRESS + ":" + PORT + "/note/qc/note/trainee/5356/for/2"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void findQCBatchNotesTest() throws Exception {	
		mvc.perform(get(ADDRESS + ":" + PORT + "/note/qc/note/batch/2100/2"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getAllQCTraineeNotesTest() throws Exception {	
		mvc.perform(get(ADDRESS + ":" + PORT + "/note/qc/note/trainee/2100/2"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getAllQCTraineeOverallNotesTest() throws Exception {	
		mvc.perform(get(ADDRESS + ":" + PORT + "/note/qc/note/trainee/5356"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void findAllBatchNotesTest() throws Exception {	
		mvc.perform(get(ADDRESS + ":" + PORT + "/note/vp/note/batch/2100/2"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void findAllTraineeNotesTest() throws Exception {	
		mvc.perform(get(ADDRESS + ":" + PORT + "/note/all/notes/trainee/5356"))
				.andExpect(status().isOk());
	}
}
