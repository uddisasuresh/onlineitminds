package com.mailsend;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mailer {

	public static void send(String to, String subject, String msg, String name, int no, String companyName) {

		final String user = "onlineitminds@gmail.com";// change accordingly
		final String pass = "Cherry@123";
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, pass);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(user));
			message.setSubject(subject);
			message.setText("name : " + name + "   phone  no :  " + no + "    company name :  " + companyName
					+ "   msg  :" + msg);

			Transport.send(message);
			System.out.println("Done");

			MimeMessage message1 = new MimeMessage(session);
			message1.setFrom(new InternetAddress(user));
			message1.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message1.setSubject("onlineitminds");
			message1.setText(
					"Thank you for showing interest on us,We will contact you Soon. \r\n\r\n\r\n\r\n\r\n Regards\r\n-OnlineItMinds");

			Transport.send(message1);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void sendMailAttachment(String to, String experience, String msg, String name, String no,
			String skills, String filePath) {

		final String user = "onlineitminds@gmail.com";// change accordingly
		final String pass = "Cherry@123";
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, pass);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(user));
			message.setSubject(msg);

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(
					"name : " + name + "   phone  no :  " + no + "    company name :  " + skills + "   msg  :" + msg);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = filePath;
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Done");

		} catch (Exception e) {
			System.err.println(e);
		}
	}
}