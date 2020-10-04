package com.sdgcrm.application.views.asset;

import com.sdgcrm.application.data.entity.Asset;
import com.sdgcrm.application.data.entity.Employee;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.AssetService;
import com.sdgcrm.application.data.service.EmployeeService;
import com.sdgcrm.application.data.service.UserService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDate;


public class AssetForm extends FormLayout {

    AssetService assetService;
    UserService userService;
    EmployeeService employeeService;
    private Asset asset;
    User currentUser;



    ComboBox<String> categorytf = new ComboBox<>("Equipment Category");

    TextField nametf = new TextField("Equipment Name");
    DatePicker purchaseDatetf;

    ComboBox<String> maintenceScheduletf = new ComboBox<>("Maintenance Schedule");

    NumberField quantitytf = new NumberField("Quantity");

    ComboBox<String> healthStatustf = new ComboBox<>("Health Status");


    TextArea notestf = new TextArea("Notes");

    ComboBox<String> purchasedBytf = new ComboBox<>("Purchased By: Select / Enter Name");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Asset> binder = new Binder<>();

    public AssetForm(User currentUser,  AssetService assetService,   UserService userService, EmployeeService employeeService) {
        this.currentUser = currentUser;
        this.assetService= assetService;
        this.userService= userService;
        this.employeeService= employeeService;
        purchaseDatetf  = new DatePicker("Purchase Date");



        binder.forField(categorytf).bind(Asset::getCategory, Asset::setCategory);
        binder.forField(nametf).bind(Asset::getName,Asset::setName);
        binder.forField(purchaseDatetf).withConverter(new LocalDateToDateConverter()).bind(Asset::getPurchaseDate,Asset::setPurchaseDate);
        maintenceScheduletf.setItems(assetService.getMaintenanceSchedule());
        maintenceScheduletf.setAllowCustomValue(false);

        categorytf.setItems(assetService.getAssetCategories());
        categorytf.setAllowCustomValue(false);

        healthStatustf.setItems(assetService.getAssetHealthCategories());
        healthStatustf.setAllowCustomValue(false);

        purchasedBytf.setItems(employeeService.findAll("", currentUser).stream().map(Employee::getName));


        binder.forField(maintenceScheduletf).bind(Asset::getMaintenceSchedule,Asset::setMaintenceSchedule);
        binder.forField(quantitytf).bind(Asset::getQuantity,Asset::setQuantity);
        binder.forField(healthStatustf).bind(Asset::getHealthStatus,Asset::setHealthStatus);
        binder.forField(purchasedBytf).bind(Asset::getPurchasedBy,Asset::setPurchasedBy);
        binder.forField(notestf).bind(Asset::getNotes,Asset::setNotes);

        purchaseDatetf.setClearButtonVisible(true);
        purchaseDatetf.setValue(LocalDate.now());

        addClassName("customer-form");
        getStyle().set("margin","10px");
        add(nametf,
                categorytf,
                purchaseDatetf,
                maintenceScheduletf,
                quantitytf,
                healthStatustf,
                purchasedBytf,
                notestf,
                createButtonsLayout());







        purchasedBytf.setLabel("Purchased By");
        purchasedBytf.setPlaceholder("Enter Name...");

    }

    private HorizontalLayout createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new AssetForm.DeleteEvent(this, asset)));
        close.addClickListener(event -> fireEvent(new AssetForm.CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);

    }

    private void validateAndSave() {
        try {

            asset.setCompany(currentUser.getCompanyProfile());
            binder.writeBean(asset);
            fireEvent(new AssetForm.SaveEvent(this, asset));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }



    public static abstract class AssetFormEvent extends ComponentEvent<AssetForm> {
        private final Asset asset;

        protected AssetFormEvent(AssetForm source, Asset asset) {
            super(source, false);
            this.asset = asset;
        }

        public Asset getAsset() {
            return asset;
        }
    }

    public static class SaveEvent extends AssetFormEvent{
        SaveEvent(AssetForm source, Asset asset) {
            super(source, asset);
        }
    }

    public static class DeleteEvent extends AssetFormEvent {
        DeleteEvent(AssetForm source, Asset asset) {
            super(source, asset);
        }

    }

    public static class CloseEvent extends AssetFormEvent {
        CloseEvent(AssetForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }




    public void setAsset(Asset asset) {
        this.asset = asset;
        binder.readBean(asset);
    }
}
