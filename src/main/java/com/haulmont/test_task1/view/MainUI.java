package com.haulmont.test_task1.view;

import com.haulmont.test_task1.model.db.ClientsDBController;
import com.haulmont.test_task1.model.db.MechanicsDBController;
import com.haulmont.test_task1.model.db.OrdersDBController;
import com.haulmont.test_task1.model.entities.Client;
import com.haulmont.test_task1.model.entities.Human;
import com.haulmont.test_task1.model.entities.Mechanic;
import com.haulmont.test_task1.model.entities.Order;
import com.haulmont.test_task1.model.enums.Status;
import com.haulmont.test_task1.utils.Log;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    private ClientsDBController clients;
    private MechanicsDBController mechanics;
    private OrdersDBController orders;

    @Override
    protected void init(VaadinRequest request) {
        initControllers();
        TabSheet tabSheet = new TabSheet();
        initClientsTable(tabSheet);
        initMechanicsTable(tabSheet);
        initOrdersTable(tabSheet);
        tabSheet.setSizeFull();
        setContent(tabSheet);
    }

    private void initControllers() {
        clients = new ClientsDBController();
        mechanics = new MechanicsDBController();
        orders = new OrdersDBController();
    }

    private void initClientsTable(TabSheet tabSheet) {
        final Grid<Client> clientGrid = new Grid<>();
        clientGrid.setItems(clients.getAll());
        clientGrid.addColumn(Human::getFullName).setCaption("Name");
        clientGrid.addColumn(Client::getPhoneNumber).setCaption("PhoneNumber");
        clientGrid.setSizeFull();
        Layout functionalLayout = new HorizontalLayout();

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button delButton = new Button("Delete");
        addButton.addClickListener(clickEvent -> {
            ClientDialog dialog = new ClientDialog("Add Client", client -> {
                clients.create(client);
                clientGrid.setItems(clients.getAll());
            });
            addWindow(dialog);
        });

        editButton.addClickListener(clickEvent -> {
            if ((clientGrid.getSelectionModel().getFirstSelectedItem().isPresent())) {
                Client selectItem = clientGrid.getSelectionModel().getFirstSelectedItem().get();
                ClientDialog dialog = new ClientDialog("Edit Client", selectItem, client -> {
                    clients.update(client);
                    clientGrid.setItems(clients.getAll());
                });
                addWindow(dialog);
            }
        });

        delButton.addClickListener(clickEvent -> {
            if ((clientGrid.getSelectionModel().getFirstSelectedItem().isPresent())) {
                Client selectItem = clientGrid.getSelectionModel().getFirstSelectedItem().get();
                DeleteDialog<Client> dialog = new DeleteDialog<>(selectItem, client -> {
                    clients.delete(client.getId());
                    clientGrid.setItems(clients.getAll());
                });
                addWindow(dialog);
            }
        });

        functionalLayout.addComponents(addButton, editButton, delButton);


        Layout l = new VerticalLayout();

        l.addComponent(functionalLayout);
        l.addComponent(clientGrid);

        tabSheet.addTab(l).setCaption("Clients");
    }

    private void initMechanicsTable(TabSheet tabSheet) {
        final Grid<Mechanic> mechanicGrid = new Grid<>();
        mechanicGrid.setItems(mechanics.getAll());
        mechanicGrid.addColumn(Human::getFullName).setCaption("Name");
        mechanicGrid.addColumn(Mechanic::getHourlyWage).setCaption("HourlyWage");
        mechanicGrid.setSizeFull();
        Layout functionalLayout = new HorizontalLayout();

        Button statButton = new Button("Statistic");
        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button delButton = new Button("Delete");
        addButton.addClickListener(clickEvent -> {
            MechanicDialog dialog = new MechanicDialog("Add Mechanic", mechanic -> {
                mechanics.create(mechanic);
                mechanicGrid.setItems(mechanics.getAll());
            });
            addWindow(dialog);
        });

        editButton.addClickListener(clickEvent -> {
            if ((mechanicGrid.getSelectionModel().getFirstSelectedItem().isPresent())) {
                Mechanic selectItem = mechanicGrid.getSelectionModel().getFirstSelectedItem().get();
                MechanicDialog dialog = new MechanicDialog("Edit Mechanic", selectItem, mechanic -> {
                    mechanics.update(mechanic);
                    mechanicGrid.setItems(mechanics.getAll());
                });
                addWindow(dialog);
            }
        });

        delButton.addClickListener(clickEvent -> {
            if ((mechanicGrid.getSelectionModel().getFirstSelectedItem().isPresent())) {
                Mechanic selectItem = mechanicGrid.getSelectionModel().getFirstSelectedItem().get();
                DeleteDialog<Mechanic> dialog = new DeleteDialog<>(selectItem, mechanic -> {
                    mechanics.delete(mechanic.getId());
                    mechanicGrid.setItems(mechanics.getAll());
                });
                addWindow(dialog);
            }
        });

        statButton.addClickListener(clickEvent -> {
            if ((mechanicGrid.getSelectionModel().getFirstSelectedItem().isPresent())) {
                Mechanic selectItem = mechanicGrid.getSelectionModel().getFirstSelectedItem().get();
                StatisticView dialog = new StatisticView(status -> String.valueOf(orders.getOrdersCountByMechanic(selectItem.getId(), status)));
                addWindow(dialog);
            }
        });

        functionalLayout.addComponents(statButton, addButton, editButton, delButton);


        Layout l = new VerticalLayout();

        l.addComponent(functionalLayout);
        l.addComponent(mechanicGrid);

        tabSheet.addTab(l).setCaption("Mechanic");
    }

    private void initOrdersTable(TabSheet tabSheet) {
        Grid<Order> orderGrid = new Grid<>();
        orderGrid.setItems(orders.getAll());
        orderGrid.addColumn(Order::getDescription).setCaption("Description");
        orderGrid.addColumn(r -> (new SimpleDateFormat("yyyy-MM-dd")).format(r.getDateStart())).setCaption("Date Start");
        orderGrid.addColumn(r -> (new SimpleDateFormat("yyyy-MM-dd")).format(r.getDateEnd())).setCaption("Date End");
        orderGrid.addColumn(r -> clients.getEntityById(r.getClientID()).getFullName()).setCaption("Client");
        orderGrid.addColumn(r -> mechanics.getEntityById(r.getMechanicID()).getFullName()).setCaption("Mechanic");
        orderGrid.addColumn(Order::getPrice).setCaption("Price");
        orderGrid.addColumn(Order::getStatus).setCaption("Status");
        orderGrid.setSizeFull();
        Layout functionalLayout = new HorizontalLayout();

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button delButton = new Button("Delete");

        addButton.addClickListener(clickEvent -> {
            OrderEditDialog dialog = new OrderEditDialog(clients.getAll(), mechanics.getAll(), order -> {
                orders.create(order);
                orderGrid.setItems(orders.getAll());
            });
            addWindow(dialog);
        });

        editButton.addClickListener(clickEvent -> {
            if ((orderGrid.getSelectionModel().getFirstSelectedItem().isPresent())) {
                Order selectItem = orderGrid.getSelectionModel().getFirstSelectedItem().get();
                OrderEditDialog dialog = new OrderEditDialog(clients.getAll(), mechanics.getAll(), selectItem, order -> {
                    orders.update(order);
                    orderGrid.setItems(orders.getAll());
                });
                addWindow(dialog);
            }
        });

        delButton.addClickListener(clickEvent -> {
            if ((orderGrid.getSelectionModel().getFirstSelectedItem().isPresent())) {
                Order selectItem = orderGrid.getSelectionModel().getFirstSelectedItem().get();
                DeleteDialog<Order> dialog = new DeleteDialog<>(orderGrid.getSelectionModel().getFirstSelectedItem().get(), order -> {
                    orders.delete(selectItem.getId());
                    orderGrid.setItems(orders.getAll());
                });
                addWindow(dialog);
            }
        });

        TextField filterDescField = new TextField();
        filterDescField.setPlaceholder("filterByDescription by Description");
        NativeSelect<Client> clientSelector = new NativeSelect<>("Client: ");
        clientSelector.setItems(clients.getAll());
        NativeSelect<Status> statusSelector = new NativeSelect<>("Status:");
        statusSelector.setItems(Status.values());

        Button filterButton = new Button("Filter");
        filterButton.addClickListener(clickEvent -> {
            List<Order> filter1;
            List<Order> filter2;
            List<Order> filter3;
            List<Order> out = new ArrayList<>();

            if (!filterDescField.getValue().isEmpty()) {
                filter1 = orders.filterByDescription(filterDescField.getValue());
            } else {
                filter1 = orders.getAll();
            }

            if (clientSelector.getSelectedItem().isPresent()) {
                filter2 = orders.filterByClient(clientSelector.getSelectedItem().get().getId());
            } else {
                filter2 = orders.getAll();
            }
            if (statusSelector.getSelectedItem().isPresent()) {
                filter3 = orders.filterByStatus(statusSelector.getSelectedItem().get());
            } else {
                filter3 = orders.getAll();
            }

            /* Соответствующие элементы должны содержаться в каждом списке
             * Если фильт пустой, то он не влияет на выборку
             * */
            filter1.forEach(e1 -> {
                filter2.forEach(e2 -> {
                    filter3.forEach(e3 -> {
                        if (e1.getId() == e2.getId() && e2.getId() == e3.getId()) {
                            out.add(e3);
                        }
                    });
                });
            });
            orderGrid.setItems(out);
        });

        functionalLayout.addComponents(addButton, editButton, delButton,
                filterDescField, clientSelector, statusSelector, filterButton);


        Layout l = new VerticalLayout();

        l.addComponent(functionalLayout);
        l.addComponent(orderGrid);

        tabSheet.addTab(l).setCaption("Orders");
    }
}