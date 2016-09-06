package com.techvisio.eserve.communication.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvisio.eserve.beans.ClientComConfig;
import com.techvisio.eserve.beans.EmailMessage;
import com.techvisio.eserve.manager.impl.ClientConfiguration;
import com.techvisio.eserve.util.ClientComConfigConstants;
@Service
public class CustomMailSender extends AbstractEmailSender {

	@Autowired
	ClientConfiguration configPreferences;

	@Override
	public boolean sendEmail(EmailMessage msg) {

		ClientComConfig mailtransportProtocol = configPreferences.getClientComConfigObject(ClientComConfigConstants.MAIL_TRANSPORT_PROTOCOL);
		ClientComConfig mailHost = configPreferences.getClientComConfigObject(ClientComConfigConstants.MAIL_HOST);
		ClientComConfig mailSmtpAuth = configPreferences.getClientComConfigObject(ClientComConfigConstants.MAIL_SMTP_AUTH);
		ClientComConfig mailuser = configPreferences.getClientComConfigObject(ClientComConfigConstants.MAIL_USER);
		ClientComConfig mailPassword = configPreferences.getClientComConfigObject(ClientComConfigConstants.MAIL_PASSWORD);

		try {
			Properties props = System.getProperties();
			props.setProperty(ClientComConfigConstants.MAIL_TRANSPORT_PROTOCOL, mailtransportProtocol.getValue());
			props.setProperty(ClientComConfigConstants.MAIL_HOST, mailHost.getValue());


			props.put(ClientComConfigConstants.MAIL_SMTP_AUTH, mailSmtpAuth.getValue());
			props.setProperty(ClientComConfigConstants.MAIL_USER, mailuser.getValue());
			props.setProperty(ClientComConfigConstants.MAIL_PASSWORD, mailPassword.getValue());

			Session mailSession = Session.getDefaultInstance(props, null);
			// mailSession.setDebug(true);
			Transport transport = mailSession.getTransport("smtp");
			MimeMessage message = new MimeMessage(mailSession);
			message.setSentDate(new java.util.Date());
			message.setSubject(msg.getSubject());
			message.setFrom(new InternetAddress(msg.getFrom()));
			for (String recipient:msg.getRecipients())
			{

				message.addRecipient(Message.RecipientType.TO, new  
						InternetAddress(recipient));
			}

			// message.setText(text);
			message.setContent(msg.getText(), "text/html; charset=utf-8");
			transport.connect(mailHost.getValue(),mailuser.getValue(),mailPassword.getValue());
			transport.sendMessage(message,
					message.getRecipients(Message.RecipientType.TO));
			transport.close();
		} catch (Exception e) {
		throw new RuntimeException("Error while sending email", e);
		}
		return true;
	}
}