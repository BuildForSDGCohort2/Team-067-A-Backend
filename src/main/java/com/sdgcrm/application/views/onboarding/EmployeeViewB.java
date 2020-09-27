package com.sdgcrm.application.views.onboarding;

import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.AssetService;
import com.sdgcrm.application.data.service.EmployeeService;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.security.SecurityUtils;
import com.sdgcrm.application.views.AppConst;
import com.sdgcrm.application.views.asset.AssetView;
import com.sdgcrm.application.views.dashboard.DashboardView;
import com.sdgcrm.application.views.employee.EmployeeView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("get-started/employee")
@CssImport("./styles/shared-styles.css")
@Viewport(AppConst.VIEWPORT)
public class EmployeeViewB extends VerticalLayout {


    EmployeeView employeeView;

    UserService userService;
    EmployeeService employeeService;
    User currentUser;




    Button next = new Button("Next");
    Button skip = new Button("Skip Walk Through");

    public EmployeeViewB(@Autowired EmployeeService employeeService, @Autowired UserService userService) {

        this.userService= userService;
        this.employeeService= employeeService;

        this.currentUser= userService.findByEmail(SecurityUtils.getLoggedinUsername());
        employeeView= new EmployeeView(employeeService, userService);


        Image image = new Image("https://res.cloudinary.com/moversng/image/upload/v1601219633/signuplogo_dxlkwc_xwgrcc.svg", "logo");

        setAlignItems(Alignment.START);
        image.getStyle().set("margin","0px auto");


        H6 step= new H6("STEP TWO");

        H6 body= new H6("ENTER YOUR STAFF DETAILS. (IF ANY) SKIP THIS STEP IF YOU DON’T HAVE STAFF YET. DON’T WORRY, YOU CAN ALWAYS COME BACK TO UPDATE YOUR INCOME LIST AS YOU MAKE INCOME.");


        add(image, step, body, employeeView, createButtonsLayout());

        getStyle().set("max-width","950px");
        getStyle().set("margin","0px auto");
        getStyle().set("overflow","hidden");

        next.addClickListener(e -> {
            UI.getCurrent().navigate(CustomerViewB.class);
        });

        skip.addClickListener(e -> {
            UI.getCurrent().navigate(DashboardView.class);
        });


    }

    private HorizontalLayout createButtonsLayout() {
        next.setSizeFull();
        skip.setSizeFull();
        skip.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        next.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        next.addClickShortcut(Key.ENTER);


        HorizontalLayout footer= new HorizontalLayout();
        footer.setWidthFull();
        footer.add(next, skip);
         return footer;

    }

}
