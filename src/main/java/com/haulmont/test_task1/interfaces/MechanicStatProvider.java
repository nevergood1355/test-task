package com.haulmont.test_task1.interfaces;

import com.haulmont.test_task1.model.enums.Status;

public interface MechanicStatProvider {
    String getOrdersCountByStatus(Status status);
}
