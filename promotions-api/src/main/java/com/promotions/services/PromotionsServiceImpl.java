package com.promotions.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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

//	@Value("${twilio.my.whatsapp.number}")
//	private String twilioAccountWhatsAppNumber;

	@Value("${twilio.account.whatsapp.number}")
	private String twilioAccountWhatsAppNumber;

	@Autowired
	MessageRepository messageRepository;


	private int lastMessageTracker = 0;

	private String lastOrderSelected;

	private String lastOfferSelected;

	Map<Integer,String> flowMap= new HashMap<Integer,String>(){{
		put(1,"Offers");
		put(2,"Order");
	}};

	Map<Integer,String> promotionMap = new HashMap<Integer,String>(){{
		put(1,"Buy one get one on carryout");
		put(2,"Buy one and get 20% off on second pizza");
		put(3,"Flat 30% off on payment by Paypal");
	}};


	Map<Integer,String> pizzaSizeMap = new HashMap<Integer,String>(){{
		put(1,"10\"");
		put(2,"14\"");
		put(3,"16\"");
	}};


	Map<Integer,String> pizzaToppingMap = new HashMap<Integer,String>(){{
		put(1,"Tomato");
		put(2,"Onion");
		put(3,"Capcicum");
		put(4,"Mushroom");
		put(5,"cheese");
	}};


	Map<Integer,String> pizzaTypesMap = new HashMap<Integer,String>(){{
		put(1,"Chicken Dominator");
		put(2,"Indi Tandoori Paneer & Peppy Paneer ");
		put(3,"The 4 Cheese Pizza");
		put(4,"Veg Extravaganza");
		put(5,"Achari Do Pyaza");
		put(6,"The Unthinkable Pizza");

	}};

	Map<Integer,String> pizzaBreverageMap = new HashMap<Integer,String>(){{
		put(1,"2ltr Pepsi");
		put(2,"2ltr Dew");
		put(3,"2ltr Sprite");
	}};

	Map<Integer,String> pizzaExtraSideMap = new HashMap<Integer,String>(){{
		put(1,"Cheesestik");
		put(2,"Garlic Bread");
		put(3,"Chicken Lolipop");
	}};


	Map<Integer,String> pizzaExtraTopUpMap = new HashMap<Integer,String>(){{
		put(1,"Extra topping");
		put(2,"Extra Cheese");
		put(3,"Cheese Burst");
	}};




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
		String cusWhatsAppNumber = "whatsapp:"+whatsAppMsgDto.getTo();
		Twilio.init(twilioAccountSid, twilioAccountAuthToken);
		logger.info("\n\n\nFrom:{}\nTo:{}\n\n",twilioAccountWhatsAppNumber,cusWhatsAppNumber);
		com.twilio.rest.api.v2010.account.Message twilioMessage = com.twilio.rest.api.v2010.account.Message
				.creator(new com.twilio.type.PhoneNumber(cusWhatsAppNumber),
						new com.twilio.type.PhoneNumber(twilioAccountWhatsAppNumber), messageText)
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
		processWebhookReply(whatsAppMsgDto);
	}

