package com.sdgcrm.application.views.deal;

import com.sdgcrm.application.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "deal", layout = MainView.class)
@PageTitle("Deal")
@CssImport("./styles/views/about/about-view.css")
public class DealView extends Div {


    public DealView() {
        setId("about-view");
        add(new Label("Content placeholder"));
    }
}
