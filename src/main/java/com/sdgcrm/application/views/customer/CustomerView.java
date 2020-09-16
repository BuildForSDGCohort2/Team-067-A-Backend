package com.sdgcrm.application.views.customer;

import com.sdgcrm.application.data.entity.Customer;
import com.sdgcrm.application.data.entity.Person;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.CustomerService;
import com.sdgcrm.application.data.service.PersonService;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.security.SecurityUtils;
import com.sdgcrm.application.views.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

@Route(value = "customer", layout = MainView.class)
@PageTitle("Customer")
@CssImport("./styles/shared-styles.css")
public class CustomerView extends Div {


    CustomerService customerService;
    UserService userService;
    User currentUser;

    CustomerForm form;

    Grid<Customer> grid= new Grid<>(Customer.class);
    private TextField filterText = new TextField();

    public CustomerView(@Autowired CustomerService customerService, @Autowired UserService userService) {
        this.customerService=customerService;
        this.userService= userService;
       

        addClassName("list-view");
        setSizeFull();

        configureGrid();

        this.currentUser= userService.findByEmail(SecurityUtils.getLoggedinUsername());

        form = new CustomerForm(currentUser);
        form.addListener(CustomerForm.SaveEvent.class, this::saveCustomer);
        form.addListener(CustomerForm.DeleteEvent.class, this::deleteCustomer);
        form.addListener(CustomerForm.CloseEvent.class, e -> closeEditor());


        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
    }

    private void saveCustomer(CustomerForm.SaveEvent event) {
        customerService.saveCustomer(event.getCustomer());
        updateList();
        closeEditor();
    }

    private  void deleteCustomer(CustomerForm.DeleteEvent event) {
        customerService.delete(event.getCustomer());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setCustomer(null);
        form.setVisible(false);
        removeClassName("editing");
    }



    private void configureGrid() {
        grid.addClassName("customer-grid");
        grid.setSizeFull();
        excludeColumns();
        grid.addSelectionListener(e -> closeEditor());


        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editCustomer(event.getValue()));


    }

    public void excludeColumns() {
        grid.removeColumnByKey("company");
        grid.removeColumnByKey("createdBy");
        grid.removeColumnByKey("createdDate");
        grid.removeColumnByKey("lastModifiedBy");
        grid.removeColumnByKey("lastModifiedDate");
    }

    private void editCustomer(Customer customer) {
        if (customer == null) {
            closeEditor();
        } else {
            form.setCustomer(customer);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateList(){
        grid.setItems(customerService.findAll(filterText.getValue(), currentUser));

    }



    private void addFormItem(Div wrapper, FormLayout formLayout, AbstractField field, String fieldName) {
        formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Search Customers ...");
        Icon icon = VaadinIcon.SEARCH.create();
        filterText.setPrefixComponent(icon);
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addCustomerButton = new Button("New Customer");


        addCustomerButton.addClickListener(click -> addCustomer());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCustomerButton);
        toolbar.addClassName("toolbar");
        toolbar.getStyle().set("margin-left", "10px");
        toolbar.getStyle().set("margin-top", "10px");


        return toolbar;
    }

    private void addCustomer() {
        grid.asSingleSelect().clear();
        editCustomer(new Customer());
    }


}