//	@Override
//	public void processWhatsAppWebhook(WhatsAppMsgDto whatsAppMsgDto) {
//		//Sailendu: used for my local to test from postman
//		processWebhookReply(whatsAppMsgDto);
//	}


	private void processWebhookReply(WhatsAppMsgDto whatsAppMsgDto) {
		/*
		* 1. Show Offers (if customer ask for offer or saying Hi for first time)
		* 2. Ask to confirm place order
		* 3. Show Order menu
		* 4. Ask for quantity
		* 5. Send confirmation message
		* */
		if(whatsAppMsgDto != null && StringUtils.isNotBlank(whatsAppMsgDto.getBody())) {
			//checking for outbound flow reply - can be removed later
			if(lastMessageTracker == 0 && whatsAppMsgDto.getBody().equalsIgnoreCase("1")) {
				lastMessageTracker = 1;
			}
			
			String customerMessage = whatsAppMsgDto.getBody();
			String replyMessage = "";
			if(lastMessageTracker == 0 ||
					(lastMessageTracker == 0 &&customerMessage.toUpperCase().contains("OFFER") || customerMessage.toUpperCase().contains("PROMOTION"))) {
				replyMessage = "Hi "+whatsAppMsgDto.getProfileName() + "\n";
				replyMessage += buildPromotionMessageBody();
				lastMessageTracker = 1;
			}

			//case: customer reply for offers(number or invalid character)
			if(lastMessageTracker == 1 && StringUtils.isBlank(replyMessage)) {
				if (NumberUtils.isParsable(customerMessage)) {
					int replyPromtionKey = Integer.parseInt(customerMessage);
					if (promotionMap.containsKey(replyPromtionKey)) {
						String promoName = promotionMap.get(replyPromtionKey);
						replyMessage = buildAskToOrderMessageBody();
						lastMessageTracker = 2;
						lastOfferSelected = promoName;
					} else {
						// case: customer replied invalid number
						replyMessage = "Please reply a number given from above list only. ";
					}
				} else {
					//for any non numeric invalid reply for Promotion key
					replyMessage = "Please reply a number given from above list only.";
				}
			}

			//case: customer replied to place order. Must be boolean
			if(lastMessageTracker == 2 && StringUtils.isBlank(replyMessage)) {
				if(BooleanUtils.toBooleanObject(customerMessage) == null) {
					replyMessage = "Please reply with Y or N";
				}
				if(BooleanUtils.toBooleanObject(customerMessage) != null) {
					if(BooleanUtils.toBooleanObject(customerMessage) == Boolean.TRUE) {
						replyMessage = buildShowOrderMenuMessageBody();
						lastMessageTracker = 3;
					} else {
						replyMessage = "Thank you! You can place order any time just by saying Hi to us.";
						lastMessageTracker = 0;
						lastOfferSelected = "";
						lastOrderSelected = "";
					}
				}

			}

			//case: customer choose an order from menu. Must be numeric
			if(lastMessageTracker == 3 && StringUtils.isBlank(replyMessage)) {
				if(NumberUtils.isParsable(customerMessage)) {
					int replyOrderKey = Integer.parseInt(customerMessage);
					if(pizzaTypesMap.containsKey(replyOrderKey)) {
						String menuName = pizzaTypesMap.get(replyOrderKey);
						replyMessage = "You have seleted "+ menuName + ". Please select the quantity.";//buildConfirmAndQuantityOrderMessageBody
						lastMessageTracker = 4;
						lastOrderSelected = menuName;
					} else {
						// case: customer replied invalid number
						replyMessage = "Please reply a number given from above list only. ";
					}
				} else {
					replyMessage = "Please reply a number given from above list only. ";
				}
			}

			//case: customer replied with quantity. Must be numeric
			if(lastMessageTracker == 4 && StringUtils.isBlank(replyMessage)) {
				if(NumberUtils.isParsable(customerMessage)) {
					int quantity = Integer.parseInt(customerMessage);
					replyMessage = buildFinalConfirmationMessageBody(quantity);
					lastMessageTracker = 0;
				} else {
					replyMessage = "Please reply a number given from above list only. ";
				}
			}

			if(lastMessageTracker == 0 && StringUtils.isBlank(replyMessage)
					&& (customerMessage.toUpperCase().contains("ORDER") || customerMessage.toUpperCase().contains("MENU"))) {
				replyMessage = "Hi "+whatsAppMsgDto.getProfileName() + "\n";
				replyMessage = buildShowOrderMenuMessageBody();
				lastMessageTracker = 3;

			}
			if(replyMessage.isEmpty() && lastMessageTracker == 0) {
				replyMessage = "Hi "+whatsAppMsgDto.getProfileName() + "\n";
				replyMessage += buildPromotionMessageBody();
				lastMessageTracker = 1;
			}
			sendWhatsAppMsgToCustomer(replyMessage, whatsAppMsgDto.getFrom());
		}
	}

	private String buildFinalConfirmationMessageBody(int qunatity) {
		StringBuilder sbMessage = new StringBuilder();
		sbMessage.append("Thank you for choosing Papajohns Pizza. Below is your order detail.\n");
		sbMessage.append(lastOrderSelected).append("\n");
		sbMessage.append("Quantity: ").append(qunatity).append("\n");
		if(StringUtils.isNotBlank(lastOfferSelected)) {
			sbMessage.append("Promo applied: ").append(lastOfferSelected).append("\n");
		}
		sbMessage.append("---------------------------").append("\n");
		sbMessage.append("Total amount to pay:").append(" $").append("30");
		return sbMessage.toString();
	}

	private String buildShowOrderMenuMessageBody() {
		StringBuilder sbMessage = new StringBuilder();
		pizzaTypesMap.entrySet().stream().forEachOrdered(integerStringEntry -> {
			sbMessage.append(integerStringEntry.getKey())
					.append(". ")
					.append(integerStringEntry.getValue())
					.append("\n");
		});
		sbMessage.append("Please select a number from above menu list");
		return sbMessage.toString();
	}

	private String buildAskToOrderMessageBody() {
		String message = "Thank you! Do you want to order now?(Y/N)";
		return message;
	}

	private String buildPromotionMessageBody() {
		StringBuilder sbMessage = new StringBuilder();
		promotionMap.entrySet().stream().forEachOrdered(integerStringEntry -> {
			sbMessage.append(integerStringEntry.getKey())
			.append(". ")
			.append(integerStringEntry.getValue())
			.append("\n");
		});
		sbMessage.append("Please select a number");
		return sbMessage.toString();
	}

	private int parseMessageToNumber(String message) {
		int reply = 0;
		if(message == null) {
			return reply;
		}
		try {
			reply = Integer.parseInt(message);
		} catch(NumberFormatException nfe) {
			return 0;
		}
		return reply;
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

	public void sendWhatsAppMsgToCustomer(String messageText, String cusWhatsAppNumber) {
		Twilio.init(twilioAccountSid, twilioAccountAuthToken);
		com.twilio.rest.api.v2010.account.Message twilioMessage = com.twilio.rest.api.v2010.account.Message
				.creator(new com.twilio.type.PhoneNumber(cusWhatsAppNumber),
						new com.twilio.type.PhoneNumber(twilioAccountWhatsAppNumber), messageText)
				.create();
		logger.info("Twilio SID={}", twilioMessage.getSid());
	}

}
