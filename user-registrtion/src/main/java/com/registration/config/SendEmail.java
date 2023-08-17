package com.registration.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.registration.advice.UtilException;
import com.registration.util.ResponseMsg;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class SendEmail {

	private static final Logger logger = LoggerFactory.getLogger(SendEmail.class);

	
	public static void sendPasswordUpdateEmail(String text, String mailFrom, String mailTo, String subject) throws Exception {
		try {
			Properties props = System.getProperties();
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.socketFactory.port", "587");
			props.setProperty("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.auth", "true");
			
			Authenticator auth = new MyAuthenticator();
			Session smtpSession = Session.getInstance(props, auth);
			smtpSession.setDebug(false);

			MimeMessage message = new MimeMessage(smtpSession);
			message.setFrom(new InternetAddress(mailFrom));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));

			final String encoding = "UTF-8";

			message.setSubject(subject, encoding);
			message.setText(text, encoding);
			Transport.send(message);
			logger.debug("Email sent to : {}", mailTo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new UtilException(ResponseMsg.INTERNAL_ERROR);
		}
	}

	static class MyAuthenticator extends Authenticator {
		@Value("${sender.email}")
		private String email;

		@Value("${sender.password}")
		private String password;
		
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(email, password);
		}
	}
}
