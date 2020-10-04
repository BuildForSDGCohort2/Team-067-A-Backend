package com.sdgcrm.application.views.creditor;

import com.sdgcrm.application.data.entity.Credit;
import com.sdgcrm.application.data.entity.CreditPayment;
import com.sdgcrm.application.data.entity.User;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDate;
import java.util.Date;

public class CreditorForm  extends FormLayout {
    private Credit credit;


    User currentUser;



    TextField creditorNametf = new TextField("Creditor Name ");
    NumberField totalAmounttf = new NumberField("Total Amount");
    DatePicker dateDuetf = new DatePicker("Date Due");

    DatePicker repaymentDatetf = new DatePicker("Repayment Date");

    NumberField repaymentAmounttf = new NumberField("Repayment Amount");


    private Checkbox addCreditBox;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Grid<CreditPayment> grid= new Grid<>(CreditPayment.class);
    Binder<Credit> binder = new Binder<>();

    Binder<CreditPayment> binder2 = new Binder<>();
    public CreditorForm(User currentUser) {
        this.currentUser = currentUser;


        binder.forField(creditorNametf).bind(Credit::getCreditorName,Credit::setCreditorName);
        binder.forField(totalAmounttf).bind(Credit::getTotalAmount,Credit::setTotalAmount);
        binder.forField(dateDuetf).withConverter(new LocalDateToDateConverter()).bind(Credit::getDateDue,Credit::setDateDue);

        binder2.forField(repaymentDatetf).withConverter(new LocalDateToDateConverter()).bind(CreditPayment::getRepaymentDate, CreditPayment::setRepaymentDate);
        binder2.forField(repaymentAmounttf).bind(CreditPayment::getRepaymentAmount, CreditPayment::setRepaymentAmount);

        addCreditBox = new Checkbox("Add Credit Payment?");

        dateDuetf.setClearButtonVisible(true);
        dateDuetf.setValue(LocalDate.now());

        addClassName("list-view");
        setSizeFull();


        configureGrid();

        repaymentAmounttf.setVisible(false);
        repaymentDatetf.setVisible(false);

        addClassName("customer-form");
        getStyle().set("margin","10px");
        add(creditorNametf,
                totalAmounttf,
                dateDuetf,
                grid,
                addCreditBox,

                repaymentAmounttf,
                repaymentDatetf,
                createButtonsLayout());


        addCreditBox.addValueChangeListener(e -> {


            repaymentAmounttf.setVisible(addCreditBox.getValue());
            repaymentDatetf.setVisible(addCreditBox.getValue());
            // Additionally, remove the input if the user decides not to allow emails. This
            // way any input that ends up hidden on the page won't end up in the bean when
            // saved.
            if (!addCreditBox.getValue()) {

            }
        });

    }

    private void configureGrid() {

        grid.setSizeFull();
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    private HorizontalLayout createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new CreditorForm.DeleteEvent(this, credit)));
        close.addClickListener(event -> fireEvent(new CreditorForm.CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);

    }

    private void validateAndSave() {
        try {

            credit.getClient().add(new CreditPayment(34.9, credit));
            binder.writeBean(credit);

            fireEvent(new CreditorForm.SaveEvent(this, credit));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
        binder.readBean(credit);
    }

    public Binder<Credit> getBinder() {
        return binder;
    }

    public void setBinder(Binder<Credit> binder) {
        this.binder = binder;
    }

    // Events
    public static abstract class CreditorFormEvent extends ComponentEvent<CreditorForm> {
        private final Credit credit;


        protected CreditorFormEvent(CreditorForm source, Credit credit) {
            super(source, false);
            this.credit = credit;
        }
        public Credit getCredit() {
            return credit;
        }
    }

    public static class SaveEvent extends CreditorForm.CreditorFormEvent {
        SaveEvent(CreditorForm source, Credit credit) {
            super(source, credit);
        }
    }

    public static class DeleteEvent extends CreditorForm.CreditorFormEvent {
        DeleteEvent(CreditorForm source, Credit credit) {
            super(source, credit);
        }

    }

    public static class CloseEvent extends CreditorForm.CreditorFormEvent {
        CloseEvent(CreditorForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
