package com.languagelearn.service.mailer;

import com.languagelearn.util.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Component
public class Mailer {

    private static final Logger log = LoggerFactory.getLogger(Mailer.class);

    private final static String ENCODING = "UTF-8";

    @Autowired
    private MailSender mailSender;
    @Autowired
    private ApplicationProperties applicationProperties;

    public void send(String address, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject(subject);
        msg.setTo(address);
        msg.setText(body);

        try {
            this.mailSender.send(msg);
        }
        catch(Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public class CustomMessage {
        private MimeMessage message;

        Multipart multipart = new MimeMultipart();

        public CustomMessage(String address, String subject, String body) {
            Properties props = new Properties();
            props.put("mail.smtp.auth", applicationProperties.mailSmtpAuthEnabled);
            props.put("mail.smtp.starttls.enable", applicationProperties.mailSmtpTlsEnabled);
            props.put("mail.smtp.host", applicationProperties.mailSmtpHost);
            props.put("mail.smtp.port", applicationProperties.mailSmtpPort);

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(applicationProperties.mailerUserName, applicationProperties.mailerPwd);
                        }
                    });


            message = new MimeMessage(session);

            try {
                message.setFrom(new InternetAddress(((JavaMailSenderImpl) mailSender).getUsername()));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
                message.setSubject(subject);

                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(body);
                multipart.addBodyPart(messageBodyPart);

            } catch (MessagingException e) {
                log.error(e.getMessage(), e);
            }

        }

        public CustomMessage addAttachment(String folderPath, String fileName) throws MessagingException {
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(folderPath + "/" + fileName);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            return this;
        }

        public void send() {

            try {
                message.setContent(multipart);
                Transport.send(message);
            }
            catch(Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }
}