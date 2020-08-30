package com.sdgcrm.application.views.customer;

import com.sdgcrm.application.data.entity.Customer;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.security.SecurityUtils;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Email;

public class CustomerForm extends FormLayout {

    private Customer customer;

    User currentUser;

    TextField firstNametf = new TextField("First Name");
    TextField lastNametf = new TextField("Last Name");
    EmailField emailtf = new EmailField("Email");
    TextField phonetf = new TextField("Phone");
    TextField locationtf = new TextField("Location");
    TextField companyNametf = new TextField("Company Name");
    TextField notestf = new TextField("Notes");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Customer> binder = new Binder<Customer>();
    public CustomerForm(User currentUser) {
        this.currentUser= currentUser;
      //  binder.bindInstanceFields(this);

        binder.forField(firstNametf).bind(Customer::getFirstName,Customer::setFirstName);
        binder.forField(lastNametf).bind(Customer::getLastName,Customer::setLastName);
        binder.forField(emailtf).bind(Customer::getEmail,Customer::setEmail);
        binder.forField(locationtf).bind(Customer::getLocation,Customer::setLocation);
        binder.forField(companyNametf).bind(Customer::getCompanyName,Customer::setCompanyName);



        binder.forField(phonetf).withConverter(new StringToLongConverter("Invalid Phone Number")).bind(Customer::getPhone,Customer::setPhone);
        binder.forField(notestf).bind(Customer::getNotes,Customer::setNotes);
        addClassName("customer-form");
        getStyle().set("margin","10px");
        add(firstNametf,
                lastNametf,
                emailtf,
                phonetf,
                companyNametf,
                locationtf,
                notestf,
                createButtonsLayout());

    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        binder.readBean(customer);
    }

    private HorizontalLayout createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, customer)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);

    }

    private void validateAndSave() {
        try {

            customer.setCompany(currentUser);
            binder.writeBean(customer);
            fireEvent(new SaveEvent(this, customer));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    // Events
    public static abstract class CustomerFormEvent extends ComponentEvent<CustomerForm> {
        private Customer customer;

        protected CustomerFormEvent(CustomerForm source, Customer customer) {
            super(source, false);
            this.customer = customer;
        }

        public Customer getCustomer() {
            return customer;
        }
    }

    public static class SaveEvent extends CustomerFormEvent {
        SaveEvent(CustomerForm source, Customer customer) {
            super(source, customer);
        }
    }

    public static class DeleteEvent extends CustomerFormEvent {
        DeleteEvent(CustomerForm source, Customer customer) {
            super(source, customer);
        }

    }

    public static class CloseEvent extends CustomerFormEvent {
        CloseEvent(CustomerForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}
