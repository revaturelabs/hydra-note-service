package com.revature.caliber.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.revature.caliber.model.Note;
import com.revature.caliber.model.SimpleNote;
import com.revature.caliber.repository.NoteRepository;
import com.revature.caliber.service.NoteCompositionService;

@Configuration
public class NoteCompositionServiceConfiguration {
	@Autowired
	NoteCompositionService ncs;
	
	@Autowired
	NoteRepository nr;
	
	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory factory) {
		return new RabbitTemplate(factory);
	}
	
	@Bean
	public CommandLineRunner runner() {
		return args -> {
//			Note n = ncs.findTraineeNote(5529, (short) 2);
//			//Batch ID = 2201
//			n.getBatch().setGradedWeeks(20);
//			n.getTrainee().setName("Kei Peralta");
//			System.out.println(n);
//			System.out.println(n.getTrainee().getBatch());
//			ncs.save(n);

//			SimpleNote n2 = new SimpleNote(n);
//			n2.setContent("test");
//			SimpleNote n3 = nr.save(n2);
//			System.out.println(n2);
//			System.out.println(n3);
		};
	}

}
