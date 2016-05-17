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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author jota
 */
public class Metodos {

    public final static String ACCESSTOKEN = "EAACEdEose0cBAMNWZCGrFZBgT3Y4o183QZBWYT8Rb6HFvzznBHezDCm2ewZAFRDhktVQJN5YJLkTmv7pfh80pDALn3AgKTJTU1bPJdgNcoXuKDXr33f7bImvMfEwPuH8PrTXV58xBC4859M3P4Mx0dqG6J1rZC9ykTVKUX6kRMQZDZD";
    public static FacebookClient facebookClient = new DefaultFacebookClient(ACCESSTOKEN);

    static void post(String message) throws FacebookOAuthException {
        facebookClient.publish("me/feed", FacebookType.class, Parameter.with("message", message));
    }

    static void uploadImage(String imagePath, String messagePost) throws FileNotFoundException {
        InputStream is = new FileInputStream(new File(imagePath));
        FacebookType publishVideoResponse = facebookClient.publish("me/photos", FacebookType.class,
                BinaryAttachment.with("myphoto.jpg", is),
                Parameter.with("message", messagePost));
    }

    static DefaultListModel getPosts() {
        DefaultListModel model = new DefaultListModel();
        try {
            Page page = facebookClient.fetchObject("100008350479291", Page.class);
            System.out.println(page.getName());
            Connection<Post> pageFeed = facebookClient.fetchConnection(page.getId() + "/feed", Post.class);
            while (pageFeed.hasNext()) {
                pageFeed = facebookClient.fetchConnectionPage(pageFeed.getNextPageUrl(), Post.class);
                model.addElement(pageFeed.getNextPageUrl());
            }
        } catch (com.restfb.exception.FacebookOAuthException ex) {
            System.out.println("\n!!!!!!! Token Expired !!!!!!!!\n");
        }
        return model;
    }

}
