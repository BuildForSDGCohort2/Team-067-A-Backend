package com.sdgcrm.application.views.login;

import com.helger.commons.base64.Base64;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.views.AppConst;
import com.sdgcrm.application.views.signup.SignUpView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;

@Route("login")
@CssImport("./styles/shared-styles.css")
@Viewport(AppConst.VIEWPORT)
public class LoginView extends VerticalLayout implements  BeforeEnterObserver {

    private LoginForm login = new LoginForm();
    LoginI18n i18n;
    Span successMessage = new Span();
    public LoginView(){
        this.i18n= createLoginI18n();
       login.setI18n(createLoginI18n());
        addClassName("login-view");

        Image image = new Image("https://res.cloudinary.com/moversng/image/upload/v1598887746/easycrmx2_kqmi0r.svg", "logo");




        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        successMessage.getStyle().set("color", "var(--lumo-success-text-color)");
        successMessage.getStyle().set("padding", "15px 0");

        login.setAction("login");
        addClassName("centered-content");

        add(image,successMessage, login);

        Button registerbtn= new Button("Get Stated", new Icon(VaadinIcon.ARROW_RIGHT));

        registerbtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerbtn.addClickListener(e -> {
            UI.getCurrent().navigate(SignUpView.class);
        });

        add(registerbtn);
    }

    private LoginI18n createLoginI18n() {
        LoginI18n i18n = LoginI18n.createDefault();


	    i18n.setHeader(new LoginI18n.Header());
	    i18n.setForm(new LoginI18n.Form());
	    i18n.setErrorMessage(new LoginI18n.ErrorMessage());


        // define all visible Strings to the values you want
        // this code is copied from above-linked example codes for Login
        // in a truly international application you would use i.e. `getTranslation(USERNAME)` instead of hardcoded string values. Make use of your I18nProvider
        i18n.getHeader().setTitle("Nome do aplicativo");
        i18n.getHeader().setDescription("Descrição do aplicativo");
        i18n.getForm().setUsername("Username"); // this is the one you asked for.
        i18n.getForm().setTitle("Log in");
        i18n.getForm().setSubmit("Login");
        i18n.getForm().setPassword("Password");
        i18n.getForm().setForgotPassword("Forgot password?");
        i18n.getErrorMessage().setTitle("Username/Password Error");
        i18n.getErrorMessage()
                .setMessage("Check that you have entered the correct username and password and try again.");
        i18n.setAdditionalInformation("Easy CRM was created as part of the program for buildforsdg program organised by Andela and Facebook");

        return i18n;
    }

    @Autowired
    UserService userservice;
    @Autowired
    PasswordEncoder encoder;



    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            System.out.println("error");
            login.setError(true);
        }if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("verify")){

            Map<String, List<String>> parametersMap =beforeEnterEvent.getLocation().getQueryParameters().getParameters();
            List<String> token= parametersMap.get("verify");
            String value = token.get(0);

           User validUser= userservice.findByUserToken(value);
            System.out.println(validUser.toString());;
            validUser.setActive(1);

            validUser.setPassword(encoder.encode(validUser.getPassword()));
            userservice.store(validUser);
            successMessage.setText("Account verification successful");
            Notification.show("Account verification successful- "
                    + validUser.getEmail());


        }
    }



}
