package com.sdgcrm.application.views.main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.security.SecurityUtils;
import com.sdgcrm.application.views.asset.AssetView;
import com.sdgcrm.application.views.creditor.CreditorView;
import com.sdgcrm.application.views.customer.CustomerView;
import com.sdgcrm.application.views.dashboard.DashboardView;
import com.sdgcrm.application.views.deal.DealView;
import com.sdgcrm.application.views.employee.EmployeeView;
import com.sdgcrm.application.views.messaging.MessagingView;
import com.sdgcrm.application.views.order.OrderView;
import com.sdgcrm.application.views.product.ProductView;
import com.sdgcrm.application.views.setting.OrganisationSettingView;
import com.sdgcrm.application.views.setting.ProfileSettingView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
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
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * The main view is a top-level placeholder for other views.
 */

@PWA(name = "SDGCRM", shortName = "SDGCRM")
@Theme(value = Lumo.class, variant=Lumo.DARK)
@CssImport("./styles/views/main/main-view.css")
public class MainView extends AppLayout {

    private final Tabs menu;
    private H1 viewTitle;


    UserService userservice;
    User currentUser;
    Image avatar;
    Image logo;

    public MainView( @Autowired
                             UserService userservice) {
        this.userservice= userservice;
        currentUser= userservice.findByEmail(SecurityUtils.getLoggedinUsername());

        avatar=  new Image();
        logo= new Image();
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        menu = createMenu();





        addToDrawer(createDrawerContent(menu));
    }

