package com.haulmont.test_task1.model.db;

import com.haulmont.test_task1.model.entities.Client;
import com.haulmont.test_task1.utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientsDBController extends AbstractDBController<Client, Long> {

    public ClientsDBController() {
        super();
    }

    @Override
    protected void addResultsToList(ResultSet rs, List<Client> out) {
        try {
            while (rs.next()) {
                out.add(new Client(rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }
        } catch (SQLException e) {
            Log.printSQLEx("ClientDBController", "addResultsToList", e.getMessage());
        }
    }

    @Override
    public List<Client> getAll() {
        ArrayList<Client> clients = new ArrayList<>();
        try {
            ResultSet rs = sendQuery("SELECT * FROM Clients");
            addResultsToList(rs, clients);
        } catch (SQLException e) {
            Log.printSQLEx("ClientDBController", "getAll", e.getMessage());
        }
        return clients;
    }

    @Override
    public Client getEntityById(Long id) {
        Client client = null;
        try {
            ResultSet rs = sendQuery("SELECT * FROM Clients WHERE id =" + id);
            if (rs.next()) {
                client = new Client(rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                );
            }
        } catch (SQLException e) {
            Log.printSQLEx("ClientDBController", "getEntityById", e.getMessage());
        }
        return client;
    }

    @Override
    public void update(Client entity) {
        try {
            sendQuery("UPDATE Clients SET "
                    + "firstName = '" + entity.getFirstName()
                    + "', middleName = '" + entity.getMiddleName()
                    + "', lastName = '" + entity.getLastName()
                    + "', phoneNumber = '" + entity.getPhoneNumber()
                    + "' WHERE id = " + entity.getId()
            );
        } catch (SQLException e) {
            Log.printSQLEx("ClientDBController", "update", e.getMessage());
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            sendQuery("DELETE FROM Clients WHERE id = " + id);
            return true;
        } catch (SQLException e) {
            Log.printSQLEx("ClientDBController", "delete", e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public boolean create(Client entity) {
        try {
            sendQuery("INSERT INTO Clients (firstName, middleName, lastName, phoneNumber)" +
                    " VALUES ('"
                    + entity.getFirstName() + "', '"
                    + entity.getMiddleName() + "', '"
                    + entity.getLastName() + "', '"
                    + entity.getPhoneNumber() + "')"
            );
            return true;
        } catch (SQLException e) {
            Log.printSQLEx("ClientDBController", "create", e.getMessage());
        }
        return false;
    }
}
