package com.sdgcrm.application.views.setting;

import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.security.SecurityUtils;
import com.sdgcrm.application.views.dashboard.DashboardView;
import com.sdgcrm.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@Route(value = "setting/profile", layout = MainView.class)
@PageTitle("Setting")
@CssImport("./styles/views/about/about-view.css")
public class ProfileSettingView extends Div {


    UserService userservice;
    User currentUser;


    TextField firstnametf = new TextField("First Name");
    TextField lastnametf = new TextField("Last Name");
    EmailField emailtf = new EmailField("Email");
    NumberField phonetf = new NumberField("Phone");


    ComboBox<String> roletf = new ComboBox<>("Company Position");



    Button save = new Button("Update Infomation");

    Button switchEmail = new Button("Change Email");
    Button resetPassword = new Button("Reset Password");

    Image profilePic = new Image();


    public ProfileSettingView(@Autowired UserService userService) {


        this.userservice= userService;
        currentUser= userservice.findByEmail(SecurityUtils.getLoggedinUsername());
        firstnametf.setValue(currentUser.getFirstname());
        lastnametf.setValue(currentUser.getLastname());
        emailtf.setValue(currentUser.getEmail());
        emailtf.setReadOnly(true);
        phonetf.setValue((double) currentUser.getPhone());
        roletf.setItems(userservice.getCompanyPosition());
        roletf.setValue(currentUser.getCompanyPosition());

        roletf.setReadOnly(true);


        if(currentUser.getProfileImg()!=null){


            updateProfileimage(currentUser.getProfileImg());
        }else{
            System.out.println("nulled image");
            profilePic.setSrc("images/user.svg");
            profilePic.setWidthFull();

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

            showSuccessNotification("Profile image updated successfully");
            showOutput(event.getFileName(), component, profilePic);
        });
        profilePic.getStyle().set("border-radius","50%");



        pic.add(profilePic, upload);

        root.add(pic);

        save.addClickListener(buttonClickEvent -> {
            currentUser.setFirstname(firstnametf.getValue());
            currentUser.setLastname(lastnametf.getValue());
            currentUser.setPhone(phonetf.getValue().longValue());

            userService.store(currentUser);
        showSuccessNotification("Profile details updated successfully");
        });



        FormLayout sample = new FormLayout();

        upload.addClassName("upload-btn");
        // Restrict maximum width and center on page
        sample.setMaxWidth("800px");
        sample.getStyle().set("margin", "0 auto");
        sample.getStyle().set("padding", "10px");
        // Allow the form layout to be responsive. On device widths 0-490px we have one
        // column, then we have two. Field labels are always on top of the fields.

        sample.add( firstnametf,

                lastnametf,

                phonetf,

                roletf,
                emailtf,
                createButtonsLayout(), createButtonsResetandchangeLayout());





        sample.setColspan(upload, 1);
        sample.setColspan(firstnametf, 1);
        root.add(sample);
        add(root);




    }




    private Component createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        save.addClickShortcut(Key.ENTER);

        save.setWidthFull();

        return new HorizontalLayout(save);
    }

    private Component createButtonsResetandchangeLayout() {

        switchEmail.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        resetPassword.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
         return new HorizontalLayout(switchEmail, resetPassword);
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

                currentUser.setProfileImg(bytes);
                userservice.store(currentUser);
                profilePic.getElement().setAttribute("src", new StreamResource(
                        fileName, () -> new ByteArrayInputStream(bytes)));
                try (ImageInputStream in = ImageIO.createImageInputStream(
                        new ByteArrayInputStream(bytes))) {
                    final Iterator<ImageReader> readers = ImageIO
                            .getImageReaders(in);
                    if (readers.hasNext()) {
                        ImageReader reader = readers.next();
                        try {
                            reader.setInput(in);
                            profilePic.setWidth(200+ "px");
                            profilePic.setHeight(200+ "px");
                        } finally {
                            reader.dispose();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(profilePic.getSrc());
            System.out.println(profilePic.getHeight()+" "+ profilePic.getWidth());

            return profilePic;
        }
        Div content = new Div();
        String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'",
                mimeType, MessageDigestUtil.sha256(stream.toString()));
        content.setText(text);
        return content;

    }




    private void updateProfileimage(byte[] bytes) {

            try {



                profilePic.getElement().setAttribute("src", new StreamResource(
                        "sample", () -> new ByteArrayInputStream(bytes)));
                try (ImageInputStream in = ImageIO.createImageInputStream(
                        new ByteArrayInputStream(bytes))) {
                    final Iterator<ImageReader> readers = ImageIO
                            .getImageReaders(in);
                    if (readers.hasNext()) {
                        ImageReader reader = readers.next();
                        try {
                            reader.setInput(in);
                            profilePic.setWidth(200+ "px");
                            profilePic.setHeight(200+ "px");
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

    public void showSuccessNotification(String message){
        Notification notification = Notification.show(message, 3000, Notification.Position.BOTTOM_END);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

    }

    public void showErrorNotification(String message){
        Notification notification = Notification.show(message, 3000, Notification.Position.BOTTOM_END);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

    }
}