    private Component createHeaderContent() {
        H1 userTitle;
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



        if( currentUser.getProfileImg() != null  && currentUser.getProfileImg().length > 0){
            System.out.println("showing main logo");
             updateProfileimage(currentUser.getProfileImg());
        }else{
            System.out.println("showing default");
        avatar.setSrc("images/user.svg");

        }


       layout.add(avatar, userTitle);

        ContextMenu contextMenu = new ContextMenu(userTitle);
        contextMenu.setOpenOnClick(true);
        contextMenu.addItem("Settings",
                e -> getUI().ifPresent(ui -> ui.navigate("setting/profile")));
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

        if(currentUser.getCompanyProfile().getProfileImg() != null  && currentUser.getCompanyProfile().getProfileImg().length > 0){
            updateCompanyimage(currentUser.getCompanyProfile().getProfileImg());
        }else{
            logo.setSrc("images/logo.png");

        }
        logo.getStyle().set("border-radius","50%");

        logoLayout.add(logo);

        logoLayout.add(new H1(currentUser.getCompanyProfile().getName().toUpperCase()));
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

            Span producttxt= new Span("Product");
            Span dashboardtxt= new Span("Dashboard");
            Span employeetxt= new Span("Employee");
            Span customertxt= new Span("Customer");
            Span dealtxt= new Span("Deal");
            Span ordertxt= new Span("Order");
            Span  inventorytxt= new Span("Inventory");
            Span  messagetxt= new Span("  Message");
            Span  invoicetxt= new Span("Invoice");
            Span  companySettingtxt= new Span("Company Setting");
            Span  personnalSettingtxt= new Span("personnal Setting");
            Span  creditortxt= new Span("Creditor");

            Image dashboardimg= new Image();
            dashboardimg.setSrc("icons/appbar.app.svg");
            dashboardimg.addClassName("icon");

        Image productimg= new Image();
        productimg.setSrc("icons/appbar.box.svg");
        productimg.addClassName("icon");

        Image employeeimg= new Image();
        employeeimg.setSrc("icons/appbar.people.multiple.svg");
        employeeimg.addClassName("icon");

        Image customerimg= new Image();
        customerimg.setSrc("icons/appbar.group.svg");
        customerimg.addClassName("icon");

        Image orderimg= new Image();
        orderimg.setSrc("icons/appbar.shopping.svg");
        orderimg.addClassName("icon");

        Image dealimg= new Image();
        dealimg.setSrc("icons/appbar.thumbs.up.svg");
        dealimg.addClassName("icon");

        Image inventoryimg= new Image();
        inventoryimg.setSrc("icons/appbar.tools.svg");
        inventoryimg.addClassName("icon");

        Image messageimg= new Image();
        messageimg.setSrc("icons/appbar.message.send.svg");
        messageimg.addClassName("icon");


        Image invoiceimg= new Image();
        invoiceimg.setSrc("icons/appbar.book.list.svg");
        invoiceimg.addClassName("icon");

        RouterLink dashboardLink = new RouterLink("", DashboardView.class);
         dashboardLink.getElement().appendChild(dashboardimg.getElement()).appendChild(dashboardtxt.getElement());


        RouterLink productLink = new RouterLink("", ProductView.class);
        productLink.getElement().appendChild(productimg.getElement()).appendChild(producttxt.getElement());


        RouterLink employeeLink = new RouterLink("", EmployeeView.class);
        employeeLink.getElement().appendChild(employeeimg.getElement()).appendChild(employeetxt.getElement());


        RouterLink customerLink = new RouterLink("", CustomerView.class);
        customerLink.getElement().appendChild(customerimg.getElement()).appendChild(customertxt.getElement());

        RouterLink dealLink = new RouterLink("", DealView.class);
        dealLink.getElement().appendChild(dealimg.getElement()).appendChild(dealtxt.getElement());

        RouterLink inventoryLink = new RouterLink("", AssetView.class);
        inventoryLink.getElement().appendChild(inventoryimg.getElement()).appendChild(inventorytxt.getElement());

        RouterLink orderLink = new RouterLink("", OrderView.class);
        orderLink.getElement().appendChild(orderimg.getElement()).appendChild(ordertxt.getElement());


        RouterLink messageLink = new RouterLink("", MessagingView.class);
        messageLink.getElement().appendChild(messageimg.getElement()).appendChild(messagetxt.getElement());


        RouterLink invoiceLink = new RouterLink("", MessagingView.class);
        invoiceLink.getElement().appendChild(invoiceimg.getElement()).appendChild(invoicetxt.getElement());

        RouterLink personnalSettingLink = new RouterLink("", ProfileSettingView.class);
        personnalSettingLink.getElement().appendChild(invoiceimg.getElement()).appendChild(companySettingtxt.getElement());


        RouterLink companySettingLink = new RouterLink("", OrganisationSettingView.class);
        companySettingLink.getElement().appendChild(invoiceimg.getElement()).appendChild(personnalSettingtxt.getElement());



        RouterLink creditorLink = new RouterLink("", CreditorView.class);
        creditorLink.getElement().appendChild(creditortxt.getElement());

        RouterLink[] links = new RouterLink[] {
                dashboardLink,
                productLink,
                employeeLink,
                customerLink,
                orderLink,
                dealLink,
                inventoryLink,
                messageLink,
                personnalSettingLink,
                companySettingLink,
                creditorLink


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


    private void updateProfileimage(byte[] bytes) {

        try {



            avatar.getElement().setAttribute("src", new StreamResource(
                    "sample", () -> new ByteArrayInputStream(bytes)));
            try (ImageInputStream in = ImageIO.createImageInputStream(
                    new ByteArrayInputStream(bytes))) {
                final Iterator<ImageReader> readers = ImageIO
                        .getImageReaders(in);
                if (readers.hasNext()) {
                    ImageReader reader = readers.next();
                    try {
                        reader.setInput(in);
                        avatar.setWidth(32+ "px");
                        avatar.setHeight(32+ "px");
                    } finally {
                        reader.dispose();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateCompanyimage(byte[] bytes) {

        try {



            logo.getElement().setAttribute("src", new StreamResource(
                    "sample", () -> new ByteArrayInputStream(bytes)));
            try (ImageInputStream in = ImageIO.createImageInputStream(
                    new ByteArrayInputStream(bytes))) {
                final Iterator<ImageReader> readers = ImageIO
                        .getImageReaders(in);
                if (readers.hasNext()) {
                    ImageReader reader = readers.next();
                    try {
                        reader.setInput(in);
                        logo.setWidth(50+ "px");
                        logo.setHeight(50+ "px");
                    } finally {
                        reader.dispose();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
