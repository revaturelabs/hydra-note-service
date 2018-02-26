package com.revature.caliber.test.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
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
import com.revature.caliber.model.Batch;
import com.revature.caliber.model.Note;
import com.revature.caliber.model.QCStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteEndpointsTest {
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mvc;
	
	private Gson gson;
	
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		gson = new Gson();
	}
	
	@Ignore
	@Test
	public void createNoteTest() throws Exception {
		Note note = Note.qcBatchNote("Test", 8, new Batch(), QCStatus.Good);
		
		mvc.perform(post("http://localhost:8081/note/note/create")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(note, Note.class).toString()))
				.andExpect(status().isCreated());
	}
}
