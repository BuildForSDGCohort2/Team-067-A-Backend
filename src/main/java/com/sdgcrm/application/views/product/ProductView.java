package com.sdgcrm.application.views.product;

import com.sdgcrm.application.data.entity.Product;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.ProductService;
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

@Route(value = "Product", layout = MainView.class)
@PageTitle("Product")
@CssImport("./styles/views/about/about-view.css")
public class ProductView  extends Div {




    ProductService productService;
    UserService userService;
    User currentUser;

    ProductForm form;

    Grid<Product> grid= new Grid<>(Product.class);

    private TextField filterText = new TextField();

    public ProductView(@Autowired ProductService productService, @Autowired UserService userService) {
        this.productService= productService;
        this.userService= userService;

        addClassName("list-view");
        setSizeFull();

        configureGrid();

        this.currentUser= userService.findByEmail(SecurityUtils.getLoggedinUsername());

        form = new ProductForm(currentUser);
        form.addListener(ProductForm.SaveEvent.class, this::saveProduct);
        form.addListener(ProductForm.DeleteEvent.class, this::deleteProduct);
        form.addListener(ProductForm.CloseEvent.class, e -> closeEditor());


        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();

    }

    private void saveProduct(ProductForm.SaveEvent event) {
        productService.saveProduct(event.getProduct());
        updateList();
        closeEditor();
    }

    private  void deleteProduct(ProductForm.DeleteEvent event) {
        productService.delete(event.getProduct());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setProduct(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void configureGrid() {
        grid.addClassName("customer-grid");
        grid.setSizeFull();
      excludeColumns();

        grid.addSelectionListener(e -> closeEditor());


        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editProduct(event.getValue()));


    }

    public void excludeColumns() {
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("company");
        grid.removeColumnByKey("createdBy");
        grid.removeColumnByKey("createdDate");
        grid.removeColumnByKey("lastModifiedBy");
        grid.removeColumnByKey("lastModifiedDate");
    }

    private void editProduct(Product product) {
        if (product == null) {
            closeEditor();
        } else {
            form.setProduct(product);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateList() {

        grid.setItems(productService.findAll(filterText.getValue(), currentUser));

    }
    private HorizontalLayout getToolbar() {

        filterText.setPlaceholder("Search products ...");
        Icon icon = VaadinIcon.SEARCH.create();
        filterText.setPrefixComponent(icon);
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addProductButton = new Button("New product");


        addProductButton.addClickListener(click -> addProduct());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addProductButton);
        toolbar.addClassName("toolbar");
        toolbar.getStyle().set("margin-left", "10px");
        toolbar.getStyle().set("margin-top", "10px");


        return toolbar;
    }

    private void addProduct() {
        editProduct(new Product());
        grid.asSingleSelect().clear();

    }








}
