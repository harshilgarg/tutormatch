package ws.bethpage.tutormatch;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Window;

/*
 * The HelpOverlay class extends Window. A component that represents a floating 
 * popup window that can be added to a UI. A window is added to a UI using 
 * UI.addWindow(Window).The contents of a window is set using 
 * AbstractSingleComponentContainer.setContent(Component) or by using the 
 * Window(String, Component) constructor. A window can be positioned on the screen 
 * using absolute coordinates (pixels) or set to be centered using center().
 * The caption is displayed in the window header.
 */
public class HelpOverlay extends Window {

    public HelpOverlay() {
        //Sets the content of the window to an empty CssLayout.
        setContent(new CssLayout());
        
        //Allows CSS styling of the window, in the dashboard.scss file.
        setPrimaryStyleName("help-overlay");
        
        //Stops dragging or resizing of this special subclass of window objects.
        setDraggable(false);
        setResizable(false);
    }
    
    /*
    * Method for adding components to the overlay.
    */
    public void addComponent(Component c) {
        /* Adds the component to the already existing content. Notice that windows
       * are single component containers, and in this case, components are added
       * to the CssLayout (which is the content of the window, since the layout
       * acts as a single component)
       */
        ((CssLayout) getContent()).addComponent(c);
    }

}
