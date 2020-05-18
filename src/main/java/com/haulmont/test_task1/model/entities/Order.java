package com.haulmont.test_task1.model.entities;


import com.haulmont.test_task1.model.enums.Status;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order extends AbstractEntity {
    private long id;
    private String description;
    private Date dateStart;
    private Date dateEnd;
    private long clientID;
    private long mechanicID;
    private long price;
    private Status status;

    public Order(long id, String description, Date dateStart, Date dateEnd, long clientID, long mechanicID, long price, Status status) {
        this.id = id;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.clientID = clientID;
        this.mechanicID = mechanicID;
        this.price = price;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    public long getMechanicID() {
        return mechanicID;
    }

    public void setMechanicID(long mechanicID) {
        this.mechanicID = mechanicID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return (getId() + 1) + ")"
                + "desc: " + description
                + ", dateStart: " + (new SimpleDateFormat("yyyy-MM-dd")).format(dateStart)
                + ", dateEnd: " + (new SimpleDateFormat("yyyy-MM-dd")).format(dateStart)
                + ", price: " + price
                + ", status: " + status.toString();
    }
}
