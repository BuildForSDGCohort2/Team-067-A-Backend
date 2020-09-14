package com.sdgcrm.application.views.setting;

import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.CompanyService;
import com.sdgcrm.application.data.service.ProductService;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.security.SecurityUtils;
import com.sdgcrm.application.views.dashboard.DashboardView;
import com.sdgcrm.application.views.main.MainView;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.context.WebApplicationContext;
import sun.tools.jconsole.Plotter;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;

@Route(value = "setting/organisation", layout = MainView.class)
@PageTitle("Setting")
@CssImport("./styles/views/about/about-view.css")
public class OrganisationSettingView extends Div {


    UserService userservice;
    CompanyService companyService;
    User currentUser;


    TextField companyNametf = new TextField("Company Name");
    TextField sectortf = new TextField("Sector");
    EmailField emailtf = new EmailField("Email");
    NumberField phonetf = new NumberField("Phone");
    TextField locationtf = new TextField("Location");



    TextField roletf = new TextField("Role");

    Button save = new Button("Update Infomation");


    Image organisationpic = new Image();


    public OrganisationSettingView(@Autowired UserService userService, @Autowired CompanyService companyService) {


        this.userservice= userService;
        this.companyService= companyService;
        currentUser= userservice.findByEmail(SecurityUtils.getLoggedinUsername());
        companyNametf.setValue(currentUser.getCompanyProfile().getName());
        sectortf.setValue(currentUser.getCompanyProfile().getSector());
        emailtf.setValue(currentUser.getEmail());
        emailtf.setReadOnly(true);
        phonetf.setValue((double) currentUser.getPhone());
        locationtf.setValue(currentUser.getCompanyProfile().getLocation());
        roletf.setValue(currentUser.getCompanyPosition());




        if(currentUser.getCompanyProfile().getProfileImg()!=null){
            updateProfileimage(currentUser.getCompanyProfile().getProfileImg());
        }else{
            organisationpic.setSrc("images/logo.png");


        }

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);

        upload.getStyle().set("margin","10px auto");
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.setDropAllowed(false);




        VerticalLayout root = new VerticalLayout();
        root.setSizeUndefined();

        VerticalLayout pic = new VerticalLayout();
        pic.setSizeUndefined();
        pic.setSpacing(true);
        pic.getStyle().set("margin","0px auto");


        upload.addSucceededListener(event -> {
            Component component = createComponent(event.getMIMEType(),
                    event.getFileName(), buffer.getInputStream());
            Notification.show("Profile image updated successfully");

            showOutput(event.getFileName(), component, organisationpic);
        });
        organisationpic.getStyle().set("border-radius","50%");

        add(getToolbar());

        pic.add(organisationpic, upload);

        root.add(pic);



        FormLayout sample = new FormLayout();

        upload.addClassName("upload-btn");
        // Restrict maximum width and center on page
        sample.setMaxWidth("800px");
        sample.getStyle().set("margin", "0 auto");
        sample.getStyle().set("padding", "10px");
        // Allow the form layout to be responsive. On device widths 0-490px we have one
        // column, then we have two. Field labels are always on top of the fields.

        sample.add( companyNametf,

                sectortf,

                phonetf,
                locationtf,

                roletf,
                emailtf,
                createButtonsLayout());





        sample.setColspan(upload, 1);
        sample.setColspan(companyNametf, 1);
        root.add(sample);
        add(root);




    }




    private Component createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        save.addClickShortcut(Key.ENTER);

        save.setWidthFull();

        return new HorizontalLayout(save);
    }




    private Tabs getToolbar() {

        Tab tab1 = createMenuItem("Profile", ProfileSettingView.class);
        Tab tab2 = createMenuItem("Organisation", OrganisationSettingView.class);
        Tab tab3 = createMenuItem("Application", AppSettingView.class);
        Tabs tabs = new Tabs(tab1, tab2, tab3);
        tabs.setFlexGrowForEnclosedTabs(1);
        tabs.setSelectedTab(tab2);





        return tabs;
    }

    private Tab createMenuItem(String title, Class<? extends Component> target) {
        RouterLink link = new RouterLink(title, target);
        //if (icon != null) link.add(icon.create());

        Tab tab = new Tab();
        tab.add(link);
        return tab;
    }

    private Component createComponent(String mimeType, String fileName,
                                      InputStream stream) {
        if (mimeType.startsWith("text")) {
            return createTextComponent(stream);
        } else if (mimeType.startsWith("image")) {

            try {

                byte[] bytes = IOUtils.toByteArray(stream);

                currentUser.getCompanyProfile().setProfileImg(bytes);
                userservice.store(currentUser);
                organisationpic.getElement().setAttribute("src", new StreamResource(
                        fileName, () -> new ByteArrayInputStream(bytes)));
                try (ImageInputStream in = ImageIO.createImageInputStream(
                        new ByteArrayInputStream(bytes))) {
                    final Iterator<ImageReader> readers = ImageIO
                            .getImageReaders(in);
                    if (readers.hasNext()) {
                        ImageReader reader = readers.next();
                        try {
                            reader.setInput(in);
                            organisationpic.setWidth(200+ "px");
                            organisationpic.setHeight(200+ "px");
                        } finally {
                            reader.dispose();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(organisationpic.getSrc());
            System.out.println(organisationpic.getHeight()+" "+ organisationpic.getWidth());

            return organisationpic;
        }
        Div content = new Div();
        String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'",
                mimeType, MessageDigestUtil.sha256(stream.toString()));
        content.setText(text);
        return content;

    }




    private void updateProfileimage(byte[] bytes) {

            try {



                organisationpic.getElement().setAttribute("src", new StreamResource(
                        "sample", () -> new ByteArrayInputStream(bytes)));
                try (ImageInputStream in = ImageIO.createImageInputStream(
                        new ByteArrayInputStream(bytes))) {
                    final Iterator<ImageReader> readers = ImageIO
                            .getImageReaders(in);
                    if (readers.hasNext()) {
                        ImageReader reader = readers.next();
                        try {
                            reader.setInput(in);
                            organisationpic.setWidth(200+ "px");
                            organisationpic.setHeight(200+ "px");
                        } finally {
                            reader.dispose();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }




    private Component createTextComponent(InputStream stream) {
        String text;
        try {
            text = IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            text = "exception reading stream";
        }
        return new Text(text);
    }

    private void showOutput(String text, Component content,
                            HasComponents outputContainer) {
        System.out.println("file uploaded successfully");
    }
}
