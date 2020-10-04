package com.sdgcrm.application.views.employee;

import com.sdgcrm.application.data.entity.Employee;
import com.sdgcrm.application.data.entity.User;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDate;

public class EmployeeForm extends FormLayout {

    User currentUser;

    private Employee employee;


    TextField  nametf = new TextField("Full Name");
    EmailField emailtf = new EmailField("Email");
    TextField positiontf = new TextField("Position");
    TextField phonetf = new TextField("Phone");

    DatePicker dateHiredtf= new DatePicker("Hire date");
    DatePicker dateOfBirthtf= new DatePicker("Date of Birth") ;
    NumberField salaryTF = new NumberField("Salary");
    TextField sexTF = new TextField("Sex");
    TextField addressTF = new TextField("Address");
    TextField cityTf = new TextField("City");
    TextField stateTF = new TextField("State");



    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");


    Binder<Employee> binder = new Binder<>();
    public EmployeeForm(User currentUser) {
        this.currentUser= currentUser;

        //  binder.bindInstanceFields(this);

        binder.forField(nametf).bind(Employee::getName,Employee::setName);
        binder.forField(emailtf).bind(Employee::getEmail,Employee::setEmail);
        binder.forField(positiontf).bind(Employee::getPosition,Employee::setPosition);
        binder.forField(dateHiredtf).withConverter(new LocalDateToDateConverter()).bind(Employee::getDateHired, Employee::setDateHired);

        binder.forField(dateOfBirthtf).withConverter(new LocalDateToDateConverter()).bind(Employee::getDateOfBirth, Employee::setDateOfBirth);
        binder.forField(salaryTF).bind(Employee::getSalary, Employee::setSalary);
        binder.forField(sexTF).bind(Employee::getSex, Employee::setSex);
        binder.forField(addressTF).bind(Employee::getCity, Employee::setCity);
        binder.forField(cityTf).bind(Employee::getCity, Employee::setCity);
        binder.forField(stateTF).bind(Employee::getState, Employee::setState);


        dateHiredtf.setClearButtonVisible(true);
        dateHiredtf.setValue(LocalDate.now());

        dateOfBirthtf.setClearButtonVisible(true);
        dateOfBirthtf.setValue(LocalDate.now());






        binder.forField(phonetf).withConverter(new StringToLongConverter("Invalid Phone Number")).bind(Employee::getPhone,Employee::setPhone);
        addClassName("customer-form");
        getStyle().set("margin","10px");
        add(nametf,
                emailtf,
                phonetf,
                positiontf,
                dateHiredtf,
                dateOfBirthtf,
                salaryTF,
                sexTF,
                addressTF,
                cityTf,
                stateTF,
                createButtonsLayout());

    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        binder.readBean(employee);
    }

    private HorizontalLayout createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new EmployeeForm.DeleteEvent(this, employee)));
        close.addClickListener(event -> fireEvent(new EmployeeForm.CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);

    }

    private void validateAndSave() {
        try {

            employee.setEmployer(currentUser);
            binder.writeBean(employee);
            fireEvent(new EmployeeForm.SaveEvent(this, employee));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class EmployeeFormEvent extends ComponentEvent<EmployeeForm> {
        private final Employee employee;


        protected EmployeeFormEvent(EmployeeForm source, Employee employee) {
            super(source, false);
            this.employee = employee;
        }
        public Employee getEmployee() {
            return employee;
        }
    }

    public static class SaveEvent extends EmployeeForm.EmployeeFormEvent {
        SaveEvent(EmployeeForm source, Employee employee) {
            super(source, employee);
        }
    }

    public static class DeleteEvent extends EmployeeForm.EmployeeFormEvent {
        DeleteEvent(EmployeeForm source, Employee employee) {
            super(source, employee);
        }

    }

    public static class CloseEvent extends EmployeeForm.EmployeeFormEvent {
        CloseEvent(EmployeeForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}
