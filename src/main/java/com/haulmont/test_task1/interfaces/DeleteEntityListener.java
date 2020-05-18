package com.haulmont.test_task1.interfaces;

import com.haulmont.test_task1.model.entities.AbstractEntity;

public interface DeleteEntityListener<E extends AbstractEntity> {
    void delete(E e);
}
