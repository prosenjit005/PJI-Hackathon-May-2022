package com.promotions.services;

import java.util.List;
import java.util.Map;

import com.promotions.dto.WhatsAppMsgDto;
import com.promotions.entities.Message;

public interface PromotionsService {

	Message createMessage(String msgText);

	Message readMessage(Integer msgId);

	Message updateMessage(Message msg);

	Message deleteMessage(Integer msgId);

	List<Message> getAllMessages();

	void sendWhatsAppMsg(WhatsAppMsgDto whatsAppMsgDto);

	void processWhatsAppWebhook(Map<String, String[]> webhookResponse);

}
