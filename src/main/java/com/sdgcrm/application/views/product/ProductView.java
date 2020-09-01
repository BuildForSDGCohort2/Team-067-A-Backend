package com.sdgcrm.application.views.product;

import com.sdgcrm.application.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "Product", layout = MainView.class)
@PageTitle("Product")
@CssImport("./styles/views/about/about-view.css")
public class ProductView  extends Div {

    public ProductView() {
        setId("about-view");
        add(new Label("Content placeholder"));
    }
}
