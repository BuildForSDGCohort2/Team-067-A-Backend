package com.sdgcrm.application.views.deal;

import com.sdgcrm.application.data.entity.Deal;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.DealService;
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

@Route(value = "deal", layout = MainView.class)
@PageTitle("Deal")
@CssImport("./styles/views/about/about-view.css")
public class DealView extends Div {


    DealService dealService;
    UserService userService;
    User currentUser;

    DealForm form;

    Grid<Deal> grid= new Grid<>(Deal.class);
    private TextField filterText = new TextField();

    public DealView(@Autowired DealService dealService, @Autowired UserService userService) {
        this.dealService=dealService;
        this.userService= userService;


        addClassName("list-view");
        setSizeFull();

        configureGrid();

        this.currentUser= userService.findByEmail(SecurityUtils.getLoggedinUsername());

        form = new DealForm(currentUser, dealService);
        form.addListener(DealForm.SaveEvent.class, this::saveDeal);
        form.addListener(DealForm.DeleteEvent.class, this::deleteDeal);
        form.addListener(DealForm.CloseEvent.class, e -> closeEditor());


        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
    }

    private void saveDeal(DealForm.SaveEvent event) {
        dealService.saveDeal(event.getCustomer());
        updateList();
        closeEditor();
    }

    private  void deleteDeal(DealForm.DeleteEvent event) {
        dealService.delete(event.getCustomer());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setDeal(null);
        form.setVisible(false);
        removeClassName("editing");
    }



    private void configureGrid() {
        grid.addClassName("customer-grid");
        grid.setSizeFull();
      //  grid.removeColumnByKey("company");
        grid.addSelectionListener(e -> closeEditor());


        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editDeal(event.getValue()));


    }

    private void editDeal(Deal deal) {
        if (deal == null) {
            closeEditor();
        } else {
            form.setDeal(deal);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateList(){
        grid.setItems(dealService.findAll());

    }



    private void addFormItem(Div wrapper, FormLayout formLayout, AbstractField field, String fieldName) {
        formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Search Deals...");
        Icon icon = VaadinIcon.SEARCH.create();
        filterText.setPrefixComponent(icon);
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addCustomerButton = new Button("New Deal/Order");


        addCustomerButton.addClickListener(click -> addCustomer());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCustomerButton);
        toolbar.addClassName("toolbar");
        toolbar.getStyle().set("margin-left", "10px");
        toolbar.getStyle().set("margin-top", "10px");


        return toolbar;
    }

    private void addCustomer() {
        grid.asSingleSelect().clear();
        editDeal(new Deal());
    }

}
