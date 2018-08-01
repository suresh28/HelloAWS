package com.aws.sample.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class HelloAWSController {
	
	
	private static final Logger log = LogManager.getLogger(HelloAWSController.class.getSimpleName());

	@RequestMapping(value="/connect" , method=RequestMethod.GET)
	public String connectToS3()
	{
		log.info("Greetings from  AWS Controller");
		
		return "Greetings from AWS Controller";
	}


	@RequestMapping(value="/" , method=RequestMethod.GET)
	public String home()
	{
		log.info("Greetings from  Home Controller");
		
		return "Hello World!!!";
	}
}
