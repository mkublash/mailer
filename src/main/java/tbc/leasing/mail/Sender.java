package tbc.leasing.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sender {

    private   Properties props;
    private   Session session;

    private   String FROM = "test1@tbcleasing.ge";


    public Sender(String aUserLogin,String aPassword) {

        this.FROM = aUserLogin;


        props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.host", "m.outlook.com");
        props.put("mail.smtp.auth", "true");
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, aPassword);
            }
        });
//        session.setDebug(true);
    }

    public void send(String to, String subject, File baseImage) throws MessagingException {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        /*message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(WebConstants.AdminMail));*/
        message.setSubject(subject);

        MimeMultipart multipart = new MimeMultipart("related");

        // first part  (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<html><body><img width=\"100%\" src=\"cid:image\"></body></html>";
        messageBodyPart.setContent(htmlText, "text/html");

        // add it
        multipart.addBodyPart(messageBodyPart);

        // second part (the image)
        messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource(baseImage);
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<image>");

        // add it
        multipart.addBodyPart(messageBodyPart);


//        message.setText(body);
        message.setContent(multipart, "text/html; charset=utf-8");
        //message.setText(messageText);
        Transport.send(message);
////        transport.connect(gmail,port, senderEmail, senderMailPassword);
//        transport.sendMessage(message, message.getAllRecipients());
//
//        transport.close();

    }

    public static List<Contact> loadCsv(File csvFile) throws IOException {

        List<Contact> data = null;

        try (Stream<String> lines = Files.lines(csvFile.toPath(), Charset.defaultCharset())) {
            data = lines.map(s -> {
                String[] split = s.split(";");
                return new Contact(split[0], split[1]);
            }).collect(Collectors.toList());
        }

        return data;
    }

}
