package com.haulmont.test_task1.interfaces;

import com.haulmont.test_task1.model.entities.AbstractEntity;
import com.haulmont.test_task1.model.entities.Client;

import javax.swing.text.html.parser.Entity;

public interface UpdateEntityListener<E extends AbstractEntity> {
    void update(E e);
}
