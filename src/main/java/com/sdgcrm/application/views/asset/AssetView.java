package com.sdgcrm.application.views.asset;

import com.sdgcrm.application.data.entity.Asset;
import com.sdgcrm.application.data.entity.Customer;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.AssetService;
import com.sdgcrm.application.data.service.CustomerService;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.security.SecurityUtils;
import com.sdgcrm.application.views.customer.CustomerForm;
import com.sdgcrm.application.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "Asset", layout = MainView.class)
@PageTitle("Asset")
@CssImport("./styles/shared-styles.css")
public class AssetView extends Div {


    AssetService assetService;
    UserService userService;
    User currentUser;

    AssetForm form;
    Grid<Asset> grid= new Grid<>(Asset.class);
    private TextField filterText = new TextField();

    public AssetView(@Autowired AssetService assetService, @Autowired UserService userService) {
        this.assetService= assetService;
        this.userService= userService;
        this.currentUser= userService.findByEmail(SecurityUtils.getLoggedinUsername());



        addClassName("list-view");
        setSizeFull();

        configureGrid();

        form = new AssetForm(currentUser);
        form.addListener(AssetForm.SaveEvent.class, this::saveAsset);
        form.addListener(AssetForm.DeleteEvent.class, this::deleteAsset);
        form.addListener(AssetForm.CloseEvent.class, e -> closeEditor());


        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getTabsbar(),getToolbar(), content);
        updateList();
        closeEditor();

    }

    private void configureGrid() {
        grid.addClassName("customer-grid");
        grid.setSizeFull();
        grid.addSelectionListener(e -> closeEditor());


        grid.getColumns().forEach(col -> col.setAutoWidth(true));
     grid.asSingleSelect().addValueChangeListener(event ->
                editAsset(event.getValue()));

    }

    private void editAsset(Asset asset) {
        if (asset == null) {
            closeEditor();
        } else {
            form.setAsset(asset);
            form.setVisible(true);
            addClassName("editing");
        }
    }
    private void updateList(){
        grid.setItems(assetService.findAll());

    }
    private Tabs getTabsbar() {

        Tab tab1 = new Tab("Assets");
        Tab tab2 = new Tab("Maintenance");
        Tabs tabs = new Tabs(tab1, tab2);
        tabs.setFlexGrowForEnclosedTabs(1);




        return tabs;
    }


    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Search Assets ...");
        Icon icon = VaadinIcon.SEARCH.create();
        filterText.setPrefixComponent(icon);
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addCustomerButton = new Button("New Asset");



        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCustomerButton);
        toolbar.addClassName("toolbar");
        toolbar.getStyle().set("margin-left", "10px");
        toolbar.getStyle().set("margin-top", "10px");
        addCustomerButton.addClickListener(click -> addAsset());


        return toolbar;
    }

    private void addAsset() {
        grid.asSingleSelect().clear();
        editAsset(new Asset());
    }


    private void saveAsset(AssetForm.SaveEvent event) {
        assetService.saveAsset(event.getAsset());
        updateList();
        closeEditor();
    }

    private  void deleteAsset(AssetForm.DeleteEvent event) {
        assetService.delete(event.getAsset());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setAsset(null);
        form.setVisible(false);
        removeClassName("editing");
    }



}
