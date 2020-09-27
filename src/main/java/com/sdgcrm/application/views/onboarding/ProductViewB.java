package com.sdgcrm.application.views.onboarding;

import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.ProductService;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.security.SecurityUtils;
import com.sdgcrm.application.views.AppConst;
import com.sdgcrm.application.views.dashboard.DashboardView;
import com.sdgcrm.application.views.product.ProductForm;
import com.sdgcrm.application.views.product.ProductView;
import com.sdgcrm.application.views.signup.SignUpView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("get-started/product")
@CssImport("./styles/shared-styles.css")
@Viewport(AppConst.VIEWPORT)
public class ProductViewB extends VerticalLayout {


    ProductView productView;
    ProductService productService;
    UserService userService;
    User currentUser;




    Button next = new Button("Next");
    Button skip = new Button("Skip Walk Through");

    public ProductViewB(@Autowired ProductService productService, @Autowired UserService userService) {
        this.productService= productService;
        this.userService= userService;

        this.currentUser= userService.findByEmail(SecurityUtils.getLoggedinUsername());
        productView= new ProductView(productService, userService);


        Image image = new Image("https://res.cloudinary.com/moversng/image/upload/v1601219633/signuplogo_dxlkwc_xwgrcc.svg", "logo");

        setAlignItems(Alignment.START);
        image.getStyle().set("margin","0px auto");


        H6 text= new H6("Welcome to EasyCRM. This App will help you manage your Business Basic Operations seamlessly.\n" +
                "Let’s get you started with just 9 simple steps\n");

        H6 step= new H6("STEP ONE");

        H6 body= new H6("YOU CAN ADD YOUR BUSINESS INVENTORY HERE ( I.E. YOUR PRODUCTS/STOCKS- THINGS YOU SELL) DON’T WORRY, YOU CAN ALWAYS COME BACK TO UPDATE YOUR THIS LIST");

        productView.getGrid().setVisible(true);
        add(image, text, step, body, productView, createButtonsLayout());

        getStyle().set("max-width","950px");
        getStyle().set("margin","0px auto");


        next.addClickListener(e -> {
            UI.getCurrent().navigate(EmployeeViewB.class);
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
