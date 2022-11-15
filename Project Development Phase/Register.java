package inventorymanagement;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailOtp{
        static int ot;
        public void OTP(String to){
        final String username = "samplemailforbootcamp@gmail.com";
        final String password = "@bootteam123";
        final String from = "samplemailforbootcamp@gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Authenticator a = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        int max =9999;
        int min =1000;
        ot = (int)(Math.random()*(max-min+1)+min);
        Session session = Session.getInstance(props, a);
        try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
                message.setSubject("OTP");
                message.setText(String.valueOf(ot)+"   "+"this the OTP(One Time Password) Veriication Code for Inventory Registration");
                Transport.send(message);
                System.out.println("Done");
        } catch (MessagingException e) {
                System.out.println(e);
        }
    }
}

    
