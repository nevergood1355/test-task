package com.haulmont.test_task1.view;

import com.haulmont.test_task1.interfaces.DeleteEntityListener;
import com.haulmont.test_task1.model.entities.AbstractEntity;
import com.vaadin.ui.*;

class DeleteDialog<E extends AbstractEntity> extends Window {
    private Label warning = new Label();
    private Button ok = new Button("OK");
    private Button cancel = new Button("CANCEL");
    private HorizontalLayout hLayout = new HorizontalLayout(ok, cancel);
    private VerticalLayout layout = new VerticalLayout(warning, hLayout);

    private void init(E entity) {
        setModal(true);
        setClosable(false);
        setResizable(false);
        layout.setComponentAlignment(hLayout, Alignment.BOTTOM_RIGHT);
        warning.setValue("Are you sure to delete '" + entity.toString() + "' from database?");
        cancel.addClickListener(clickEvent -> close());
        setContent(layout);
    }

    DeleteDialog(E entity, DeleteEntityListener<E> deleteEntityListener) {
        super("Element removing");
        init(entity);
        ok.addClickListener(clickEvent -> {
            try {
                deleteEntityListener.delete(entity);
                close();
            } catch (Exception e) {
                new ErrorMessage(e);
            }
        });
    }
}
