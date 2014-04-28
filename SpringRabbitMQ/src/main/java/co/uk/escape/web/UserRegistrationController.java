package co.uk.escape.web;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.uk.escape.domain.RegisteredUser;
import co.uk.escape.domain.RegistrationRequest;

import org.springframework.http.MediaType;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRegistrationController {

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@RequestMapping(method = RequestMethod.POST)
	public RegisteredUser registerUser(@RequestBody RegistrationRequest newUserRegistrationRequest){
		System.out.println("in the controller");
		
		RegisteredUser registeredUser = (RegisteredUser)rabbitTemplate.convertSendAndReceive(newUserRegistrationRequest);		
		
		System.out.println("obj returned: " + registeredUser);		
		return registeredUser;
	}


	@ExceptionHandler(DuplicateKeyException.class)
	ResponseEntity<String> handleNotFounds(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}
}
