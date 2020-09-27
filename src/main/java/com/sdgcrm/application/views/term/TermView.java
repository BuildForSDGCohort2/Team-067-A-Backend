package com.sdgcrm.application.views.term;

import com.sdgcrm.application.views.AppConst;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;

@Route("terms")
@CssImport("./styles/shared-styles.css")
@Viewport(AppConst.VIEWPORT)
public class TermView extends VerticalLayout  {


    public TermView() {


        Image image = new Image("https://res.cloudinary.com/moversng/image/upload/v1598887746/easycrmx2_kqmi0r.svg", "logo");




        setAlignItems(Alignment.CENTER);


        HorizontalLayout header= new HorizontalLayout();
        Anchor homepage =new  Anchor("/signup", "");


        homepage.getElement().appendChild(VaadinIcon.ANGLE_LEFT.create().getElement()).appendChild(new Span("Back to website").getElement());

        header.add(homepage);
        getStyle().set("margin", "0 auto");
        H1 text= new H1("Terms & Conditions");
        Span details= new Span("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sodales augue eget urna laoreet vehicula. Maecenas elementum justo ut auctor convallis. Mauris vitae tempus dolor. Suspendisse ornare at urna faucibus rutrum. Nunc hendrerit sodales nulla, vel malesuada justo lobortis vitae. Integer posuere dolor velit, nec sagittis dui ornare vitae. Vestibulum non mi metus.\n" +
                "\n" +
                "Vestibulum consectetur porttitor eros, ut tristique tortor vehicula nec. Sed at pulvinar purus, sit amet viverra est. Nunc egestas vehicula mattis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent porttitor diam massa, in tempus arcu vehicula sit amet. Nunc at posuere elit, at maximus velit. Mauris convallis porta libero. Vivamus ante diam, rhoncus quis orci eget, ultricies lobortis ex. Cras non blandit nibh, id vestibulum lorem. Aliquam sed metus non felis vestibulum tincidunt. Nunc mauris eros, rutrum in ligula id, interdum ultrices nibh. Proin convallis sapien vitae tortor tincidunt luctus. Aenean eleifend ipsum ut lacus convallis pellentesque. Sed et nisi ex. Suspendisse consectetur elit non arcu efficitur finibus.\n" +
                "\n" +
                "Nunc egestas dapibus fringilla. Donec justo nulla, mollis nec libero placerat, scelerisque faucibus magna. Fusce fermentum vitae dui sed mollis. Sed lacus arcu, tempor at augue a, dignissim condimentum ligula. Curabitur at tincidunt sapien, sed fringilla leo. Donec tincidunt non lectus quis sodales. Proin cursus pharetra ex, vitae aliquam sapien euismod vel.\n" +
                "\n" +
                "Maecenas semper viverra lorem, id interdum tortor suscipit sit amet. Sed a ligula et ipsum condimentum dapibus non dictum mi. Etiam pulvinar, sapien nec fringilla fringilla, lorem elit fermentum risus, id fermentum magna metus ut mauris. Sed nec congue turpis. Ut volutpat id sem sit amet malesuada. Phasellus nibh nunc, tincidunt sit amet dapibus id, suscipit nec lectus. Aliquam erat volutpat. In vulputate varius mi in commodo. Donec consequat sem a dapibus aliquet. Nam eu urna ut orci tempus viverra in eget dolor. Quisque lacinia est non lectus cursus feugiat. Nulla facilisi.\n" +
                "\n" +
                "Aenean convallis mattis nisi vel condimentum. Integer congue turpis eget dolor vulputate aliquam. Etiam id egestas neque. Cras vel viverra tortor, sit amet consequat purus. Etiam iaculis nibh a sapien mollis pretium. Aliquam blandit velit at vehicula suscipit. Sed a gravida risus. Maecenas lacinia auctor faucibus. Praesent nisl leo, convallis efficitur volutpat sed, elementum non lorem. Praesent sodales ipsum nisl, eget maximus justo placerat quis. ");

        details.addClassName("termstxt");
        details.getStyle().set("margin", "0 auto");
        details.getStyle().set("max-width","1000px");
        text.getStyle().set("margin", "0 auto");

        getStyle().set("margin", "0 auto");


        add(image, header, text, details);
    }
}
