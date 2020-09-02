package com.sdgcrm.application.views.main;

import java.util.Arrays;
import java.util.Optional;

import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.security.SecurityUtils;
import com.sdgcrm.application.views.asset.AssetView;
import com.sdgcrm.application.views.customer.CustomerView;
import com.sdgcrm.application.views.deal.DealView;
import com.sdgcrm.application.views.employee.EmployeeView;
import com.sdgcrm.application.views.product.ProductView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import com.sdgcrm.application.views.main.MainView;
import com.sdgcrm.application.views.hello.HelloView;
import com.sdgcrm.application.views.about.AboutView;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The main view is a top-level placeholder for other views.
 */

@PWA(name = "SDGCRM", shortName = "SDGCRM",  enableInstallPrompt = true)
@CssImport("./styles/views/main/main-view.css")
@Theme(value = Lumo.class, variant =Lumo.DARK)
public class MainView extends AppLayout {

    private final Tabs menu;
    private H1 viewTitle;
    private H1 userTitle;

    UserService userservice;
    User currentUser;


    public MainView( @Autowired
                             UserService userservice) {
        this.userservice= userservice;
        currentUser= userservice.findByEmail(SecurityUtils.getLoggedinUsername());
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        menu = createMenu();




        addToDrawer(createDrawerContent(menu));
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        userTitle= new H1(currentUser.getFirstname() +", "+ currentUser.getLastname());
        layout.add(viewTitle);
        userTitle.getStyle().set("margin-right", "10px");

        Image avatar=  new Image("images/user.svg", "Avatar");
        layout.add(avatar, userTitle);

        ContextMenu contextMenu = new ContextMenu(userTitle);
        contextMenu.setOpenOnClick(true);
        contextMenu.addItem("Settings",
                e -> getUI().ifPresent(ui -> ui.navigate("Setting")));
        Anchor li= new Anchor("/logout", "Logout");
        li.setClassName("centered-content");
        contextMenu.add(li);

        return layout;
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", "SDGCRM logo"));

        logoLayout.add(new H1(currentUser.getCompanyName().toUpperCase()));
        layout.add(logoLayout, menu);
        return layout;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {


        RouterLink[] links = new RouterLink[] {
                new RouterLink("Product", ProductView.class),
                new RouterLink("Employee Management", EmployeeView.class),
                new RouterLink("Customer Management", CustomerView.class),
                new RouterLink("Order/Deal Management", DealView.class),
                new RouterLink("Asset Management", AssetView.class),
                new RouterLink("Account Management", HelloView.class),
            new RouterLink("About", AboutView.class),
        };
        return Arrays.stream(links).map(MainView::createTab).toArray(Tab[]::new);
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.add(content);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        updateChrome();
    }

    private void updateChrome() {
        getTabWithCurrentRoute().ifPresent(menu::setSelectedTab);
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabWithCurrentRoute() {
        String currentRoute = RouteConfiguration.forSessionScope()
                .getUrl(getContent().getClass());
        return menu.getChildren().filter(tab -> hasLink(tab, currentRoute))
                .findFirst().map(Tab.class::cast);
    }

    private boolean hasLink(Component tab, String currentRoute) {
        return tab.getChildren().filter(RouterLink.class::isInstance)
                .map(RouterLink.class::cast).map(RouterLink::getHref)
                .anyMatch(currentRoute::equals);
    }

    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }
}
