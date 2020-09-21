package com.sdgcrm.application.views.dashboard;

import com.sdgcrm.application.data.entity.Customer;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.CustomerService;
import com.sdgcrm.application.data.service.EmployeeService;
import com.sdgcrm.application.data.service.ProductService;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.security.SecurityUtils;
import com.sdgcrm.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "dashboard", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("dashboard")
@CssImport("./styles/shared-styles.css")
public class DashboardView extends Div {


    EmployeeService employeeService;
    CustomerService customerService;
    ProductService productService;

    UserService userService;
    User currentUser;

    public DashboardView(@Autowired EmployeeService employeeService,
                         @Autowired CustomerService customerService,
                         @Autowired ProductService productService,
                         @Autowired UserService userService) {

        this.employeeService=employeeService;
        this.userService= userService;
        this.customerService= customerService;
        this.productService= productService;

        this.currentUser= userService.findByEmail(SecurityUtils.getLoggedinUsername());


        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("100%");
       // layout.getStyle().set("border", "1px solid #9E9E9E");

        Component productsTxt = createProductsHeader();
        layout.setFlexGrow(0.5, productsTxt);

        Component staffsTxt = createStaffsHeader();
        layout.setFlexGrow(0.5, staffsTxt);

        Component customerTxt = createCustomerHeader();
        layout.setFlexGrow(0.5, customerTxt);

        layout.add(productsTxt, staffsTxt, customerTxt);




        HorizontalLayout layout2 = new HorizontalLayout();
        layout2.setWidth("100%");
        // layout.getStyle().set("border", "1px solid #9E9E9E");

        Component component1 = createAssetsHeader();
        layout2.setFlexGrow(0.5, component1);

        Component component2 = createOrdersHeader();
        layout2.setFlexGrow(0.5, component2);

        Component component3 = createAccountHeader();
        layout2.setFlexGrow(0.5, component3);

        layout2.add(component1, component2, component3);

        add(layout,layout2);
    }


    private Component createStaffsHeader() {
        Div productdiv= new Div();

        VerticalLayout container= new VerticalLayout();
        container.getStyle().set("align-items", "center");


        H4 he=new H4("STAFF");
        H2 details=new H2(Integer.toString(employeeService.getTotalEmployees(currentUser)));
        container.add(he,details);
        productdiv.add(container);


        return productdiv;

    }

    private Component createCustomerHeader() {
        Div productdiv= new Div();
        VerticalLayout container= new VerticalLayout();
        container.getStyle().set("align-items", "center");


        H4 he=new H4("CUSTOMER");
        H2 details=new H2(Integer.toString(customerService.getTotalCustomers(currentUser)));
        container.add(he,details);
        productdiv.add(container);

        return productdiv;

    }

    private Component createProductsHeader() {
        Div productdiv= new Div();
        VerticalLayout container= new VerticalLayout();
        container.getStyle().set("align-items", "center");


        H4 he=new H4("PRODUCT");
        H2 details=new H2(Integer.toString(productService.getTotalProducts(currentUser)));
        container.add(he,details);
        productdiv.add(container);

        return productdiv;


    }

    private Component createAccountHeader() {
        Div productdiv= new Div();

        VerticalLayout container= new VerticalLayout();
        container.getStyle().set("align-items", "center");


        H4 he=new H4("ASSET");
        H2 details=new H2(Integer.toString(employeeService.getTotalEmployees(currentUser)));
        container.add(he,details);
        productdiv.add(container);


        return productdiv;
    }

    private Component createOrdersHeader() {
        Div productdiv= new Div();

        VerticalLayout container= new VerticalLayout();
        container.getStyle().set("align-items", "center");


        H4 he=new H4("ORDER");
        H2 details=new H2(Integer.toString(employeeService.getTotalEmployees(currentUser)));
        container.add(he,details);
        productdiv.add(container);


        return productdiv;
    }

    private Component createAssetsHeader() {
        Div productdiv= new Div();

        VerticalLayout container= new VerticalLayout();
        container.getStyle().set("align-items", "center");


        H4 he=new H4("ACCOUNT");
        H2 details=new H2(Integer.toString(employeeService.getTotalEmployees(currentUser)));
        container.add(he,details);
        productdiv.add(container);


        return productdiv;
    }

}
