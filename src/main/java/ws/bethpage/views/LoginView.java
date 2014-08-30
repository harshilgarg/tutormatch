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

    //Handles the appearance of the help overlay window on the bottom of the Login screen.
    private HelpManager helpManager;

    //The vertical layout that controls the entire login screen.
    private VerticalLayout loginLayout;

    //HTML label that contains the background image.
    public Label backgroundImageContainer;
    
    //Stores page details. Needed for positioning of certain text elements.
    private final int browserWidth;
    private final int browserHeight;

    public LoginView() {
        this.browserWidth = Page.getCurrent().getBrowserWindowWidth();
        this.browserHeight = Page.getCurrent().getBrowserWindowHeight();
        buildLogin();
    }

    private void buildLogin() {
        //HelpManager to control help overlay windows.
        helpManager = new HelpManager(UI.getCurrent());
        //Full screen unscrollable view component.
        setSizeFull();
        //Sets title of page. Evident in browser tab.
        Page.getCurrent().setTitle("Login to TutorMatch");
        
        //Sets the necessary translation for the login panel window, based on browser height.
        double loginPanelTranslation = (155 * browserHeight / 444) - (27835 / 148);
        
        //Creates the translation by dynamically adding CSS
        String loginPanelPosition = ".login-layout {position:relative;z-index:2;transform: translate(0px, "
                + loginPanelTranslation + "px);-webkit-transform: translate(0," + loginPanelTranslation + "px); }";
        
        Page.getCurrent().getStyles().add(loginPanelPosition);

        //Login image display.
        String imageHTML = LoginImageProvider.getHTML();
        backgroundImageContainer = new Label(imageHTML, ContentMode.HTML);
        backgroundImageContainer.addStyleName("login-bg");

        this.addComponent(backgroundImageContainer);

        buildComponents(false);
    }

    public void buildComponents(boolean exit) {
        if (exit) {
            this.removeAllComponents();
        }
        
        //Window at bottom of screen, help instructions.
        helpManager.closeAll();
        HelpOverlay w = helpManager
                .addOverlay(
                        "Welcome to the Bethpage High School TutorMatch application",
                        "<p>This application is designed as a resource to ask questions in academic subjects, answered only by registered BHS tutors.</p>"
                        + "<p>Please login with the school email account and password to either ask or answer questions. Contact administration for help.</p>",
                        "login");
        w.center();
        UI.getCurrent().addWindow(w);

        UI.getCurrent().addStyleName("login");

        loginLayout = new VerticalLayout();
        loginLayout.addStyleName("login-layout");

        //Vertical Layout for sign in to Gmail.
        VerticalLayout loginToGmail = new VerticalLayout();
    
        double transX = (0.5*browserWidth)-150;
        double transY = 350;
        
        //Sign in using Gmail HTML string.
        String p = "<div style=\"-webkit-transform:translate(" + transX + "px,"
                + transY + "px);" + "transform:translate(" + transX + "px," + transY 
                + "px);color: rgb(255,255,255);\n" + "            opacity: 1;\n"
                + "font-weight: 200;font-size:16px;font-style:normal;font-family:'Source Sans Pro';position:fixed !important;"
                + "\">Sign in using <span style=\"font-weight:400;\">Gmail</span></div>";
        
        //String p = "<div>Sign in using Gmail</div>";
       
        //Create label which houses "Sign in using Gmail" prompt
        Label infoPrompt = new Label(p, ContentMode.HTML);
        infoPrompt.addStyleName("prompt");

        loginToGmail.addComponent(infoPrompt);
        loginToGmail.setComponentAlignment(infoPrompt, Alignment.TOP_CENTER);
        
        //Link for navigating to another page in the application.
        Link link = new Link("Forgot your password?", new ExternalResource("http://www.vaadin.com"));
        link.addStyleName("badboo");

        double linkX = (0.5*browserWidth);
        double linkY = 346;
        
        //Adds forgot password hyperlink
        Page.getCurrent().getStyles().add(".v-link.badboo {font-family:'Source Sans Pro';font-weight:200;font-size: 16px;color: rgb(207, 207, 207);transform: translate("
        + linkX + "px," + linkY + "px);-webkit-transform:translate("+ linkX + "px,"+ linkY + "px);}");
        
        loginToGmail.addComponent(link);
        
        //Adds the "Sign in using Gmail" component to the UI.
        loginLayout.addComponent(loginToGmail);

        this.addComponent(loginLayout);

        final CssLayout loginPanel = new CssLayout();
        loginPanel.addStyleName("login-panel");

        final VerticalLayout loginPanelVerticalLayout = new VerticalLayout();

        //username horizontal layout
        final HorizontalLayout usernameFieldHorizontalLayout
                = new HorizontalLayout();
        usernameFieldHorizontalLayout.setSpacing(true);
        usernameFieldHorizontalLayout.setMargin(true);
        usernameFieldHorizontalLayout.addStyleName("fields");

        //password horizontal layout
        final HorizontalLayout passwordFieldHorizontalLayout
                = new HorizontalLayout();
        passwordFieldHorizontalLayout.setSpacing(true);
        passwordFieldHorizontalLayout.setMargin(true);
        passwordFieldHorizontalLayout.addStyleName("fields2");

        //label creations
        final Label usernamePrompt = new Label("username or email");
        final Label passwordPrompt = new Label("password");

        //start with labels translated into the box
        usernamePrompt.addStyleName("headlabel");
        passwordPrompt.addStyleName("headlabel2");

        //horizontal layouts for each of the labels
        final HorizontalLayout usernamePromptHorizontalLayout
                = new HorizontalLayout();
        usernamePromptHorizontalLayout.addComponent(usernamePrompt);

        final HorizontalLayout passwordPromptHorizontalLayout
                = new HorizontalLayout();
        passwordPromptHorizontalLayout.addComponent(passwordPrompt);

        //create text fields
        final TextField username = new TextField("");
        username.addStyleName("username");

        final PasswordField password = new PasswordField("");
        password.addStyleName("password");

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

        username.addTextChangeListener(new FieldEvents.TextChangeListener() {

            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                /*If the textfield for username input is empty, make the label animate 
                 up. This will return true if the user starts typing, since the old 
                 field value is returned during the text change event trigger. */
                if (username.getValue().isEmpty()) {
                    usernamePrompt.removeStyleName("animationdown");
                    usernamePrompt.addStyleName("animationup");
                }

                /*If the textfield for username is currently empty, after a text 
                 change event has been triggered, it must mean that the user is 
                 erasing the field. Thus, make the label animate down. */
                if (event.getText().isEmpty()) {
                    usernamePrompt.addStyleName("animationdown");
                    usernamePrompt.removeStyleName("animationup");
                    username.setValue("");
                }
            }
        });

        password.addTextChangeListener(new FieldEvents.TextChangeListener() {

            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                /*If the textfield for username input is empty, make the label animate 
                 up. This will return true if the user starts typing, since the old 
                 field value is returned during the text change event trigger. */
                if (password.getValue().isEmpty()) {
                    passwordPrompt.removeStyleName("animationdown2");
                    passwordPrompt.addStyleName("animationup2");
                }

                /*If the textfield for username is currently empty, after a text 
                 change event has been triggered, it must mean that the user is 
                 erasing the field. Thus, make the label animate down. */
                if (event.getText().isEmpty()) {
                    passwordPrompt.addStyleName("animationdown2");
                    passwordPrompt.removeStyleName("animationup2");
                    password.setValue("");
                }
            }
        });

        username.setTextChangeEventMode(
                AbstractTextField.TextChangeEventMode.EAGER);
        password.setTextChangeEventMode(
                AbstractTextField.TextChangeEventMode.EAGER);

        username.addBlurListener(new FieldEvents.BlurListener() {

            @Override
            public void blur(FieldEvents.BlurEvent event) {
                if (!username.getValue().isEmpty()) {
                    usernamePrompt.removeStyleName("darkening");
                    usernamePrompt.removeStyleName("animationdown");
                    //usernamePrompt.removeStyleName("animationup");
                    usernamePrompt.addStyleName("fading");
                }
            }
        });

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
        usernameFieldHorizontalLayout.addComponent(username);
        passwordFieldHorizontalLayout.addComponent(password);

        final HorizontalLayout fields3 = new HorizontalLayout();
        fields3.setSpacing(true);
        fields3.setMargin(true);
        fields3.addStyleName("fields");

        final Button signin = new Button("Sign in");

        signin.addStyleName("default");
        fields3.addComponent(signin);
        fields3.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        final ShortcutListener enter = new ShortcutListener("Sign In",
                ShortcutAction.KeyCode.ENTER, null) {
                    @Override
                    public void handleAction(Object sender, Object target) {
                        signin.click();
                    }
                };

        final HorizontalLayout errorHorizontalLayout = new HorizontalLayout();

        signin.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean auth = Authentication.authenticate(username.getValue(),
                        password.getValue());
                boolean isUser = username.getValue().endsWith(
                        "@bethpageeagles.ws");
                if (isUser && auth) {
                    if (errorHorizontalLayout.getComponentCount() == 1) {
                        // Remove the previous error message
                        errorHorizontalLayout.removeComponent(
                                errorHorizontalLayout.getComponent(0));
                    }
                    //ViewFlowAccessProperties.getViewFlowAccessProperties().setUserAuthenticated(true);
                    UI.getCurrent().getNavigator().addView("profile", new ProfileView());
                    //((TutormatchUI)UI.getCurrent()).setLoggedInUser(username.getValue());
                    UI.getCurrent().getNavigator().navigateTo("profile");
                    //Successful login.
                } else if ((!isUser && auth) || (!isUser && !auth)) {
                    if (errorHorizontalLayout.getComponentCount() == 1) {
                        // Remove the previous error message
                        errorHorizontalLayout.removeComponent(
                                errorHorizontalLayout.getComponent(0));
                    }
                    Label error = new Label(
                            "Please sign in with Bethpage Eagles account.",
                            ContentMode.HTML);
                    error.addStyleName("error");
                    error.setSizeUndefined();
                    error.addStyleName("light");
                    // Add animation
                    error.addStyleName("v-animate-reveal");
                    errorHorizontalLayout.addComponent(error);

                } else if (isUser && !auth) {
                    if (errorHorizontalLayout.getComponentCount() == 1) {
                        // Remove the previous error message
                        errorHorizontalLayout.removeComponent(
                                errorHorizontalLayout.getComponent(0));
                    }
                    Label error = new Label(
                            "Authentication failed. Please try again.",
                            ContentMode.HTML);
                    error.addStyleName("error");
                    error.setSizeUndefined();
                    error.addStyleName("light");
                    // Add animation
                    error.addStyleName("v-animate-reveal");
                    errorHorizontalLayout.addComponent(error);
                }
            }
        });

        signin.addShortcutListener(enter);
        HorizontalLayout welcomeLabel = new HorizontalLayout();
        Label m = new Label("Login to");
        Label n = new Label("TutorMatch");
        m.addStyleName("loginlabel");
        n.addStyleName("tutormatch");
        welcomeLabel.addComponent(m);
        welcomeLabel.addComponent(n);

        HorizontalLayout line = new HorizontalLayout();
        Label o = new Label("This text will never be aaaaaaaaaaaaa");
        o.addStyleName("linelabel");
        line.addComponent(o);

        loginPanelVerticalLayout.addComponent(welcomeLabel);
        loginPanelVerticalLayout.setComponentAlignment(welcomeLabel,
                Alignment.TOP_CENTER);

        loginPanelVerticalLayout.addComponent(line);
        loginPanelVerticalLayout.setComponentAlignment(line,
                Alignment.TOP_CENTER);

        loginPanelVerticalLayout.addComponent(usernamePromptHorizontalLayout);
        loginPanelVerticalLayout.setComponentAlignment(
                usernamePromptHorizontalLayout, Alignment.TOP_CENTER);

        loginPanelVerticalLayout.addComponent(usernameFieldHorizontalLayout);
        loginPanelVerticalLayout.addComponent(passwordPromptHorizontalLayout);
        loginPanelVerticalLayout.setComponentAlignment(
                passwordPromptHorizontalLayout, Alignment.MIDDLE_CENTER);
        loginPanelVerticalLayout.addComponent(passwordFieldHorizontalLayout);
        loginPanelVerticalLayout.addComponent(fields3);
        loginPanelVerticalLayout.setComponentAlignment(
                usernameFieldHorizontalLayout, Alignment.TOP_CENTER);
        loginPanelVerticalLayout.setComponentAlignment(
                passwordFieldHorizontalLayout,
                Alignment.MIDDLE_CENTER);
        loginPanelVerticalLayout.setComponentAlignment(fields3,
                Alignment.MIDDLE_CENTER);
        loginPanelVerticalLayout.addComponent(errorHorizontalLayout);
        loginPanelVerticalLayout.setComponentAlignment(errorHorizontalLayout,
                Alignment.MIDDLE_CENTER);

        //Label x22 = new Label("yolo girl");
        //loginPanelVerticalLayout.addComponent(x22);
        loginPanel.addComponent(loginPanelVerticalLayout);

        loginLayout.addComponent(loginPanel);

        //infoPrompt.addStyleName("prompt");
        //this.setComponentAlignment(infoPrompt, Alignment.MIDDLE_CENTER);
        loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
