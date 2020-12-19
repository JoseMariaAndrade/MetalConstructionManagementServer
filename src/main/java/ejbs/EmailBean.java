package ejbs;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Stateless
public class EmailBean {

    @Resource(name = "java:/jboss/mail/fakeSMTP")
    private Session mailSession;

    public void send(String to, String subject, String text)
            throws MessagingException {

        Message message = new MimeMessage(mailSession);

        try {

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            message.setSubject(subject);

            if (text != null) {
                message.setText(text);
            } else {
                message.setText("No Comments");
            }

            Date timeStamp = new Date();
            message.setSentDate(timeStamp);

            Transport.send(message);

        } catch (MessagingException messagingException) {
            throw messagingException;
        }
    }
}
