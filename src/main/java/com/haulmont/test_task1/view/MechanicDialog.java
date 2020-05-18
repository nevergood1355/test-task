package com.haulmont.test_task1.view;

import com.haulmont.test_task1.interfaces.AddEntityListener;
import com.haulmont.test_task1.interfaces.UpdateEntityListener;
import com.haulmont.test_task1.model.entities.Human;
import com.haulmont.test_task1.model.entities.Mechanic;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.TextField;

public class MechanicDialog extends HumanEditDialog {
    private TextField hourlyWageField = new TextField("Hourly Wage");
    private UpdateEntityListener<Mechanic> updateMechanicListener;
    private AddEntityListener<Mechanic> addMechanicListener;

    private void initUI() {
        subContent.addComponent(hourlyWageField, 3);
        subContent.setComponentAlignment(hourlyWageField, Alignment.TOP_CENTER);
    }

    @Override
    protected boolean wrongValues() {
        return super.wrongValues() || hourlyWageField.isEmpty();
    }

    MechanicDialog(String caption, AddEntityListener<Mechanic> addMechanicListener) {
        super(caption);
        this.addMechanicListener = addMechanicListener;
        initUI();
        ok.addClickListener(clickEvent -> {
            if (wrongValues()) {
                new ErrorMessage(new Exception("Editing error! Empty fields detected!"));
            } else {
                this.addMechanicListener.add(new Mechanic(0,
                        fNameField.getValue(),
                        lNameField.getValue(),
                        mNameField.getValue(),
                        Long.parseLong(hourlyWageField.getValue())
                ));
                close();
            }
        });
    }

    MechanicDialog(String caption, Human human, UpdateEntityListener<Mechanic> updateMechanicListener) {
        super(caption, human);
        this.updateMechanicListener = updateMechanicListener;
        initUI();
        hourlyWageField.setValue(((Mechanic) human).getHourlyWage().toString());
        ok.addClickListener(clickEvent -> {
            if (wrongValues()) {
                new ErrorMessage(new Exception("Editing error! Empty fields detected!"));
            } else {
                this.updateMechanicListener.update(new Mechanic(human.getId(),
                        fNameField.getValue(),
                        lNameField.getValue(),
                        mNameField.getValue(),
                        Long.parseLong(hourlyWageField.getValue())
                ));
                close();
            }
        });
    }
}
