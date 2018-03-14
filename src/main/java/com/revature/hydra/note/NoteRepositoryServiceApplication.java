package com.revature.hydra.note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.revature.beans")
public class NoteRepositoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteRepositoryServiceApplication.class, args);
	}
}
