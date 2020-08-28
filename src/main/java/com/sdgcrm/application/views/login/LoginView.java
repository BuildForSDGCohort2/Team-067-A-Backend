package com.sdgcrm.application.views.login;

import com.sdgcrm.application.views.AppConst;
import com.sdgcrm.application.views.signup.SignUpView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route("login")
@CssImport("./styles/shared-styles.css")
@Viewport(AppConst.VIEWPORT)
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm login = new LoginForm();

    public LoginView(){
        addClassName("login-view");



        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");
        addClassName("centered-content");

        add(new H1("EASY CRM"), login);
        Button registerbtn= new Button("Create new user", new Icon(VaadinIcon.ARROW_RIGHT));

        registerbtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerbtn.addClickListener(e -> {
            UI.getCurrent().navigate(SignUpView.class);
        });

        add(registerbtn);
    }



    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }







}
