package com.promotions.services;

import java.util.List;

import com.promotions.entities.Message;

public interface PromotionsService {

	Message createMessage(String msgText);

	Message readMessage(Integer msgId);

	Message updateMessage(Message msg);

	Message deleteMessage(Integer msgId);

	List<Message> getAllMessages();

}
