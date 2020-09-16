package com.sdgcrm.application.views.employee;

import com.sdgcrm.application.data.entity.Employee;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.EmployeeService;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.security.SecurityUtils;
import com.sdgcrm.application.views.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "Employee", layout = MainView.class)
@PageTitle("Employee")
@CssImport("./styles/shared-styles.css")
public class EmployeeView  extends Div{
    EmployeeService employeeService;
    UserService userService;
    User currentUser;

    EmployeeForm form;

    Grid<Employee> grid= new Grid<>(Employee.class);
    private TextField filterText = new TextField();

    public EmployeeView(@Autowired EmployeeService employeeService, @Autowired UserService userService) {
        this.employeeService=employeeService;
        this.userService= userService;


        addClassName("list-view");
        setSizeFull();

        configureGrid();

        this.currentUser= userService.findByEmail(SecurityUtils.getLoggedinUsername());

        form = new EmployeeForm(currentUser);
        form.addListener(EmployeeForm.SaveEvent.class, this::saveCustomer);
        form.addListener(EmployeeForm.DeleteEvent.class, this::deleteCustomer);
        form.addListener(EmployeeForm.CloseEvent.class, e -> closeEditor());


        Div content = new Div(grid,  form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
    }

    private void saveCustomer(EmployeeForm.SaveEvent event) {
        employeeService.saveCustomer(event.getEmployee());
        updateList();
        closeEditor();
    }

    private  void deleteCustomer(EmployeeForm.DeleteEvent event) {
        employeeService.delete(event.getEmployee());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setEmployee(null);
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
                editEmployee(event.getValue()));


    }

    public void excludeColumns() {
        grid.removeColumnByKey("employer");
        grid.removeColumnByKey("createdBy");
        grid.removeColumnByKey("createdDate");
        grid.removeColumnByKey("lastModifiedBy");
        grid.removeColumnByKey("lastModifiedDate");
    }

    private void editEmployee(Employee employee) {
        if (employee == null) {
            closeEditor();
        } else {
            form.setEmployee(employee);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateList(){
        grid.setItems(employeeService.findAll(filterText.getValue(), currentUser));

    }



    private void addFormItem(Div wrapper, FormLayout formLayout, AbstractField field, String fieldName) {
        formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Search Staffs ...");
        Icon icon = VaadinIcon.SEARCH.create();
        filterText.setPrefixComponent(icon);
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addEmployeeButton = new Button("New Staffs");


        addEmployeeButton.addClickListener(click -> addEmployee());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addEmployeeButton);
        toolbar.addClassName("toolbar");
        toolbar.getStyle().set("margin-left", "10px");
        toolbar.getStyle().set("margin-top", "10px");


        return toolbar;
    }

    private void addEmployee() {
        System.out.println("hi");
        grid.asSingleSelect().clear();
        editEmployee(new Employee());
    }

}
