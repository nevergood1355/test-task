package com.haulmont.test_task1.model.db;

import com.haulmont.test_task1.model.entities.Order;
import com.haulmont.test_task1.model.enums.Status;
import com.haulmont.test_task1.utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrdersDBController extends AbstractDBController<Order, Long> {

    public OrdersDBController() {
        super();
    }

    @Override
    protected void addResultsToList(ResultSet rs, List<Order> out) {
        try {
            while (rs.next()) {
                out.add(new Order(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getDate(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        Status.get(rs.getString(8))
                ));
            }
        } catch (SQLException e) {
            Log.printSQLEx("OrdersDBController", "addResultsToList", e.getMessage());
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        try {
            ResultSet rs = sendQuery("SELECT * FROM Orders");
            addResultsToList(rs, orders);
        } catch (SQLException e) {
            Log.printSQLEx("OrdersDBController", "getAll", e.getMessage());
        }
        return orders;
    }

    @Override
    public Order getEntityById(Long id) {
        Order entity = null;
        try {
            ResultSet rs = sendQuery("SELECT * FROM Orders WHERE id = " + id);
            if (rs.next())
                entity = new Order(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getDate(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        Status.get(rs.getString(8))
                );

        } catch (SQLException e) {
            Log.printSQLEx("OrdersDBController", "getEntityById", e.getMessage());
        }

        return entity;
    }

    @Override
    public void update(Order entity) {
        String query;
        try {
            query = "UPDATE Orders SET "
                    + "description = '" + entity.getDescription()
                    + "', dateStart = DATE '" + (new SimpleDateFormat("yyyy-MM-dd").format(entity.getDateStart()))
                    + "', dateEnd = DATE '" + (new SimpleDateFormat("yyyy-MM-dd").format(entity.getDateEnd()))
                    + "', clientID = " + entity.getClientID()
                    + ", mechanicID = " + entity.getMechanicID()
                    + ", price = " + entity.getPrice()
                    + ", status = '" + entity.getStatus().toString()
                    + "' WHERE id = " + entity.getId();
            sendQuery(query);

        } catch (SQLException e) {
            Log.printSQLEx("OrdersDBController", "update ", e.getMessage());
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            sendQuery("DELETE FROM Orders WHERE id = " + id);
            return true;
        } catch (SQLException e) {
            Log.printSQLEx("OrdersDBController", "delete", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean create(Order entity) {
        try {
            sendQuery("INSERT INTO Orders (description, dateStart, dateEnd, clientID, mechanicID, price, status) values ('"
                    + entity.getDescription()
                    + "', DATE '" + (new SimpleDateFormat("yyyy-MM-dd").format(entity.getDateStart()))
                    + "', DATE '" + (new SimpleDateFormat("yyyy-MM-dd").format(entity.getDateEnd())) + "', "
                    + entity.getClientID() + ", "
                    + entity.getMechanicID() + ", "
                    + entity.getPrice() + ", '"
                    + entity.getStatus().toString() + "')"
            );
            return true;
        } catch (SQLException e) {
            Log.printSQLEx("OrdersDBController", "create", e.getMessage());
        }
        return false;
    }

    public List<Order> filterByDescription(String excerpt) {
        List<Order> out = new ArrayList<>();
        try {
            ResultSet rs = sendQuery("SELECT * FROM Orders WHERE " +
                    "description LIKE '" + excerpt + "'");
            addResultsToList(rs, out);
        } catch (SQLException e) {
            Log.printSQLEx("OrdersDBController", "filterByDescription", e.getMessage());
        }
        return out;
    }

    public List<Order> filterByClient(long clientID) {
        List<Order> out = new ArrayList<>();
        try {
            ResultSet rs = sendQuery("SELECT * FROM Orders WHERE clientID = " + clientID);
            addResultsToList(rs, out);
        } catch (SQLException e) {
            Log.printSQLEx("OrdersDBController", "filterByClient", e.getMessage());
        }
        return out;
    }

    public int getOrdersCountByMechanic(long mechanicID, Status status) {
        List<Order> out = new ArrayList<>();
        try {
            ResultSet rs = sendQuery("SELECT * FROM Orders WHERE mechanicID = " + mechanicID + " AND " +
                    "status = '" + status.toString() + "'");
            addResultsToList(rs, out);
        } catch (SQLException e) {
            Log.printSQLEx("OrdersDBController", "getOrdersCountByMechanic", e.getMessage());
        }
        return out.size();
    }

    public List<Order> filterByStatus(Status status) {
        List<Order> out = new ArrayList<>();
        try {
            ResultSet rs = sendQuery("SELECT * FROM Orders WHERE " + "status = '" + status.toString() + "'");
            addResultsToList(rs, out);
        } catch (SQLException e) {
            Log.printSQLEx("OrdersDBController", "filterByStatus", e.getMessage());
        }
        return out;
    }
}
