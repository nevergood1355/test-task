package com.haulmont.test_task1.model.db;

import com.haulmont.test_task1.model.entities.Client;
import com.haulmont.test_task1.model.entities.Mechanic;
import com.haulmont.test_task1.utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MechanicsDBController extends AbstractDBController<Mechanic, Long> {

    public MechanicsDBController() {
        super();
    }

    public List<Mechanic> find(String pattern) {
        ArrayList<Mechanic> filter = new ArrayList<>();
        try {
            ResultSet rs;
            if (pattern.isEmpty())
                rs = sendQuery("SELECT * FROM Mechanics");

            else
                rs = sendQuery("SELECT * FROM Mechanics WHERE " +
                        "LOWER(firstName) LIKE LOWER('%" + pattern +
                        "%') OR LOWER(middleName) LIKE LOWER('%" + pattern +
                        "%') OR LOWER(lastName) LIKE LOWER('%" + pattern + "%')");

            addResultsToList(rs, filter);
        } catch (SQLException e) {
            System.out.println("MechanicsDBController::find SQLEx. message:\n" + e.getMessage());
        }

        return filter;
    }

    @Override
    protected void addResultsToList(ResultSet rs, List<Mechanic> out) {
        try {
            while (rs.next()) {
                out.add(new Mechanic(rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getLong(5)
                ));
            }
        } catch (SQLException e) {
            Log.printSQLEx("MechanicsDBController", "addResultsToList", e.getMessage());
        }
    }

    @Override
    public List<Mechanic> getAll() {
        ArrayList<Mechanic> mechanics = new ArrayList<>();
        try {
            ResultSet rs = sendQuery("SELECT * FROM Mechanics");
            addResultsToList(rs, mechanics);
        } catch (SQLException e) {
            Log.printSQLEx("MechanicsDBController", "getAll", e.getMessage());
        }
        return mechanics;
    }

    @Override
    public Mechanic getEntityById(Long id) {
        Mechanic mechanic = null;
        try {
            ResultSet rs = sendQuery("SELECT * FROM Mechanics WHERE id =" + id);
            if (rs.next()) {
                mechanic = new Mechanic(rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getLong(5)
                );
            }
        } catch (SQLException e) {
            Log.printSQLEx("MechanicsDBController", "getEntityById", e.getMessage());
        }
        return mechanic;
    }

    @Override
    public void update(Mechanic entity) {
        try {
            sendQuery("UPDATE Mechanics SET "
                    + "firstName = '" + entity.getFirstName()
                    + "', middleName = '" + entity.getMiddleName()
                    + "', lastName = '" + entity.getLastName()
                    + "', hourlyWage = '" + entity.getHourlyWage()
                    + "' WHERE id = " + entity.getId()
            );
        } catch (SQLException e) {
            Log.printSQLEx("MechanicsDBController", "update", e.getMessage());
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            sendQuery("DELETE FROM Mechanics WHERE id = " + id);
            return true;
        } catch (SQLException e) {
            Log.printSQLEx("MechanicsDBController", "delete", e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public boolean create(Mechanic entity) {
        try {
            sendQuery("INSERT INTO Mechanics (firstName, middleName, lastName, hourlyWage)" +
                    " VALUES ('"
                    + entity.getFirstName() + "', '"
                    + entity.getMiddleName() + "', '"
                    + entity.getLastName() + "', '"
                    + entity.getHourlyWage() + "')"
            );
            return true;
        } catch (SQLException e) {
            Log.printSQLEx("MechanicsDBController", "create", e.getMessage());
        }
        return false;
    }


}
