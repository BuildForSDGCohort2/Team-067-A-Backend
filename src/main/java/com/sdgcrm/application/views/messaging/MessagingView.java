package com.sdgcrm.application.views.messaging;

import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.data.service.CustomerService;
import com.sdgcrm.application.data.service.UserService;
import com.sdgcrm.application.email.ScheduleEmailRequest;
import com.sdgcrm.application.security.SecurityUtils;
import com.sdgcrm.application.service.EmailService;
import com.sdgcrm.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "Messaging", layout = MainView.class)
@PageTitle("Messaging")
@CssImport("./styles/shared-styles.css")
public class MessagingView extends Div {


    CustomerService customerService;
    Recepient recepient;
    UserService userservice;
    User currentUser;


    EmailService emailservice;

    TextField subjectTf = new TextField("Subject");
    EmailField emailreceipiantstf = new EmailField("Email");
    TextArea messageBodyTF = new TextArea("Message");




    Button save = new Button("Send Message");
    private TextField filterText = new TextField();
    RecepientsForm recepientsForm;


    FormLayout grid= new FormLayout();
    Div pdf= new Div();

    Binder<Recepient> binder = new Binder<>();

    public MessagingView(@Autowired CustomerService customerService,  @Autowired UserService userservice,
                         @Autowired
                                 EmailService emailservice) {


        recepient= new Recepient();
        this.customerService= customerService;
        this.userservice= userservice;
        this.emailservice= emailservice;

       this.currentUser= userservice.findByEmail(SecurityUtils.getLoggedinUsername());


        addClassName("list-view");
        setSizeFull();

        configureGrid();

        recepientsForm = new RecepientsForm(customerService, currentUser);


        recepientsForm.addListener(RecepientsForm.CloseEvent.class, e -> closeEditor());
        recepientsForm.addListener(RecepientsForm.SaveEvent.class, this::saveProduct);

        messageBodyTF.setHeight("200px");
        pdf.addClassName("messagetemplateviewer");
        pdf.getStyle().set("display","none");
        pdf.getStyle().set("background-color", "white");
        pdf.getStyle().set("width", "50%");


        Div content = new Div( grid,pdf, recepientsForm);


        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);



        closeEditor();

        binder.forField(emailreceipiantstf).asRequired().withValidator(new EmailValidator("Invalid Receiver Email")).bind(Recepient::getRecepientsList,Recepient::setRecepientsList);
        binder.forField(subjectTf).asRequired().bind(Recepient::getSubject,Recepient::setSubject);
        binder.forField(messageBodyTF).asRequired().bind(Recepient::getMessage,Recepient::setMessage);


        save.addClickListener(buttonClickEvent -> {
            if(binder.isValid()){

                ScheduleEmailRequest welcomemail= new ScheduleEmailRequest();
                welcomemail.setEmail(emailreceipiantstf.getValue());
                welcomemail.setSubject(subjectTf.getValue());
                welcomemail.setName(currentUser.getCompanyProfile().getName());
                welcomemail.setBody(messageBodyTF.getValue());

                welcomemail.setTemplate("Message.html");

                emailservice.scheduleEmail(welcomemail);

                showSuccessNotification("Email sent successfully");
                init();
            }


        });


    }

    private void init() {
        binder.setBean(new Recepient());
    }


    public  void saveProduct(RecepientsForm.SaveEvent event) {
        System.out.println(event.getRecepient().getRecepientsList().toString());
        recepient.setRecepientsList(event.getRecepient().getRecepientsList());
        emailreceipiantstf.setValue(event.getRecepient().getRecepientsList().toString());

        closeEditor();
    }

    private void closeEditor() {


        recepientsForm.setRecepient(null);
        recepientsForm.setVisible(false);
        removeClassName("editing");
        pdf.setVisible(true);
    }

    private Component createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        save.setWidthFull();

        return new HorizontalLayout(save);
    }

    private HorizontalLayout getToolbar() {


        Button addProductButton = new Button("Select Receiver(s)");


        addProductButton.addClickListener(click -> selectRecepient());

        HorizontalLayout toolbar = new HorizontalLayout(addProductButton);
        toolbar.addClassName("toolbar");
        toolbar.getStyle().set("margin-left", "10px");
        toolbar.getStyle().set("margin-top", "10px");


        return toolbar;
    }

    private void selectRecepient() {
        System.out.println("selecting recepients");
        editRecepient(new Recepient());

        //grid.asSingleSelect().clear();

    }

    private void editRecepient(Recepient recepient) {

        if (recepient == null) {
            System.out.println("recipient is null");
            closeEditor();
        } else {
            recepientsForm.setRecepient(recepient);
            recepientsForm.setVisible(true);
            addClassName("editing");
            pdf.setVisible(false);
        }
    }

    private void configureGrid() {
        grid.addClassName("customer-grid");
        grid.setSizeFull();
        grid.getStyle().set("border", "1px solid black");

        grid.setMaxWidth("800px");
        grid.getStyle().set("padding", "10px");



        grid.add(emailreceipiantstf,
                subjectTf,
                messageBodyTF,

                createButtonsLayout());

        grid.setColspan(subjectTf, 2);
        grid.setColspan(messageBodyTF, 2);





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
