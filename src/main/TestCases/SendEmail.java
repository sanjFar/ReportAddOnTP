package main.TestCases;

import org.apache.commons.io.IOUtils;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SendEmail {
    static String ccAddress = "";

    /**
     * @param subject
     * @param reportFilePath
     * @param emailIds
     * @Author: kanhaiya
     */
    @SuppressWarnings("deprecation")
    public void composeMail(String subject, String reportFilePath, String... emailIds) {
        // Recipient's email ID needs to be mentioned.
        String to = "sanjay.sharma@getfareye.com";

        // Sender's email ID needs to be mentioned
        String from = "notifier@getfareye.com";
        final String username = "notifier@getfareye.com";// change accordingly
        final String password = "user@123";// change accordingly

        // Assuming you are sending email through
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");

        // Get the Session object.
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            for (String emailId : emailIds) {
                ccAddress = emailId.concat("," + ccAddress);

            }
            Thread.currentThread().setContextClassLoader( getClass().getClassLoader() );
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccAddress));

            // Set Subject: header field
            message.setSubject(subject);


            // Send the actual HTML message, as big as you like
            StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(new FileInputStream(new File(reportFilePath)), writer);
            } catch (IOException e) {
                System.out.println("File not found");
            }

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(writer.toString(), "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Attach file
            // Multipart multipart = new MimeMultipart();
            // Part two is attachment
            Zipper zip = new Zipper();
            try {
                zip.zipFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            messageBodyPart = new MimeBodyPart();
            String filename = "./Report.zip";
            this.getClass().getResource("./Report.zip");
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("Report.zip");
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);


        } catch (MessagingException  e) {
            throw new RuntimeException(e);
        }
    }
}
