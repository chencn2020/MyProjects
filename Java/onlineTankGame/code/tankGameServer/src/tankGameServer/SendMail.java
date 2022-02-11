/*
 * Name : SendMail.java
 *
 * Function : To send the mail
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/23
 */

package tankGameServer;

import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import com.sun.mail.util.MailSSLSocketFactory;

/**
 * This class is used to send the mail
 * 
 * @author Zevin
 *
 */
public class SendMail {
	/** Send the mail */
	public static void sendEmail(String content, String type) throws Exception {
		// Set the fundamental properties
		Properties properties = new Properties();

		// The default sender is qq mail. And set the right protocol
		properties.setProperty("mail.host", "smtp.qq.com");
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");

		// There is a specific SSL encryption in qq mail
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", sf);

		// Set a session object
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("838773760@qq.com", "lywvbtfwzwmxbcbg");
			}
		});

		// Begin debug
		session.setDebug(true);

		// Gain the connect object
		Transport transport = session.getTransport();

		// Connect the server
		transport.connect("smtp.qq.com", "838773760@qq.com", "lywvbtfwzwmxbcbg");

		if (type.equals("User")) {
			// Create a mail object
			MimeMessage mimeMessage = new MimeMessage(session);

			// Set the sender mail
			mimeMessage.setFrom(new InternetAddress("838773760@qq.com"));

			// Set the receiver mail
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("1261646386@qq.com"));

			// Set the mail title
			mimeMessage.setSubject("Some suggestions");

			// Set the content
			mimeMessage.setContent(content, "text/html;charset=UTF-8");

			// Sending
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			transport.close();
		} else if (type.equals("Server")) {

			// Send the PDF file to the player
			MimeMessage message = new MimeMessage(session);

			// Set the sender
			message.setFrom(new InternetAddress("838773760@qq.com"));

			// Set the receiver
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(content));

			// Set the title
			message.setSubject("Your Certificate");

			// Create and set the message
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("这是你的证书，请注意查收。");

			// Create the muliti-part
			Multipart multipart = new MimeMultipart();

			// Set the content part
			multipart.addBodyPart(messageBodyPart);

			// Set the attach
			messageBodyPart = new MimeBodyPart();

			// Set the file path
			DataSource source = new FileDataSource("certificate.pdf");
			messageBodyPart.setDataHandler(new DataHandler(source));

			// Handle the garbled code problem of attachment name in Chinese (with file path)
			messageBodyPart.setFileName(MimeUtility.encodeText("certificate.pdf"));
			multipart.addBodyPart(messageBodyPart);

			// Set the content sent
			message.setContent(multipart);

			// Sending
			Transport.send(message);
		}

	}

}
