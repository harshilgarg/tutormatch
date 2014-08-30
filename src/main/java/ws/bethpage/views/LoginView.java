package ws.bethpage.views;

//Import statements.
import ws.bethpage.authentication.Authentication;
import ws.bethpage.tutormatch.HelpManager;
import ws.bethpage.tutormatch.HelpOverlay;
import ws.bethpage.tutormatch.LoginImageProvider;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import ws.bethpage.authentication.ViewFlowAccessProperties;
import ws.bethpage.tutormatch.TutormatchUI;

public class LoginView extends CssLayout implements View {
    
    //UI of application.
    private UI ui;
    
    //Navigator of application
    private Navigator navigator;
    
    //Handles the appearance of the help overlay window on the bottom of the Login screen.
    private HelpManager helpManager;

    //The vertical layout that controls the entire login screen.
    private VerticalLayout loginLayout;

    //HTML label that contains the background image.
    public Label backgroundImageContainer;
    
    //Stores page details. Needed for positioning of certain text elements.
    private final int browserWidth;
    private final int browserHeight;
    
    //Constructor, defines some login screen variables.
    public LoginView() {
        buildLogin();
    }
    
    private void setLayoutAttributes() {
        //Define dimensions of the browser page for appropriate positioning of HTML elements.
        browserWidth = Page.getCurrent().getBrowserWindowWidth();
        browserHeight = Page.getCurrent().getBrowserWindowHeight();
        
        //Sets a local instance of the current UI and styles it
        ui = UI.getCurrent();
        ui.addStyleName("login");
        
        //Sets a local instance of the Navigator.
        navigator = ui.getNavigator();
       
        //Sets the view to a full screen unscrollable CssLayout component.
        this.setSizeFull();
        
        //Sets title of page. Evident in browser tab.
        Page.getCurrent().setTitle("Login to TutorMatch");
    }
    
    private void createHelpMessage() {
         //HelpManager to control help overlay windows.
        helpManager = new HelpManager(UI.getCurrent()); 
        
        //Closes all helpManager windows if open already.
        helpManager.closeAll();
        
        //Creates a new HelpOverlay, which is added to the helpManager instance.
        HelpOverlay w = helpManager
                .addOverlay(
                        "Welcome to the Bethpage High School TutorMatch application",
                        "<p>This application is designed as a resource to ask questions in academic subjects, answered only by registered BHS tutors.</p>"
                        + "<p>Please login with the school email account and password to either ask or answer questions. Contact administration for help.</p>",
                        "login");
        
        //Centers the window, or HelpOverlay object.
        w.center();
        
        //Adds the window to the UI.
        ui.addWindow(w);
    }
    
    private void setupLoginPanel() {
        //Sets the necessary translation for the login panel window, based on browser height.
        double loginPanelTranslation = (155 * browserHeight / 444) - (27835 / 148);
        
        //Creates the translation by dynamically adding CSS.
        String loginPanelPosition = ".login-layout {position:relative;z-index:2;transform: translate(0px, "
                + loginPanelTranslation + "px);-webkit-transform: translate(0," + loginPanelTranslation + "px); }";
        
        //Dynamically adds the CSS styling.
        Page.getCurrent().getStyles().add(loginPanelPosition);
    }
    
    private void displayLoginImage() {
        //Gets the appropriate HTML from the LoginImageProvider class.
        String imageHTML = LoginImageProvider.getHTML();
        
        //Creates a new Label with the ContentMode.HTML option to enable addition of HTML elements dynamically.
        backgroundImageContainer = new Label(imageHTML, ContentMode.HTML);
        backgroundImageContainer.addStyleName("login-bg");
        
        //Adds the Label to the CssLayout.
        this.addComponent(backgroundImageContainer);

    }
    
    private void buildLogin() {
        //Background components and attributes.
        setLayoutAttributes();
        createHelpMessage();
        setupLoginPanel();
        displayLoginImage();
        
        buildComponents();
    }
    
