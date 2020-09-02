package com.sdgcrm.application.views.setting;

import com.sdgcrm.application.views.customer.CustomerForm;
import com.sdgcrm.application.views.main.MainView;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@Route(value = "Setting", layout = MainView.class)
@PageTitle("Setting")
@CssImport("./styles/views/about/about-view.css")
public class SettingView extends Div {



    TextField fullNametf = new TextField("Full Name");
    EmailField emailtf = new EmailField("Email");
    TextField phonetf = new TextField("Phone");
    TextField locationtf = new TextField("Location");
    TextField companyNametf = new TextField("Company Name");
    TextField notestf = new TextField("Notes");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    private Upload unusedUpload;
    private ProgressBar unusedProgressBar;

    public SettingView() {
        setId("abo-view");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        Div output = new Div();

        upload.addSucceededListener(event -> {
            Component component = createComponent(event.getMIMEType(),
                    event.getFileName(), buffer.getInputStream());
            showOutput(event.getFileName(), component, output);
        });




        add(getToolbar());
        FormLayout sample = new FormLayout();
        Image image = new Image("https://dummyimage.com/300x300/000/fff", "DummyImage");


        // Restrict maximum width and center on page
        sample.setMaxWidth("800px");
        sample.getStyle().set("margin", "0 auto");
        sample.getStyle().set("padding", "10px");
        // Allow the form layout to be responsive. On device widths 0-490px we have one
        // column, then we have two. Field labels are always on top of the fields.
        sample.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        sample.add(image, upload, fullNametf,
                emailtf,
                phonetf,
                companyNametf,
                locationtf,
                notestf,
                createButtonsLayout());
        sample.setColspan(image, 1);
        sample.setColspan(emailtf, 1);
        sample.setColspan(phonetf, 1);
        add(sample);




    }

    private Component createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);



        return new HorizontalLayout(save, delete, close);
    }


    private Tabs getToolbar() {

        Tab tab1 = new Tab("Profile");
        Tab tab2 = new Tab("Account");
        Tab tab3 = new Tab("Organisation");
        Tabs tabs = new Tabs(tab1, tab2, tab3);
        tabs.setFlexGrowForEnclosedTabs(1);




        return tabs;
    }
    private Component createComponent(String mimeType, String fileName,
                                      InputStream stream) {
        if (mimeType.startsWith("text")) {
            return createTextComponent(stream);
        } else if (mimeType.startsWith("image")) {
            Image image = new Image();
            try {

                byte[] bytes = IOUtils.toByteArray(stream);
                image.getElement().setAttribute("src", new StreamResource(
                        fileName, () -> new ByteArrayInputStream(bytes)));
                try (ImageInputStream in = ImageIO.createImageInputStream(
                        new ByteArrayInputStream(bytes))) {
                    final Iterator<ImageReader> readers = ImageIO
                            .getImageReaders(in);
                    if (readers.hasNext()) {
                        ImageReader reader = readers.next();
                        try {
                            reader.setInput(in);
                            image.setWidth(reader.getWidth(0) + "px");
                            image.setHeight(reader.getHeight(0) + "px");
                        } finally {
                            reader.dispose();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return image;
        }
        Div content = new Div();
        String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'",
                mimeType, MessageDigestUtil.sha256(stream.toString()));
        content.setText(text);
        return content;

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
        HtmlComponent p = new HtmlComponent(Tag.P);
        p.getElement().setText(text);
        outputContainer.add(p);
        outputContainer.add(content);
    }
}
