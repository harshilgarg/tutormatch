/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.bethpage.authentication;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

/**
 * @author Harshil Garg
 * Tuesday, August 26, 2014
 */
public class Authentication {
    
    private static String username;
    private static String password;
    
    private static Properties properties;
    private static Session session;
    
    
    public static boolean authenticate (String username, String password) {
        Authentication.username = username;
        Authentication.password = password;
        
        setProperties();
        createSession();
        
        //properties.toString();
        
        //return session.toString();
        return tryToEstablishConnection();
    }
    
    private static void setProperties() {
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
    }
    
    private static void createSession() {
        session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                }
        );
    }
    
    private static boolean tryToEstablishConnection() {
        Transport transport;
        try {
            transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", username, password);
            transport.close();
            return true;
        } catch (NoSuchProviderException e) {
            return false;
        } catch (MessagingException e) {
            //This is the exception which is called authentication is unsuccessful.
            return false;
        }
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Authentication.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Authentication.password = password;
    }
    
}
