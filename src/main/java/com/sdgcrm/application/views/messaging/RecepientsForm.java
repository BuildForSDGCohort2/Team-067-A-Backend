package com.sdgcrm.application.views.messaging;

import com.sdgcrm.application.data.entity.Customer;
import com.sdgcrm.application.data.entity.Product;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.CustomerService;
import com.sdgcrm.application.views.product.ProductForm;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.stream.Collectors;

public class RecepientsForm  extends FormLayout {


    private Recepient recepient;
    ListBox<String> listBox = new ListBox<>();



    Binder<Recepient> binder = new Binder<>();
    Button close = new Button("Close");

    Button updateReceipents = new Button("Update Recepient");
    public RecepientsForm( CustomerService customerService, User currentuser) {

        addClassName("customer-form");
        Span df=new Span("Customer List");

        listBox.setItems(customerService.findAll("", currentuser)
        .stream().map(Customer::getEmail).collect(Collectors.toList()));


        add(df, listBox, createButtonsLayout());
    }




    public void setRecepient(Recepient recepient) {
        this.recepient = recepient;
        binder.readBean(recepient);
    }

    private HorizontalLayout createButtonsLayout() {
        updateReceipents.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        close.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addClickShortcut(Key.ESCAPE);
        updateReceipents.addClickShortcut(Key.ENTER);

        close.addClickListener(event -> fireEvent(new RecepientsForm.CloseEvent(this)));
        updateReceipents.addClickListener(event -> validateAndSave());

        return new HorizontalLayout(updateReceipents, close);

    }

    private void validateAndSave() {
        try {

            recepient.setRecepientsList(listBox.getValue());
            binder.writeBean(recepient);
            fireEvent(new RecepientsForm.SaveEvent(this, recepient));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class RecepientsFormEvent extends ComponentEvent<RecepientsForm> {
        private Recepient recepient;

        protected RecepientsFormEvent(RecepientsForm source, Recepient recepient) {
            super(source, false);
            this.recepient = recepient;
        }

        public Recepient getRecepient() {
            return recepient;
        }
    }



    public static class SaveEvent extends RecepientsForm.RecepientsFormEvent {
        SaveEvent(RecepientsForm source, Recepient recepient) {
            super(source, recepient);
        }
    }


    public static class CloseEvent extends RecepientsForm.RecepientsFormEvent {
        CloseEvent(RecepientsForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
