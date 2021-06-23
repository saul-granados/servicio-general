
package com.cominvi.app.oauth.services.impl;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class EmailService {
	
    private Session session = null;
    
    public void setProperties(String host, String puerto, String origen, String password){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", puerto);
        
	session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(origen, password);
            }
        });
    }
    
    public boolean sendEmail (String origen, String destino, String cc, String asunto, 
            String contenido, boolean contenidoAdjunto, String archivoAdjunto){
        try{
            Message msg = new MimeMessage(session);
            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            MimeBodyPart attachPart = new MimeBodyPart();
            
            //Cabecera
            msg.setFrom(new InternetAddress(origen, false));
            msg.setSubject(asunto);
            msg.setContent(contenido, "text/html; charset=UTF-8");
            msg.setSentDate(new Date());
            
            //Cuerpo
            messageBodyPart.setContent(contenido, "text/html");
            
            //Contenido adjunto (en caso de contar con ello)
            if (contenidoAdjunto){
                attachPart.attachFile(archivoAdjunto);
                multipart.addBodyPart(attachPart);
                multipart.addBodyPart(messageBodyPart);
                msg.setContent(multipart);
            }
            
            // Envío de correo con destinatarios
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
            
            if(cc != null && !cc.equals("")) {
            	msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
            }
            
            Transport.send(msg);  
        }
        catch(Exception e){
            System.out.println("Error al enviar el correo electrónico: "+e.getMessage());
            return false;
        }
        
        return true;
    }
    
     
}
