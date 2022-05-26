package com.promotions.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promotions.entities.Message;
import com.promotions.services.PromotionsService;

@RestController
@RequestMapping("/promotions")
public class PromotionsApiController {

	private static final Logger logger = LoggerFactory.getLogger(PromotionsApiController.class);

	@Autowired
	private PromotionsService promotionsService;

	@GetMapping("/test")
	public String test() {
		logger.info("PromotionsApiController test endpoint hit success!");
		return "PromotionsApiController test endpoint hit success!";
	}

	@PostMapping("/saveMessage")
	public Message saveMessage(@RequestBody Message msg) {
		Message message = null;
		if (null != msg) {
			message = promotionsService.createMessage(msg.getMessagetext());
		}
		return message;
	}

	@GetMapping("/getMessage/{id}")
	public Message getMessage(@PathVariable Integer id) {
		Message message = null;
		if (null != id) {
			message = promotionsService.readMessage(id);
		}
		return message;
	}

	@GetMapping("/getAllMessages")
	public List<Message> getAllMessages() {
		List<Message> message = promotionsService.getAllMessages();
		return message;
	}

	@PutMapping("/updateMessage")
	public Message updateMessage(@RequestBody Message msg) {
		Message message = null;
		if (null != msg) {
			message = promotionsService.updateMessage(msg);
		}
		return message;
	}

	@DeleteMapping("/deleteMessage/{id}")
	public Message deleteMessage(@PathVariable Integer id) {
		Message message = null;
		if (null != id) {
			message = promotionsService.deleteMessage(id);
		}
		return message;
	}

}