    private VerticalLayout createLoginPanelPrompt() {
        //Vertical Layout for "Sign in using Gmail" prompt and "Forgot your password?" hyperlink.
        VerticalLayout promptLayout = new VerticalLayout();
        
        //Positioning translation for the loginToGmail component.
        double a = (0.5*browserWidth)-150;
        double b = 350;
        
        //"Sign in using Gmail" HTML string.
        String gmailText = "<div style=\"-webkit-transform:translate(" + a + "px,"
                + b + "px);" + "transform:translate(" + a + "px," + b 
                + "px);color: rgb(255,255,255);\n" + "opacity: 1;"
                + "font-weight: 200;font-size:16px;font-style:normal;font-family:'Source Sans Pro';position:fixed !important;"
                + "\">Sign in using <span style=\"font-weight:400;\">Gmail</span></div>";
       
        //Create label which houses "Sign in using Gmail" prompt
        Label infoPrompt = new Label(gmailText, ContentMode.HTML);
        
        //Adds the HTML label to the loginToGmail component.
        promptLayout.addComponent(infoPrompt);
        promptLayout.setComponentAlignment(infoPrompt, Alignment.TOP_CENTER);
        
        double c = (0.5*browserWidth);
        double d = 346;
        
        //Link for navigating to another page in the application.
        Link link = new Link("Forgot your password?", new ExternalResource("http://www.vaadin.com"));
        link.addStyleName("forgot");
        
        //Adds forgot password hyperlink, dynamically styles CSS for the link portion.
        Page.getCurrent().getStyles().add(".v-link.forgot {font-family:'Source Sans Pro';font-weight:200;font-size: 16px;color: rgb(207, 207, 207);transform: translate("
        + c + "px," + d + "px);-webkit-transform:translate("+ c + "px,"+ d + "px);}");
        
        //Adss the hyperlink styling.
        Page.getCurrent().getStyles().add(".forgot a {color:white;text-decoration:none;}");
        
        //Returns the layout.
        return promptLayout;
    }
    
    public void buildComponents() {
        //Main vertical layout that represents the login screen. All components will be added to this vertical layout.
        loginLayout = new VerticalLayout();
        loginLayout.addStyleName("login-layout");
        
        //Adds the prompt layout underneath the login panel component to the main vertical layout.
        loginLayout.addComponent(createLoginPanelPrompt(););
    
        //Adds the main vertical layout to the CssLayout.
        this.addComponent(loginLayout);
        
        //Creates a CssLayout that represents the login panel.
        final CssLayout loginPanel = new CssLayout();
        loginPanel.addStyleName("login-panel");
        
        //Vertical layout that will contain a few labels, text fields, and a button that constitute the login screen function.
        final VerticalLayout loginPanelVerticalLayout = new VerticalLayout();

        //Username field horizontal layout.
        final HorizontalLayout usernameFieldHorizontalLayout = new HorizontalLayout();
        usernameFieldHorizontalLayout.setSpacing(true);
        usernameFieldHorizontalLayout.setMargin(true);
        usernameFieldHorizontalLayout.addStyleName("fields");

        //Password horizontal layout.
        final HorizontalLayout passwordFieldHorizontalLayout = new HorizontalLayout();
        passwordFieldHorizontalLayout.setSpacing(true);
        passwordFieldHorizontalLayout.setMargin(true);
        passwordFieldHorizontalLayout.addStyleName("fields2");

        //Sliding animation label creations.
        final Label usernamePrompt = new Label("username or email");
        final Label passwordPrompt = new Label("password");

        //Start with the sliding animation labels translated into the box.
        usernamePrompt.addStyleName("headlabel");
        passwordPrompt.addStyleName("headlabel2");

        //Horizontal layouts for each of the sliding animation labels.
        final HorizontalLayout usernamePromptHorizontalLayout = new HorizontalLayout();
        usernamePromptHorizontalLayout.addComponent(usernamePrompt);

        final HorizontalLayout passwordPromptHorizontalLayout = new HorizontalLayout();
        passwordPromptHorizontalLayout.addComponent(passwordPrompt);

        //Create empty text fields that will constitute the username/password field horizontal layouts.
        final TextField username = new TextField("");
        username.addStyleName("username");

        final PasswordField password = new PasswordField("");
        password.addStyleName("password");
        
        //Change CSS of focused textfields.
        username.addFocusListener(new FieldEvents.FocusListener() {

            @Override
            public void focus(FieldEvents.FocusEvent event) {
                usernamePrompt.addStyleName("darkening");
            }
        });

        password.addFocusListener(new FieldEvents.FocusListener() {

            @Override
            public void focus(FieldEvents.FocusEvent event) {
                passwordPrompt.addStyleName("darkening");
            }
        });
        
        //When the text changes in the username field, animate the label.
        username.addTextChangeListener(new FieldEvents.TextChangeListener() {

            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                //If the textfield for username input is empty, make the label animate upwards.
                if (username.getValue().isEmpty()) {
                    usernamePrompt.removeStyleName("animationdown");
                    usernamePrompt.addStyleName("animationup");
                }

                //If the textfield is being erased, make the label animate downwards.
                if (event.getText().isEmpty()) {
                    usernamePrompt.addStyleName("animationdown");
                    usernamePrompt.removeStyleName("animationup");
                    username.setValue("");
                }
            }
        });
        
