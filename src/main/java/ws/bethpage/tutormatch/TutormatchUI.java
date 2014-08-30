package ws.bethpage.tutormatch;

import com.vaadin.annotations.PreserveOnRefresh;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import java.util.Locale;
import ws.bethpage.views.ErrorView;
import ws.bethpage.views.LoginView;
import ws.bethpage.views.ProfileView;
//import ws.bethpage.views.ProfileView;
//import ws.bethpage.views.ProfileView;

@Theme("dashboard")
@Title("TutorMatch")
@PreserveOnRefresh
@SuppressWarnings("serial")
public class TutormatchUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = TutormatchUI.class, widgetset = "ws.bethpage.tutormatch.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }
    
    //Navigator to control various views in the application.
    private Navigator nav;
    
    private String loggedInUser;


    @Override
    protected void init(VaadinRequest request) {
         //Set locale of application to United States
        setLocale(Locale.US);

        nav = new Navigator(UI.getCurrent(), UI.getCurrent());
        
        nav.addView("",new LoginView());
        nav.setErrorView(new ErrorView());
        //nav.addView("profile", new ProfileView());
        
        
        nav.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                if (event.getNewView() instanceof ProfileView
                        && ((TutormatchUI)UI.getCurrent()).getLoggedInUser() == null) {
                    Notification.show("Permission denied", Type.ERROR_MESSAGE);
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {
            	System.out.println("After view change");
            }

        });

        
    }
    
    public String getLoggedInUser() {
        return loggedInUser;
    }
    
    public void setLoggedInUser(String user) {
        loggedInUser = user;
    }

    
    private void configureNavigator() {
        nav.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(
                    ViewChangeListener.ViewChangeEvent event) {
                return viewAccess(event.getOldView(), event.getNewView());
            }

            @Override
            public void afterViewChange(ViewChangeListener.ViewChangeEvent event) {
                
            }
        });
    }
    
    private boolean viewAccess(View oldView, View newView) {
        //ViewFlowAccessProperties properties = ViewFlowAccessProperties.getViewFlowAccessProperties();
        /*if (oldView instanceof LoginView && newView instanceof ProfileView && properties.isUserAuthenticated()) {
            return true;
        }
        return false;*/
        return false;
    } 

}
