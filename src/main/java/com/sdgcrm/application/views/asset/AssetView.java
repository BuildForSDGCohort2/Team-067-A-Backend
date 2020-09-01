package com.sdgcrm.application.views.asset;

import com.sdgcrm.application.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "Asset", layout = MainView.class)
@PageTitle("Asset")
@CssImport("./styles/views/about/about-view.css")
public class AssetView extends Div {

    public AssetView() {
        setId("abo-view");

        add(getToolbar());


    }

    private Tabs getToolbar() {

        Tab tab1 = new Tab("Assets");
        Tab tab2 = new Tab("Maintenance");
        Tab tab3 = new Tab("Tab three");
        Tabs tabs = new Tabs(tab1, tab2, tab3);
        tabs.setFlexGrowForEnclosedTabs(1);




        return tabs;
    }

}
