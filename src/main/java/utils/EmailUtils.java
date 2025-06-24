package utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;
import java.util.Properties;

public class EmailUtils {

    public static void sendEmail(String subject, String body, String toEmail, String attachmentPath) {
        final String fromEmail = ConfigManager.get("email.from");
        final String password = ConfigManager.get("email.password");

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail, false));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            msg.setSubject(subject);
            msg.setSentDate(new java.util.Date());

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (attachmentPath != null && !attachmentPath.isEmpty()) {
                MimeBodyPart attachPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachmentPath);
                attachPart.setDataHandler(new DataHandler(source));
                attachPart.setFileName(source.getName());
                multipart.addBodyPart(attachPart);
            }

            msg.setContent(multipart);
            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}