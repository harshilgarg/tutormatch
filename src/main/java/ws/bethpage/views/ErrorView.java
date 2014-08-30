/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.bethpage.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Harshil
 */
public class ErrorView extends VerticalLayout implements View {
    
    public ErrorView () {
        addComponent(new Label("hi"));
    }
    

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //If user tries to access profile page without user authentication.
        //if (event.getOldView() instanceof LoginView)
        if (event.getOldView() instanceof View){
            UI.getCurrent().getNavigator().navigateTo("");
        }
        else {
           // UI.getCurrent().getNavigator().navigateTo("");
        }
    }
    
}
