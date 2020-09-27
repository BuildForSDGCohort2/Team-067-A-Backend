package com.sdgcrm.application.views.messaging;

import com.sdgcrm.application.data.entity.Customer;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

import java.util.stream.Collectors;

public class MessagePreviewForm extends Div {


    private Recepient recepient;

    Button close = new Button("Close preview");
    Button send = new Button("Send Message");

    public MessagePreviewForm() {
        getStyle().set("background-color", "white");
        addClassName("preview-form");

        Div innerDiv= new Div();


        Span df=new Span("Message Preview");

        Div copyRight= new Div();
        Div date= new Div();

        Span copyRighttxt= new Span("Copyright © 2020. All Rights Reserved. We appreciate you! ");
        Span datetxt= new Span("Mon Aug 31 16:14:31 UTC 2020");
        copyRight.add(copyRighttxt);
        date.add(datetxt);



        HorizontalLayout header= new HorizontalLayout();
        Image logo= new Image();
        logo.setSrc("https://res.cloudinary.com/moversng/image/upload/v1598866869/easycrm_oiog5c.svg");

        header.add(logo);

        Div bodydiv= new Div();
        Span body=new Span("Thank you for signing up with EasyCRM.\n" +
                " \n" +
                "This would contain the body of your message for easy previewing. you would also me able to change templates for emails" +
                "for Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis aliquam odio, a iaculis justo auctor id. Donec non sem diam. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed at justo sapien.  " );

        bodydiv.add(body);


        body.getStyle().set("color","#585858");
        bodydiv.getStyle().set("margin-bottom","20px");

        innerDiv.add(df, header, bodydiv);

        getStyle().set("background-color", "#f3f3f3");
        getStyle().set("padding","50px");
        copyRight.getStyle().set("color", "#868686") ;
        copyRight.getStyle().set("text-align","center");
        date.getStyle().set("color", "#868686") ;
        date.getStyle().set("text-align", "center");
        add(innerDiv, copyRight, date, createButtonsLayout());

    }

    public void setRecepient(Recepient recepient) {
        this.recepient = recepient;

    }


    private HorizontalLayout createButtonsLayout() {
       close.addThemeVariants(ButtonVariant.LUMO_ERROR);
        send.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addClickShortcut(Key.ESCAPE);


        close.addClickListener(event -> fireEvent(new MessagePreviewForm.CloseEvent(this)));

        return new HorizontalLayout( close, send);

    }



    // Events
    public static abstract class PreviewFormEvent extends ComponentEvent<MessagePreviewForm> {
        private Recepient recepient;

        protected PreviewFormEvent(MessagePreviewForm source, Recepient recepient) {
            super(source, false);
            this.recepient = recepient;
        }

        public Recepient getRecepient() {
            return recepient;
        }
    }



    public static class SaveEvent extends MessagePreviewForm.PreviewFormEvent {
        SaveEvent(MessagePreviewForm source, Recepient recepient) {
            super(source, recepient);
        }
    }


    public static class CloseEvent extends MessagePreviewForm.PreviewFormEvent {
        CloseEvent(MessagePreviewForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
