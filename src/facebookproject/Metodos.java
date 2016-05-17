/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookproject;

import com.restfb.*;
import com.restfb.types.FacebookType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *
 * @author jota
 */
public class Metodos {

    public final static String ACCESSTOKEN = "EAACEdEose0cBAKSWYrxsKQihmV7sQGd1HWT7alMlsaQOlxZA4JBFKZCNMhli9kYcdAxWMXgg6WbzNjBXnZCfF1Snc0MTndT7uZCrZBG4KZCkra9jf4dZAF8nV5PSL9kDTj0HhGr6JhgHZA0oR8KiDFbw9VZA9bncA8vOzbIDxi5K5OgZDZD";
    public static FacebookClient facebookClient = new DefaultFacebookClient(ACCESSTOKEN);

    static void post(String message) {
        facebookClient.publish("me/feed", FacebookType.class, Parameter.with("message", message));
    }

    static void uploadImage(String imagePath,String messagePost) throws FileNotFoundException {
        InputStream is = new FileInputStream(new File(imagePath));
        FacebookType publishVideoResponse = facebookClient.publish("me/photos", FacebookType.class,
                BinaryAttachment.with("myphoto.jpg", is),
                Parameter.with("message", messagePost));
    }

}
