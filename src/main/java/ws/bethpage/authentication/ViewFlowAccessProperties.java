/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.bethpage.authentication;

/**
 *
 * @author Harshil
 */
public class ViewFlowAccessProperties {
    
    //Properties
    private boolean userAuthenticated;
    
    //Singleton pattern to keep properties constant throughout the entire application.
    private static ViewFlowAccessProperties properties;
    
    private ViewFlowAccessProperties() {
        userAuthenticated = false;
    }
    
    public static ViewFlowAccessProperties getViewFlowAccessProperties() {
        if (properties == null) {
            properties = new ViewFlowAccessProperties();
        }
        return properties;
    }

    public boolean isUserAuthenticated() {
        return userAuthenticated;
    }

    public void setUserAuthenticated(boolean userAuthenticated) {
        this.userAuthenticated = userAuthenticated;
    }
        
}
