package com.sdgcrm.application.views.product;

import com.sdgcrm.application.data.entity.Customer;
import com.sdgcrm.application.data.entity.Product;
import com.sdgcrm.application.data.entity.User;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.shared.Registration;

import java.math.BigDecimal;

public class ProductForm extends FormLayout {

    private Product product;

    User currentUser;

    TextField Nametf = new TextField("Name");
    TextArea descriptiontf = new TextArea("Description");
    TextField typetf = new TextField("Product Type");
    TextArea notestf = new TextArea("Notes");
    NumberField pricetf = new NumberField("Price");


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Product> binder = new Binder<>();
    public ProductForm(User currentUser) {
        this.currentUser= currentUser;
      //  binder.bindInstanceFields(this);

        pricetf.setPrefixComponent(new Span("₦"));

        binder.forField(Nametf).bind(Product::getName,Product::setName);
        binder.forField(descriptiontf).bind(Product::getDescription,Product::setDescription);
        binder.forField(typetf).bind(Product::getType,Product::setType);

        binder.forField(pricetf).bind(Product::getPrice, Product::setPrice);

        binder.forField(notestf).bind(Product::getNotes,Product::setNotes);


        addClassName("customer-form");
        getStyle().set("margin","10px");



        add(Nametf,
                typetf,
                pricetf,
                descriptiontf,
                notestf,
                createButtonsLayout());

    }

    public void setProduct(Product product) {

        this.product = product;
        binder.readBean(product);

    }

    private HorizontalLayout createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, product)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);

    }

    private void validateAndSave() {
        try {

         product.setCompany(currentUser);
            binder.writeBean(product);
            fireEvent(new SaveEvent(this, product));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    // Events
    public static abstract class ProductFormEvent extends ComponentEvent<ProductForm> {
        private Product product;

        protected ProductFormEvent(ProductForm source, Product product) {
            super(source, false);
            this.product = product;
        }

        public Product getProduct() {
            return product;
        }
    }

    public static class SaveEvent extends ProductFormEvent {
        SaveEvent(ProductForm source, Product product) {
            super(source, product);
        }
    }

    public static class DeleteEvent extends ProductFormEvent {
        DeleteEvent(ProductForm source, Product product) {
            super(source, product);
        }

    }

    public static class CloseEvent extends ProductFormEvent {
        CloseEvent(ProductForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }




}
