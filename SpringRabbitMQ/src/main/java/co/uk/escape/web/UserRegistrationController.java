package co.uk.escape.web;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.uk.escape.domain.RegistrationRequest;

import org.springframework.http.MediaType;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRegistrationController {

	@Autowired
	RabbitTemplate rabbitTemplate;
	
//	@Autowired
//	Queue routingKey;
	
//	@Autowired
//	DirectExchange exchangeName;
	
	@RequestMapping(method = RequestMethod.POST)
	public void registerUser(@RequestBody RegistrationRequest newUserRegistrationRequest){
		System.out.println("in the controller");
		
		Object obj = rabbitTemplate.convertSendAndReceive(newUserRegistrationRequest);		
		
		System.out.println("obj returned: " + obj);		
		
	}


	@ExceptionHandler(DuplicateKeyException.class)
	ResponseEntity<String> handleNotFounds(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}
}
