package com.haulmont.test_task1.interfaces;

import com.haulmont.test_task1.model.entities.AbstractEntity;
import com.haulmont.test_task1.model.entities.Client;

public interface AddEntityListener<E extends AbstractEntity> {
    void add(E e);
}
