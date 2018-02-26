package com.revature.hydra.note.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum InterviewFormat implements Serializable{

	@JsonProperty("Skype")
	Skype,
	@JsonProperty("Phone")
	Phone
	
}
