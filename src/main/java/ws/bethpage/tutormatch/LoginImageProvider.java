/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.bethpage.tutormatch;

import java.util.Random;

/**
 *
 * @author Harshil
 */
public class LoginImageProvider {
    /*static String [] imageList =  {
       "https://s3.amazonaws.com/ooomf-com-files/cWT6BHfQDW5yDoN1qgn0_DSC06383.png", 
        "https://s3.amazonaws.com/ooomf-com-files/yJl7OB3sSpOdEIpHhZhd_DSC_1929_1.jpg",
        "http://bit.ly/1lNkyWH",
        "http://666a658c624a3c03a6b2-25cda059d975d2f318c03e90bcf17c40.r92.cf1.rackcdn.com/unsplash_5243a2eb2bc02_1.JPG"
    };*/
    private static final String [] imageList =  {
       "https://www.andyou.com/blog/wp-content/uploads/2014/04/studying1.jpg",
        "http://www.unisstral.it/wp-content/uploads/001036.jpg",
        "https://blogs.shu.ac.uk/shutech/files/2014/05/doctype-hi-res.jpg",
        "http://lisaabellera.files.wordpress.com/2013/05/writers-desk.jpg",
     };
    
    private static final String [] imageOpacity = {
        ".3",".3",".3",".3"
    };
    private static int getRandomImageID() {
        return (new Random().nextInt(imageList.length));
    }
    public static String getHTML() {
        int id = getRandomImageID();
        String html = "<div style=\"width:100%;height:100%;"
                + "background:url("
                + imageList[id]
                + ") no-repeat center center fixed;background-size:cover;opacity:"
                + imageOpacity[id]
                + ";\"></div>";
        return html;
    }
    public static String getHTML(int imageID) {
        int id = imageID;
        String html = "<div style=\"width:100%;height:100%;"
                + "background:url("
                + imageList[id]
                + ") no-repeat center center fixed;background-size:cover;opacity:"
                + imageOpacity[id]
                + ";\"></div>";
        return html;
    }
    
}
