package apiTest;

import org.mortbay.jetty.servlet.AbstractSessionManager;

import javax.mail.*;
import javax.mail.search.SubjectTerm;
import java.security.NoSuchProviderException;
import java.util.Properties;


public class GmailApiTest {

    // main function. Project run starts from main function...
    public static void main(String[] args) {

        String host = "IMAP.gmail.com";
        String protocol = "imaps";
        String username = "ayeliqa@gmail.com";
        String password = "foxsrfesavtbujlo";


        try {
            //create properties field
            Properties properties = System.getProperties();

            properties.put("mail.store.protocol", protocol);
            Session emailSession = Session.getDefaultInstance(properties);
            //create the IMAP store object and connect with the IMAP server
            Store store = emailSession.getStore(protocol);

            System.out.println("Connecting to gmail...");
            store.connect(host, username, password);

            //create the folder object and open it
            Folder inbox = store.getFolder("Inbox");


            inbox.open(Folder.READ_ONLY);

            System.out.println("searching messages containing subject \"funny\"...");
            Message[] foundMessages = inbox.search(new SubjectTerm("funny"));

            // print number of found messages
            System.out.println("number of found messages: " + foundMessages.length);

            for (Message message : foundMessages) {
                Object obj = message.getContent();
                Multipart mp = (Multipart) obj;
                BodyPart bp = mp.getBodyPart(0);

                System.out.println("mail body:\n-----------");
                System.out.println(bp.getContent());
            }

            //close the store and folder objects
            inbox.close(false);
            store.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
