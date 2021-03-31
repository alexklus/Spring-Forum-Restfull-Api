package com.omega.backend.forum.model.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailService {
	/*
	 * JUST IMITATION OF SENDING EMAIL 
	 * FOR REAL EMAILING LOOK FOR JAVAMAILAPI
	 * 
	 */
	public void sendEmail(String adress, String message) {
		log.info(">>>>NEW PASSWORD " + message);
	}
	
}
