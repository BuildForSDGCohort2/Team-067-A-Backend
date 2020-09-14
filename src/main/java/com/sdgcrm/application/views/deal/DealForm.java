package com.sdgcrm.application.views.deal;

import com.sdgcrm.application.data.entity.Deal;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.DealService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.shared.Registration;

import java.util.function.DoubleToIntFunction;

public class DealForm extends FormLayout {


    private Deal deal;

    User currentUser;



    TextField productNametf = new TextField("Product Name");
    NumberField pricetf = new NumberField("Price");
    TextField companyNametf = new TextField("Customer / Company Name");
    NumberField quantitytf = new NumberField("Quantity");

    ComboBox<String> statusTF = new ComboBox<>("Status");

    TextArea notesTF = new TextArea("Notes");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Deal> binder = new Binder<Deal>();
    public DealForm(User currentUser, DealService dealService) {
        this.currentUser= currentUser;
        //  binder.bindInstanceFields(this);

        quantitytf.setHasControls(true);

        pricetf.setPrefixComponent(new Span("₦"));

        binder.forField(productNametf).bind(Deal::getProductName,Deal::setProductName);
        binder.forField(pricetf).bind(Deal::getPrice,Deal::setPrice);
        binder.forField(quantitytf).bind(Deal::getQuantity,Deal::setQuantity);
        binder.forField(companyNametf).bind(Deal::getCompanyName,Deal::setCompanyName);

        statusTF.setItems(dealService.getDealStatus());

        binder.forField(statusTF).bind(Deal::getStatus,Deal::setStatus);


        binder.forField(notesTF).bind(Deal::getNotes,Deal::setNotes);


        addClassName("customer-form");
        getStyle().set("margin","10px");
        add(productNametf,
                pricetf,
                companyNametf,
                quantitytf,
                statusTF,
                notesTF,
                createButtonsLayout());

    }

    public void setDeal(Deal deal) {
        this.deal = deal;
        binder.readBean(deal);
    }

    private HorizontalLayout createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DealForm.DeleteEvent(this, deal)));
        close.addClickListener(event -> fireEvent(new DealForm.CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);

    }

    private void validateAndSave() {
        try {

           // deal.setCompany(currentUser);
            binder.writeBean(deal);
            fireEvent(new DealForm.SaveEvent(this, deal));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    // Events
    public static abstract class DealFormEvent extends ComponentEvent<DealForm> {
        private Deal deal;

        protected DealFormEvent(DealForm source, Deal deal) {
            super(source, false);
            this.deal = deal;
        }

        public Deal getCustomer() {
            return deal;
        }
    }

    public static class SaveEvent extends DealForm.DealFormEvent {
        SaveEvent(DealForm source, Deal deal) {
            super(source, deal);
        }
    }

    public static class DeleteEvent extends DealForm.DealFormEvent {
        DeleteEvent(DealForm source, Deal deal) {
            super(source, deal);
        }

    }

    public static class CloseEvent extends DealForm.DealFormEvent {
        CloseEvent(DealForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}
