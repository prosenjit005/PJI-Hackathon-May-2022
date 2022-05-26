package com.promotions.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.promotions.dto.WhatsAppMsgDto;
import com.promotions.entities.Message;
import com.promotions.repository.MessageRepository;
import com.twilio.Twilio;

@Service
public class PromotionsServiceImpl implements PromotionsService {

	private static final Logger logger = LoggerFactory.getLogger(PromotionsServiceImpl.class);

	@Value("${twilio.account.sid}")
	private String twilioAccountSid;

	@Value("${twilio.auth.token}")
	private String twilioAccountAuthToken;

	@Value("${twilio.my.whatsapp.number}")
	private String twilioAccountWhatsAppNumber;

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

	@Override
	public void sendWhatsAppMsg(WhatsAppMsgDto whatsAppMsgDto) {
		String messageText = whatsAppMsgDto.getBody();
		String cusWhatsAppNumber = whatsAppMsgDto.getFrom();
		Twilio.init(twilioAccountSid, twilioAccountAuthToken);
		com.twilio.rest.api.v2010.account.Message twilioMessage = com.twilio.rest.api.v2010.account.Message
				.creator(new com.twilio.type.PhoneNumber(twilioAccountWhatsAppNumber),
						new com.twilio.type.PhoneNumber(cusWhatsAppNumber), messageText)
				.create();
		logger.info("Twilio SID={}", twilioMessage.getSid());
	}

	@Override
	public void processWhatsAppWebhook(Map<String, String[]> webhookResponse) {
		logger.info("processWhatsAppWebhook starts!");
		WhatsAppMsgDto whatsAppMsgDto = mapResponseToDto(webhookResponse);
		logger.info("processed webhook response={}", whatsAppMsgDto.toString());
		logger.info("\n\nReceived Message:\n{} \n\n", whatsAppMsgDto.getBody());
		logger.info("processWhatsAppWebhook ends!");
	}

	public WhatsAppMsgDto mapResponseToDto(Map<String, String[]> webhookResponse) {
		WhatsAppMsgDto whatsAppMsgDto = new WhatsAppMsgDto();
		whatsAppMsgDto.setApiVersion(webhookResponse.get("ApiVersion")[0]);
		whatsAppMsgDto.setSmsSid(webhookResponse.get("SmsSid")[0]);
		whatsAppMsgDto.setSmsStatus(webhookResponse.get("SmsStatus")[0]);
		whatsAppMsgDto.setSmsMessageSid(webhookResponse.get("SmsMessageSid")[0]);
		whatsAppMsgDto.setProfileName(webhookResponse.get("ProfileName")[0]);
		whatsAppMsgDto.setNumSegments(webhookResponse.get("NumSegments")[0]);
		whatsAppMsgDto.setFrom(webhookResponse.get("From")[0]);
		whatsAppMsgDto.setWaId(webhookResponse.get("WaId")[0]);
		whatsAppMsgDto.setMessageSid(webhookResponse.get("MessageSid")[0]);
		whatsAppMsgDto.setReferralNumMedia(webhookResponse.get("ReferralNumMedia")[0]);
		whatsAppMsgDto.setTo(webhookResponse.get("To")[0]);
		whatsAppMsgDto.setBody(webhookResponse.get("Body")[0]);
		whatsAppMsgDto.setNumMedia(webhookResponse.get("NumMedia")[0]);
		return whatsAppMsgDto;
	}

}
