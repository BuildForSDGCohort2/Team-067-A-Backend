package com.sdgcrm.application.views.onboarding;

import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.AssetService;
import com.sdgcrm.application.data.service.EmployeeService;
import com.sdgcrm.application.data.service.ProductService;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.security.SecurityUtils;
import com.sdgcrm.application.views.AppConst;
import com.sdgcrm.application.views.asset.AssetView;
import com.sdgcrm.application.views.dashboard.DashboardView;
import com.sdgcrm.application.views.product.ProductView;
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

@Route("get-started/asset")
@CssImport("./styles/shared-styles.css")
@Viewport(AppConst.VIEWPORT)
public class AssetViewB extends VerticalLayout {


    AssetView assetView;
    AssetService assetService;
    UserService userService;
    EmployeeService employeeService;
    User currentUser;




    Button next = new Button("Finish");


    public AssetViewB(@Autowired AssetService assetService, @Autowired UserService userService, @Autowired EmployeeService employeeService) {
        this.assetService= assetService;
        this.userService= userService;
        this.employeeService= employeeService;

        this.currentUser= userService.findByEmail(SecurityUtils.getLoggedinUsername());
        assetView= new AssetView(assetService, userService, employeeService);


        Image image = new Image("https://res.cloudinary.com/moversng/image/upload/v1601219633/signuplogo_dxlkwc_xwgrcc.svg", "logo");

        setAlignItems(Alignment.START);
        image.getStyle().set("margin","0px auto");


        H6 step= new H6("STEP FOUR");

        H6 body= new H6("ENTER YOUR ASSETS.( LAND, BUILDING, FURNITURE, MOTOR VEHICLES, TABLES ETC.) DON’T WORRY, YOU CAN ALWAYS COME BACK TO UPDATE YOUR THIS LIST. ");


        add(image, step, body, assetView, createButtonsLayout());

        getStyle().set("max-width","950px");
        getStyle().set("margin","0px auto");


        next.addClickListener(e -> {
            UI.getCurrent().navigate(DashboardView.class);
        });




    }

    private HorizontalLayout createButtonsLayout() {
        next.setSizeFull();

        next.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        next.addClickShortcut(Key.ENTER);


        HorizontalLayout footer= new HorizontalLayout();
        footer.setWidthFull();
        footer.add(next);
         return footer;

    }

}
