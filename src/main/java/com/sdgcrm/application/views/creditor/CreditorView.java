package com.sdgcrm.application.views.creditor;

import com.sdgcrm.application.data.entity.Credit;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.CreditService;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.security.SecurityUtils;
import com.sdgcrm.application.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
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


@Route(value = "credit", layout = MainView.class)
@PageTitle("Credit")
@CssImport("./styles/shared-styles.css")
public class CreditorView extends Div {

    CreditService creditService;
    UserService userService;
    User currentUser;

    CreditorForm form;

    Grid<Credit> grid= new Grid<>(Credit.class);
    private final TextField filterText = new TextField();
    public CreditorView(@Autowired CreditService creditService, @Autowired UserService userService) {

        this.creditService=creditService;
        this.userService= userService;

        this.currentUser= userService.findByEmail(SecurityUtils.getLoggedinUsername());


        addClassName("list-view");
        setSizeFull();

        configureGrid();

        form = new CreditorForm(currentUser);
        form.addListener(CreditorForm.SaveEvent.class, this::saveCredit);
        form.addListener(CreditorForm.DeleteEvent.class, this::deleteCredit);
        form.addListener(CreditorForm.CloseEvent.class, e -> closeEditor());



        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();

    }

    private void saveCredit(CreditorForm.SaveEvent event) {
        creditService.saveCredit(event.getCredit());
        updateList();
        closeEditor();
    }

    private void deleteCredit(CreditorForm.DeleteEvent event) {
        creditService.delete(event.getCredit());
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(creditService.findAll());
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Search credit...");
        Icon icon = VaadinIcon.SEARCH.create();
        filterText.setPrefixComponent(icon);
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addCustomerButton = new Button("New Creditor");


        addCustomerButton.addClickListener(click -> addCredit());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCustomerButton);
        toolbar.addClassName("toolbar");
        toolbar.getStyle().set("margin-left", "10px");
        toolbar.getStyle().set("margin-top", "10px");


        return toolbar;
    }


    private void configureGrid() {
        grid.addClassName("customer-grid");
        grid.setSizeFull();
        //  grid.removeColumnByKey("company");
       grid.addSelectionListener(e -> closeEditor());


        grid.getColumns().forEach(col -> col.setAutoWidth(true));
      grid.asSingleSelect().addValueChangeListener(event ->
              editCredit(event.getValue()));



    }

    private void closeEditor() {
        form.setCredit(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addCredit() {

        grid.asSingleSelect().clear();
        editCredit(new Credit());
    }

    private void editCredit(Credit credit) {
        if (credit == null) {
            closeEditor();
        } else {
            form.setCredit(credit);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}
