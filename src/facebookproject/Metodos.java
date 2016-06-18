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

    public final static String ACCESSTOKEN = "EAACEdEose0cBAFmJ9kDCAAeVUO7OeHr0Qg3zZBoL4UjGZC6fg37YZAhEkObjQDYEfofodaLurUkhux473ss9HVUaWxfZBqUYgLQdFVBMcaxN3nn777QAVON2MZAiDd8rnWTrIdFrZAsZBfkU70VMgVq2U3y2pBzjqY0eZBA53704UgZDZD";
    public static FacebookClient facebookClient = new DefaultFacebookClient(ACCESSTOKEN);
    public static User me =facebookClient.fetchObject("me", User.class);
    
    /**Publica (por defecto) unha mensaxe no muro do usuario que o esta a usar
     @param message Mesaxe que se quere publicar
     */
    static void post(String message) throws FacebookOAuthException {
        facebookClient.publish("me/feed", FacebookType.class, Parameter.with("message", message));
    }
    
    /**Publica unha imaxe xunto a unha mensaxe no muro do usuario
     @param imagePath ruta da imaxe
     @param messagePost mesaxe que m¡pomos coa imaxe
     */
    static void uploadImage(String imagePath, String messagePost) throws FileNotFoundException {
        InputStream is = new FileInputStream(new File(imagePath));
        FacebookType publishVideoResponse = facebookClient.publish("me/photos", FacebookType.class,
                BinaryAttachment.with("myphoto.jpg", is),
                Parameter.with("message", messagePost));
    }
    
    /**
     * Obten os post dunha paxina (de exemplo poño unha dunha paxina conocida)
     * @return DefaultListModel Devolve un modelo de jlist
     */
    static DefaultListModel getPosts() {
        DefaultListModel model = new DefaultListModel();
        try {
            Page page = facebookClient.fetchObject("https://www.facebook.com/PostureoEspanol/?fref=ts", Page.class);
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
     * @param message Mensaxe que se quere mandar
     */
    static void comment(String message){
        String id = JOptionPane.showInputDialog("Introduzca el id del post:");
       facebookClient.publish(id+"/comments", String.class, Parameter.with("message", message));
       
    }
    
    /**
     * Likear un post
     */
    static void like(){
        String s = JOptionPane.showInputDialog("Introduzca el id:");
        facebookClient.publish(s+"/likes", Boolean.class);
        
    }

}
