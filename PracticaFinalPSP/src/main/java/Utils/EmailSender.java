package Utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Emailsender es una clase preparada para lanzar correos electrónicos desde el correo PracticaFinalPSPJoseProfe@gmail.com"
 */
public class EmailSender {

    String username = "";
    String password = "";

    public EmailSender() {

    }

    /**
     * Este método nos permite mandar un correo electrónico a un destinatario que le pasamos por parámetros (emailTo)
     * informando que un producto (que le pasamos por parámetros) se ha quedado sin stock o no habiendo stock
     * se le ha reservado a un cliente y por tanto se requiren más unidades.
     * @param producto
     * @param hora
     * @param fecha
     * @param emailTo
     */
    public void sendEmail(String producto,String hora, String fecha, String emailTo, int precioProveedor) {

        username = "PracticaFinalPSPJoseProfe";
        password = "joseprofe";

        //Seteamos todas las propiedades necesarias para lanzar el correo
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        //Generamos la sesión con su autentificación
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            //Generamos el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailTo));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailTo));
            message.setSubject("Pedido a proveedor. Necesitamos más " + producto);
            message.setText("Hola proveedor, necesitamos 50 unidades del producto " + producto + "\n" +
                    "Dado que en la última compra que nos hicieron a las " + hora + " del " + fecha +
                    " nos ha dejado sin stock o se lo hemos dejado reservado al cliente." + "\n" +
                    "Recordatorio: El precio del artículo es de " + precioProveedor + "€.");

            //Y lo enviamos
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}