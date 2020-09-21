package com.sdgcrm.application.views.invoice;

import com.sdgcrm.application.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "invoice", layout = MainView.class)
@PageTitle("Invoice")
@CssImport("./styles/shared-styles.css")
public class InvoiceView extends Div {

    public InvoiceView() {

    }
}
