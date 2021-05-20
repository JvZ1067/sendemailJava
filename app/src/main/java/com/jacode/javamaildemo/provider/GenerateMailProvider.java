package com.jacode.javamaildemo.provider;

import android.content.Context;
import android.util.Log;

import com.jacode.javamaildemo.model.ConfigurationModel;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GenerateMailProvider {

    public static MimeMessage PlainMail(String to, String subject, String bodyText, Context context)  {
       try {
           ConfigurationModel config= ConfigurationProvider.ReadConfiguration(context);
           if (config.getPort()==0){
               Log.e("Error","Return");
               return null;
           }

           // Create a new properties with smtp configuration
           Properties properties =new Properties();
           properties.put("mail.smtp.host", config.getHost());
           properties.put("mail.smtp.socketFactory.port", config.getPort());
           properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
           properties.put("mail.smtp.auth", "true");

           // CREATE A NEW AUTHENTICATION FOR THE USER
           Authenticator authenticator= new Authenticator() {
               @Override
               protected PasswordAuthentication getPasswordAuthentication() {
                   return new PasswordAuthentication(config.getUsername(),config.getPassword());
               }
           };

           // OPEN SESSION
           Session session=Session.getDefaultInstance(properties,authenticator);

           MimeMessage message=new MimeMessage(session);

           message.setFrom(config.getUsername());
           message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
           message.setSubject(subject);
           message.setText(bodyText);

            // RETURN THE MAIL
           return message;
       }catch (Exception e) {
           Log.e("Error",e.toString());
           return null;
       }

    }


}
