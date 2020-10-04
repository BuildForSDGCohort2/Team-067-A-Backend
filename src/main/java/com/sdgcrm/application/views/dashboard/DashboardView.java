package com.sdgcrm.application.views.dashboard;

import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.CustomerService;
import com.sdgcrm.application.data.service.EmployeeService;
import com.sdgcrm.application.data.service.ProductService;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.security.SecurityUtils;
import com.sdgcrm.application.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "dashboard", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Dashboard")
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
        layout.getStyle().set("margin-bottom","-21px");
        layout.setWidth("100%");
        layout.setPadding(true);
        layout.setBoxSizing(BoxSizing.BORDER_BOX);
       // layout.getStyle().set("border", "1px solid #9E9E9E");

        Div productsTxt = createProductsHeader();
         productsTxt.setWidth("50%");
        productsTxt.getStyle().set("background-color","#a20025");


        Div staffsTxt = createStaffsHeader();
        staffsTxt.setWidth("50%");
        staffsTxt.getStyle().set("background-color","#e51400");

        layout.add(productsTxt, staffsTxt);




        HorizontalLayout layout2 = new HorizontalLayout();
        layout2.getStyle().set("margin-bottom","-21px");
        layout2.setWidth("100%");
        layout2.setPadding(true);
        layout2.setBoxSizing(BoxSizing.BORDER_BOX);
        // layout.getStyle().set("border", "1px solid #9E9E9E");


        Div customerTxt = createCustomerHeader();

        customerTxt.setWidth("50%");
        customerTxt.getStyle().set("background-color","#825a2c");


        Div assetsTxt = createAssetsHeader();

        assetsTxt.setWidth("50%");
        assetsTxt.getStyle().set("background-color","#76608a");
         layout2.add(customerTxt, assetsTxt);


        Div component2 = createOrdersHeader();
        component2.setWidth("50%");
        component2.getStyle().set("background-color","#008a00");


        Div component3 = createAccountHeader();
        component3.setWidth("50%");
        component3.getStyle().set("background-color","#6a00ff");


        Div component4 = createInventoryHeader();
        component4.setWidth("50%");
        component4.getStyle().set("background-color","#d80073");


        Div component5 = createDealsHeader();
        component5.setWidth("50%");
        component5.getStyle().set("background-color","#f0a30a");





        HorizontalLayout layout3 = new HorizontalLayout();
        layout3.getStyle().set("margin-bottom","-21px");

        layout3.setWidth("100%");
        layout3.setPadding(true);
        layout3.setBoxSizing(BoxSizing.BORDER_BOX);
        layout3.add(component2,component3);

        HorizontalLayout layout4 = new HorizontalLayout();
        layout4.getStyle().set("margin-bottom","-21px");

        layout4.setWidth("100%");
        layout4.setPadding(true);
        layout4.setBoxSizing(BoxSizing.BORDER_BOX);
        layout4.add(component4,component5);

        add(layout,layout2, layout3, layout4);







    }


    private Div createStaffsHeader() {
        Div productdiv= new Div();

        VerticalLayout container= new VerticalLayout();
        container.getStyle().set("align-items", "center");


        H4 he=new H4("STAFF");
        H2 details=new H2(Integer.toString(employeeService.getTotalEmployees(currentUser)));
        container.add(details,he);
        productdiv.add(container);


        return productdiv;

    }

    private Div createCustomerHeader() {
        Div productdiv= new Div();
        VerticalLayout container= new VerticalLayout();
        container.getStyle().set("align-items", "center");


        H4 he=new H4("CUSTOMER");
        H2 details=new H2(Integer.toString(customerService.getTotalCustomers(currentUser)));
        container.add(details,he);
        productdiv.add(container);

        return productdiv;

    }

    private Div createProductsHeader() {
        Div productdiv= new Div();
        VerticalLayout container= new VerticalLayout();
        container.getStyle().set("align-items", "center");


        H4 he=new H4("PRODUCT");
        H2 details=new H2(Integer.toString(productService.getTotalProducts(currentUser)));
        container.add(details,he);
        productdiv.add(container);

        return productdiv;


    }

    private Div createAccountHeader() {
        Div productdiv= new Div();

        VerticalLayout container= new VerticalLayout();
        container.getStyle().set("align-items", "center");


        H4 he=new H4("ASSET");
        H2 details=new H2(Integer.toString(employeeService.getTotalEmployees(currentUser)));
        container.add(details,he);
        productdiv.add(container);


        return productdiv;
    }

    private Div createOrdersHeader() {
        Div productdiv= new Div();

        VerticalLayout container= new VerticalLayout();
        container.getStyle().set("align-items", "center");


        H4 he=new H4("ORDER");
        H2 details=new H2(Integer.toString(employeeService.getTotalEmployees(currentUser)));
        container.add(details,he);
        productdiv.add(container);


        return productdiv;
    }

    private Div createAssetsHeader() {
        Div productdiv= new Div();

        VerticalLayout container= new VerticalLayout();
        container.getStyle().set("align-items", "center");


        H4 he=new H4("ACCOUNT");
        H2 details=new H2(Integer.toString(employeeService.getTotalEmployees(currentUser)));
        container.add(details,he);
        productdiv.add(container);


        return productdiv;
    }

    private Div createInventoryHeader() {
        Div productdiv= new Div();

        VerticalLayout container= new VerticalLayout();
        container.getStyle().set("align-items", "center");


        H4 he=new H4("INVENTORY");
        H2 details=new H2(Integer.toString(employeeService.getTotalEmployees(currentUser)));
        container.add(details,he);
        productdiv.add(container);


        return productdiv;
    }

    private Div createDealsHeader() {
        Div productdiv= new Div();

        VerticalLayout container= new VerticalLayout();
        container.getStyle().set("align-items", "center");


        H4 he=new H4("DEAL");
        H2 details=new H2(Integer.toString(employeeService.getTotalEmployees(currentUser)));
        container.add(details, he);
        productdiv.add(container);


        return productdiv;
    }



}
