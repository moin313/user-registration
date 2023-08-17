package com.registration.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.registration.advice.UtilException;
import com.registration.config.SendEmail;
import com.registration.entity.User;
import com.registration.service.UserService;
import com.registration.util.ResponseMsg;

import jakarta.mail.MessagingException;

@Service
public class PasswordUpdateService {

	private static final Logger logger = LoggerFactory.getLogger(PasswordUpdateService.class);

	@Autowired
	UserService userService;

	@Autowired
	SendEmail emailService;

	@Value("${sender.email}")
	private String from;

	@Scheduled(fixedRate = 86400000)
	public void sendPasswordUpdateEmails() throws MessagingException {
		List<User> users = userService.getUsersWithPwdUpdatedOn(LocalDate.now().minusDays(15));
		for (User user : users) {
			String email = user.getEmail();
			String subject = "Password Update Required";
			String text = "Dear " + user.getFirstName()
					+ ",\n\nYour password was last updated more than 15 days ago. For security reasons, please update your password as soon as possible.\n\nThank you,\nThe Management"
					+ "\n\nPlease wisit this url for updation :http://localhost:8080/api/admin/update-user";
			try {
				logger.info("Sending email to : " + email);
				SendEmail.sendPasswordUpdateEmail(text, from, email, subject);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Exception : {} ", e.getMessage());
				e.printStackTrace();
				throw new UtilException(ResponseMsg.INTERNAL_ERROR);
			}
		}
	}
}