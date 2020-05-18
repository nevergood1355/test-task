package com.haulmont.test_task1.view;

import com.haulmont.test_task1.interfaces.AddEntityListener;
import com.haulmont.test_task1.interfaces.UpdateEntityListener;
import com.haulmont.test_task1.model.entities.Client;
import com.haulmont.test_task1.model.entities.Human;
import com.vaadin.ui.*;

public class ClientDialog extends HumanEditDialog {
    private TextField phoneField = new TextField("Phone number");
    private UpdateEntityListener<Client> updateEntityListener;
    private AddEntityListener<Client> addEntityListener;

    private void initUI() {
        subContent.addComponent(phoneField, 3);
        subContent.setComponentAlignment(phoneField, Alignment.TOP_CENTER);
    }

    @Override
    protected boolean wrongValues() {
        return super.wrongValues() || phoneField.isEmpty();
    }

    ClientDialog(String caption, AddEntityListener<Client> addEntityListener) {
        super(caption);
        this.addEntityListener = addEntityListener;
        initUI();
        ok.addClickListener(clickEvent -> {
            if (wrongValues()) {
                new ErrorMessage(new Exception("Editing error! Empty fields detected!"));
            } else {
                this.addEntityListener.add(new Client(0,
                        fNameField.getValue(),
                        lNameField.getValue(),
                        mNameField.getValue(),
                        phoneField.getValue()));
                close();
            }
        });
    }

    ClientDialog(String caption, Human human, UpdateEntityListener<Client> updateEntityListener) {
        super(caption, human);
        this.updateEntityListener = updateEntityListener;
        initUI();
        phoneField.setValue(((Client) human).getPhoneNumber());
        ok.addClickListener(clickEvent -> {
            if (wrongValues()) {
                new ErrorMessage(new Exception("Editing error! Empty fields detected!"));
            } else {
                this.updateEntityListener.update(
                        new Client(human.getId(),
                                fNameField.getValue(),
                                lNameField.getValue(),
                                mNameField.getValue(),
                                phoneField.getValue()));
                close();
            }
        });
    }
}
