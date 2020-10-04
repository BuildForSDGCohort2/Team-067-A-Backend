package com.sdgcrm.application.security;


import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.views.login.LoginView;
import com.sdgcrm.application.views.onboarding.ProductViewB;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    @Autowired
    UserService userService;

    User currentUser;

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::beforeEnter);
        });
    }

    private void beforeEnter(BeforeEnterEvent event) {


        if (!SecurityUtils.isUserLoggedIn() && !event.getLocation().getPath().equals("signup") && !event.getLocation().getPath().equals("terms") ) {
            event.rerouteTo(LoginView.class);

        }
        else{
            this.currentUser= userService.findByEmail(SecurityUtils.getLoggedinUsername());
            System.out.println(currentUser.toString());
            if(!currentUser.isOnboarded()){
                event.rerouteTo(ProductViewB.class);
            }
        }


    }
}
