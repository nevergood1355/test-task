package com.haulmont.test_task1.view;

import com.vaadin.ui.*;

class ErrorMessage extends Window {

    ErrorMessage(Exception e) {
        setClosable(false);
        setResizable(false);
        center();
        Label label = new Label(e.getMessage());
        label.setWidth("300px");
        Button ok = new Button("Got it!");
        VerticalLayout vertical = new VerticalLayout(label, ok);
        vertical.setComponentAlignment(ok, Alignment.BOTTOM_CENTER);
        ok.addClickListener(clickEvent1 -> close());
        setContent(vertical);
        UI.getCurrent().addWindow(this);
    }
}
