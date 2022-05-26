package com.promotions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WhatsAppMsgDto {

	private String Body;// message Text
	private String From;// Customer Phone number

	private String ApiVersion;
	private String SmsSid;
	private String SmsStatus;
	private String SmsMessageSid;
	private String ProfileName;
	private String NumSegments;
	private String WaId;
	private String MessageSid;
	private String AccountSid;
	private String ReferralNumMedia;
	private String To;
	private String NumMedia;

}