        //When the text changes in the password field, animate the label.
        password.addTextChangeListener(new FieldEvents.TextChangeListener() {

            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                //If the textfield for username input is empty, make the label animate upwards.
                if (password.getValue().isEmpty()) {
                    passwordPrompt.removeStyleName("animationdown2");
                    passwordPrompt.addStyleName("animationup2");
                }

                //If the textfield is being erased, make the label animate downwards./
                if (event.getText().isEmpty()) {
                    passwordPrompt.addStyleName("animationdown2");
                    passwordPrompt.removeStyleName("animationup2");
                    password.setValue("");
                }
            }
        });
        
        //Fast responsiveness to the text change listeners.
        username.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        password.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        
        //If the username field is blurred (unfocused) and field is not empty, then fade away the sliding animation label. 
        username.addBlurListener(new FieldEvents.BlurListener() {

            @Override
            public void blur(FieldEvents.BlurEvent event) {
                if (!username.getValue().isEmpty()) {
                    usernamePrompt.removeStyleName("darkening");
                    usernamePrompt.removeStyleName("animationdown");
                    usernamePrompt.addStyleName("fading");
                }
            }
        });
        
         //If the password field is blurred (unfocused), and field is not empty, then fade away the sliding animation label. 
        password.addBlurListener(new FieldEvents.BlurListener() {

            @Override
            public void blur(FieldEvents.BlurEvent event) {
                if (!password.getValue().isEmpty()) {
                    passwordPrompt.removeStyleName("darkening");
                    passwordPrompt.removeStyleName("animationdown2");
                    passwordPrompt.addStyleName("fading");
                }

            }
        });
        
        //Adds the completely styled and functionalized fields to their respective horizontal layouts.
        usernameFieldHorizontalLayout.addComponent(username);
        passwordFieldHorizontalLayout.addComponent(password);
        
        //Horizontal layout to contain the sign in button.
        final HorizontalLayout signInButtonHorizontalLayout = new HorizontalLayout();
        signInButtonHorizontalLayout.setSpacing(true);
        signInButtonHorizontalLayout.setMargin(true);
        signInButtonHorizontalLayout.addStyleName("fields");
    
        //Create the sign in button with the default style name.
        final Button signin = new Button("Sign in");
        signin.addStyleName("default");
        
        //Add button to its horizontal layout.
        signInButtonHorizontalLayout.addComponent(signin);
        signInButtonHorizontalLayout.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);
    
        //If user presses enter instead of clicking, the sign in button should still activate.
        final ShortcutListener enter = new ShortcutListener("Sign In", ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                signin.click();
            }
        };
        signin.addShortcutListener(enter);
        
        //Horizontal layout that will contain errors that arise due to failed authentication in the log in functionality.
        final HorizontalLayout error = new HorizontalLayout();
        
        //Configure the signin button, to correctly authenticate and display error messages, and also navigate to ProfileView.
        signin.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                
                //Represents results of authentication.
                boolean auth = Authentication.authenticate(username.getValue(), password.getValue());
                
                //Represnts if the user should be granted access.
                boolean isUser = username.getValue().endsWith("@bethpageeagles.ws");
                
                //If the user is eligible and authentication is sucessful, allow advancement to next navigation view.
                if (isUser && auth) {
                    //If there is a previous error message, delete it.
                    if (error.getComponentCount() == 1) {
                        error.removeComponent(error.getComponent(0));
                    }
                    
                    //Create the user profile.
                    navigator.addView("profile", new ProfileView());
                    //Sets the user login status in the UI class to true, in order to prevent access failures or breaches.
                    ((TutormatchUI) ui).setLoggedInUser(username.getValue());
                    //Navigate to user profile.
                    navigator.navigateTo("profile");
                } 
                //If user is not eligible, print error message and deny access.
                else if ((!isUser && auth) || (!isUser && !auth)) {
                    //If there is a previous error message, delete it.
                    if (error.getComponentCount() == 1) {
                        error.removeComponent(error.getComponent(0));
                    }
                    
                    //Create and style error message, including animations.
                    Label errorMessage = new Label("Please sign in with Bethpage Eagles account.", ContentMode.HTML);
                    errorMessage.addStyleName("error");
                    errorMessage.setSizeUndefined();
                    errorMessage.addStyleName("light");
                    errorMessage.addStyleName("v-animate-reveal");
                    
                    //Add the error message to the horizontal layout that contains it.
                    error.addComponent(errorMessage);

                } 
                //If the user is eligible, but the authentication fails, deny access, but print a different error message.
                else if (isUser && !auth) {
                    //If there is a previous error message, delete it.
                    if (error.getComponentCount() == 1) {
                        error.removeComponent(error.getComponent(0));
                    }
                    
                    //Create and style error message, including animations.
                    Label errorMessage = new Label("Authentication failed. Please try again.", ContentMode.HTML);
                    errorMessage.addStyleName("error");
                    errorMessage.setSizeUndefined();
                    errorMessage.addStyleName("light");
                    errorMessage.addStyleName("v-animate-reveal");
                    
                    //Add the error message to the horizontal layout that contains it.
                    error.addComponent(errorMessage);
                }
            }
        });
        
        //Heading welcome labels horizontal layout and labels addition.
        HorizontalLayout welcomeLabel = new HorizontalLayout();
        Label m = new Label("Login to");
        Label n = new Label("TutorMatch");
        m.addStyleName("loginlabel");
        n.addStyleName("tutormatch");
        welcomeLabel.addComponent(m);
        welcomeLabel.addComponent(n);
        
        //The line label horizontal layout, that separates the welcome labels from the textfields and button.
        HorizontalLayout line = new HorizontalLayout();
        Label o = new Label("This text will never be aaaaaaaaaaaaa");
        o.addStyleName("linelabel");
        line.addComponent(o);
        
        //Add all components created above to the login panel vertical layout.
        loginPanelVerticalLayout.addComponent(welcomeLabel);
        loginPanelVerticalLayout.setComponentAlignment(welcomeLabel,Alignment.TOP_CENTER);

        loginPanelVerticalLayout.addComponent(line);
        loginPanelVerticalLayout.setComponentAlignment(line,Alignment.TOP_CENTER);

        loginPanelVerticalLayout.addComponent(usernamePromptHorizontalLayout);
        loginPanelVerticalLayout.setComponentAlignment(usernamePromptHorizontalLayout, Alignment.TOP_CENTER);

        loginPanelVerticalLayout.addComponent(usernameFieldHorizontalLayout);
        loginPanelVerticalLayout.setComponentAlignment(usernameFieldHorizontalLayout, Alignment.TOP_CENTER);
        
        loginPanelVerticalLayout.addComponent(passwordPromptHorizontalLayout);
        loginPanelVerticalLayout.setComponentAlignment(passwordPromptHorizontalLayout, Alignment.MIDDLE_CENTER);
        
        loginPanelVerticalLayout.addComponent(passwordFieldHorizontalLayout);
        loginPanelVerticalLayout.setComponentAlignment(passwordFieldHorizontalLayout, Alignment.MIDDLE_CENTER);
        
        loginPanelVerticalLayout.addComponent(signInButtonHorizontalLayout);
        loginPanelVerticalLayout.setComponentAlignment(signInButtonHorizontalLayout, Alignment.MIDDLE_CENTER);
        
        loginPanelVerticalLayout.addComponent(errorHorizontalLayout);
        loginPanelVerticalLayout.setComponentAlignment(errorHorizontalLayout, Alignment.MIDDLE_CENTER);
        
        //Vertical layout is added to CSS layout login panel.
        loginPanel.addComponent(loginPanelVerticalLayout);
        
        //In turn, the CSS layout is added to the outer vertical layout (which has been already added to this CSSlayout)
        loginLayout.addComponent(loginPanel);
        loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
