package com.techvisio.eserve.communication;

import java.util.List;

import com.techvisio.eserve.beans.EmailMessage;

public interface EmailSender {

	boolean sendEmail(EmailMessage msg);

}
