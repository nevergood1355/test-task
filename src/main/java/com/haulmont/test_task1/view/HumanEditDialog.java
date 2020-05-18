package com.haulmont.test_task1.view;

import com.haulmont.test_task1.model.entities.Client;
import com.haulmont.test_task1.model.entities.Human;
import com.vaadin.data.HasValue;
import com.vaadin.ui.*;

class HumanEditDialog extends Window {
    protected VerticalLayout subContent = new VerticalLayout();
    protected Button ok = new Button("OK");

    protected TextField fNameField = new TextField("First Name");
    protected TextField lNameField = new TextField("Last Name");
    protected TextField mNameField = new TextField("Middle Name");

    private Human human;

    public Human getHuman() {
        return human;
    }

    protected boolean wrongValues() {
        return fNameField.isEmpty()
                || mNameField.isEmpty()
                || lNameField.isEmpty();

    }

    private void initBaseUI() {
        setModal(true);
        setResizable(false);
        setClosable(false);

        Layout h = new HorizontalLayout();
        Button cancel = new Button("CANCEL");

        h.addComponents(ok, cancel);

        subContent.addComponents(fNameField, lNameField, mNameField, h);
        subContent.setComponentAlignment(fNameField, Alignment.TOP_CENTER);
        subContent.setComponentAlignment(lNameField, Alignment.TOP_CENTER);
        subContent.setComponentAlignment(mNameField, Alignment.TOP_CENTER);
        subContent.setComponentAlignment(h, Alignment.TOP_CENTER);

        cancel.addClickListener(clickEvent -> close());
        setContent(subContent);
    }

    HumanEditDialog(String caption) {
        super(caption);
        initBaseUI();
    }


    HumanEditDialog(String caption, Human human) {
        super(caption);
        this.human = human;
        initBaseUI();
        fNameField.setValue(human.getFirstName());
        lNameField.setValue(human.getLastName());
        mNameField.setValue(human.getMiddleName());
    }
}
