package io.aetherit.ats.ws.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MailTemplate {
	static final String charset = "utf-8";
	
	/**
	 * verify code mail
	 * @param email
	 * @param authKey
	 * @return
	 */
	public String authMailTemplate(String email, String randomNumber) {
		
		LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expireTime = currentTime.plusDays(1);
        
		return new StringBuffer()
				.append("Hello, \"+ email +\" \\n")
				.append("----------------------------------------------------------------------------------------------------------------------- \\n")
				.append("\\n Your Email Verification Code \\n "+ randomNumber  + "\\n\" + \"The verification code is valid for a few minutes. Please verify it in time. \\n")
				.append("code expire time : "+ expireTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.toString();
	}
	
}
