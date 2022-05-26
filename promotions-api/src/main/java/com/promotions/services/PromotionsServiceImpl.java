package com.promotions.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promotions.entities.Message;
import com.promotions.repository.MessageRepository;

@Service
public class PromotionsServiceImpl implements PromotionsService {

	private static final Logger logger = LoggerFactory.getLogger(PromotionsServiceImpl.class);

	@Autowired
	MessageRepository messageRepository;

	@Override
	public Message createMessage(String msgText) {
		logger.info("createMessage input={}", msgText);
		Message message = null;
		if (null != msgText && !msgText.isEmpty()) {
			message = new Message();
			message.setMessagetext(msgText);
			messageRepository.save(message);
		} else {
			logger.info("createMessage input is empty!");
		}
		return message;
	}

	@Override
	public Message readMessage(Integer msgId) {
		logger.info("createMessage input={}", msgId);
		Optional<Message> message = messageRepository.findById(msgId);
		return message.get();
	}

	@Override
	public Message updateMessage(Message msg) {
		logger.info("createMessage input={}", msg);
		Optional<Message> message = messageRepository.findById(msg.getId());
		Message msg1 = message.get();
		msg1.setMessagetext(msg.getMessagetext());
		messageRepository.save(msg1);
		return msg1;
	}

	@Override
	public Message deleteMessage(Integer msgId) {
		logger.info("createMessage input={}", msgId);
		Optional<Message> message = messageRepository.findById(msgId);
		messageRepository.deleteById(msgId);
		return message.get();
	}

	@Override
	public List<Message> getAllMessages() {
		return messageRepository.findAll();
	}

}
