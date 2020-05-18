package com.haulmont.test_task1;

import com.haulmont.test_task1.model.db.SingletonDatabase;
import com.haulmont.test_task1.model.entities.Client;
import com.haulmont.test_task1.model.entities.Mechanic;
import com.haulmont.test_task1.model.entities.Order;
import com.haulmont.test_task1.model.enums.Status;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDB {
    public static void main(String[] args) {
        SingletonDatabase database = SingletonDatabase.getInstance();
        try {
            //region Clients
            Statement st = database.createStatement();
            ResultSet rs = st.executeQuery("select * from Clients");
            System.out.println("\n table Clients");
            while (rs.next()) {
                System.out.println(new Client(rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)));
            }
            rs.close();
            //endregion

            //region Mechanics
            System.out.println("\n table Mechanics");
            rs = st.executeQuery("select * from Mechanics");
            while (rs.next()) {
                System.out.println(new Mechanic(rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getLong(5)));
            }
            //endregion

            //region Orders
            System.out.println("\n table Orders");
            rs = st.executeQuery("select * from Orders");
            while (rs.next()) {
                System.out.println(new Order(rs.getLong(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getDate(4),
                        rs.getLong(5),
                        rs.getLong(6),
                        rs.getLong(7),
                        Status.get(rs.getString(8))));
            }
            //endregion
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
