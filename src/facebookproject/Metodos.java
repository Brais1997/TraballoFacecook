/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookproject;

import com.restfb.*;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.FacebookType;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author jota
 */
public class Metodos {

    public final static String ACCESSTOKEN = "EAACEdEose0cBAIiCrrqEx4ZBxDE5UQQqLnxKMgd7J95L9ZCGu7ClFhZC8jAPKmARclZCB1V0OnEI2FlGYX0aytTfVZArFSs4WCutNhfUZAqYIqSZCgSpAdyRO1m0TJ0P9skKBfqCZCHwkGCRZAD4r0fvVpTqcAwdZCIO0PgVNVVw792AZDZD";
    public static FacebookClient facebookClient = new DefaultFacebookClient(ACCESSTOKEN);
    public static User me =facebookClient.fetchObject("me", User.class);
    
    /**Publica en el muro del usuario por defecto el mensaje que es pasado como argumento
     @param message El mensaje que se desea publicar
     */
    static void post(String message) throws FacebookOAuthException {
        facebookClient.publish("me/feed", FacebookType.class, Parameter.with("message", message));
    }
    
    /**Publica una imagen junto a un mensaje 
     @param imagePath la ruta de la imagen
     @param messagePost el mensaje que se quiere poner junto a la imagen
     */
    static void uploadImage(String imagePath, String messagePost) throws FileNotFoundException {
        InputStream is = new FileInputStream(new File(imagePath));
        FacebookType publishVideoResponse = facebookClient.publish("me/photos", FacebookType.class,
                BinaryAttachment.with("myphoto.jpg", is),
                Parameter.with("message", messagePost));
    }
    
    /**
     * Obtiene los posts de una página
     * @return DefaultListModel devuelve un modelo para añadir a un JList
     */
    static DefaultListModel getPosts() {
        DefaultListModel model = new DefaultListModel();
        try {
            Page page = facebookClient.fetchObject("https://www.facebook.com/realclubcelta/?pnref=lhc", Page.class);
            System.out.println(page.getName());
            Connection<Post> pageFeed = facebookClient.fetchConnection(page.getId() + "/feed", Post.class);
            while (pageFeed.hasNext()) {
                
                model.addElement(pageFeed.getNextPageUrl());
            }
        } catch (com.restfb.exception.FacebookOAuthException ex) {
            System.out.println("\n!!!!!!! Token Expired !!!!!!!!\n");
        }
        return model;
    }
    
    /**
     * Comenta un post
     * @param message El mensaje que se quiere comentar
     */
    static void comment(String message){
        String id = JOptionPane.showInputDialog("Introduzca el id del post:");
       facebookClient.publish(id+"/comments", String.class, Parameter.with("message", message));
       
    }
    
    /**
     * Le da me gusta a un post
     */
    static void like(){
        String s = JOptionPane.showInputDialog("Introduzca el id:");
        facebookClient.publish(s+"/likes", Boolean.class);
        
    }

}
