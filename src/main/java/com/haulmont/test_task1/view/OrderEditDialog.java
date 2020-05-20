package com.haulmont.test_task1.view;

import com.haulmont.test_task1.interfaces.AddEntityListener;
import com.haulmont.test_task1.interfaces.UpdateEntityListener;
import com.haulmont.test_task1.model.entities.Client;
import com.haulmont.test_task1.model.entities.Mechanic;
import com.haulmont.test_task1.model.entities.Order;
import com.haulmont.test_task1.model.enums.Status;
import com.vaadin.ui.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

class OrderEditDialog extends Window {

    private TextArea description = new TextArea("description");
    private NativeSelect<Client> clientSelector = new NativeSelect<>("Client: ");
    private NativeSelect<Mechanic> mechanicSelector = new NativeSelect<>("Mechanic: ");

    private List<Client> clients;
    private List<Mechanic> mechanics;

    private DateField dateStartField = new DateField("Date start: ");
    private DateField dateEndField = new DateField("Date end: ");
    private TextField priceField = new TextField("Price: ");
    private NativeSelect<Status> statusSelector = new NativeSelect<>("Status:");

    private Button ok = new Button("OK");
    private VerticalLayout verticalLayout = new VerticalLayout();

    Button getOk() {
        return ok;
    }

    private void init() {
        setModal(true);
        setResizable(false);
        setClosable(false);

        description.setPlaceholder("Order description.");

        clientSelector.setItems(clients);
        mechanicSelector.setItems(mechanics);
        dateStartField.setValue(LocalDate.now());
        dateEndField.setValue(LocalDate.now());
        statusSelector.setItems(Status.values());
        priceField.setPlaceholder("Order Price");

        VerticalLayout leftLayout = new VerticalLayout(clientSelector, mechanicSelector, description);
        VerticalLayout rightLayout = new VerticalLayout(dateStartField, dateEndField, priceField, statusSelector);

        HorizontalLayout centerLayout = new HorizontalLayout(leftLayout, rightLayout);

        Button cancel = new Button("CANCEL");
        HorizontalLayout hLayout = new HorizontalLayout(ok, cancel);

        verticalLayout.addComponents(centerLayout, hLayout);

        verticalLayout.setComponentAlignment(centerLayout, Alignment.TOP_CENTER);
        verticalLayout.setComponentAlignment(hLayout, Alignment.BOTTOM_RIGHT);

        cancel.addClickListener(clickEvent -> close());

        setContent(verticalLayout);
    }

    private boolean wrongValues() {
        try {
            if (dateStartField.getValue().isBefore(LocalDate.of(1991, 1, 1))
                    || dateStartField.getValue().isAfter(LocalDate.now()))
                throw new Exception("Data issue! The startStart is out of bounds [1991 - now]!");

            /*
            if (dateEndField.getValue().isBefore(LocalDate.of(1991, 1, 1))
                    || dateEndField.getValue().isAfter(dateStartField.getValue()))
                throw new Exception("Data issue! The dateEnd is out of bounds [1991 - now]!");
            */

            if (!mechanicSelector.getSelectedItem().isPresent()
                    || !clientSelector.getSelectedItem().isPresent()
                    || !statusSelector.getSelectedItem().isPresent()
                    || priceField.isEmpty()
                    || description.isEmpty()) {

                throw new Exception("Some fields are empty:"
                        + (!clientSelector.getSelectedItem().isPresent() ? " 'Client selection'" : "")
                        + (!mechanicSelector.getSelectedItem().isPresent() ? " 'Mechanic selection'" : "")
                        + (description.isEmpty() ? " 'Description Field'" : "")
                        + (priceField.isEmpty() ? " 'Validation Field'" : "")
                        + (!statusSelector.getSelectedItem().isPresent() ? " 'Priority selection'" : ""));
            }

            int price;

            try {
                price = Integer.parseInt(priceField.getValue());
            } catch (Exception e) {
                e.getMessage();
                throw new Exception("Wrong input in 'Price' field!");
            }

            if (price < 1)
                throw new Exception("'Price' is out of bounds!");
            return false;
        } catch (Exception e) {
            new ErrorMessage(e);
            return true;
        }
    }

    private Order getEntity(long id) {
        return new Order(id,
                description.getValue(),
                Date.valueOf(dateStartField.getValue()),
                Date.valueOf(dateEndField.getValue()),
                clientSelector.getSelectedItem().get().getId(),
                mechanicSelector.getSelectedItem().get().getId(),
                Integer.parseInt(priceField.getValue()),
                statusSelector.getSelectedItem().get());
    }

    OrderEditDialog(List<Client> clients, List<Mechanic> mechanics, AddEntityListener<Order> addEntityListener) {
        super("New Order");
        this.clients = clients;
        this.mechanics = mechanics;

        init();
        ok.addClickListener(clickEvent -> {
            if (wrongValues()) return;
            addEntityListener.add(getEntity(0));
            close();
        });
    }

    OrderEditDialog(List<Client> clients, List<Mechanic> mechanics, Order order, UpdateEntityListener<Order> updateEntityListener) {
        super("Edit Order");
        this.clients = clients;
        this.mechanics = mechanics;

        init();
        description.setValue(order.getDescription());
        for (Mechanic m : mechanics) {
            if (m.getId() == order.getMechanicID()) {
                mechanicSelector.setSelectedItem(m);
                break;
            }
        }

        for (Client c : clients) {
            if (c.getId() == order.getClientID()) {
                clientSelector.setSelectedItem(c);
                break;
            }
        }

        dateStartField.setValue(new Date(order.getDateStart().getTime()).toLocalDate());
        dateEndField.setValue(new Date(order.getDateEnd().getTime()).toLocalDate());
        priceField.setValue(String.valueOf(order.getPrice()));
        statusSelector.setSelectedItem(Status.get(order.getStatus().toString()));

        ok.addClickListener(clickEvent -> {
            if (wrongValues()) return;
            updateEntityListener.update(getEntity(order.getId()));
            close();
        });
    }
}
