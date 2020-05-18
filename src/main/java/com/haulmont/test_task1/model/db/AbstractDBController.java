package com.haulmont.test_task1.model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDBController<E, K> {

    private SingletonDatabase db;

    AbstractDBController() {
        db = SingletonDatabase.getInstance();
    }

    protected abstract void addResultsToList(ResultSet rs, List<E> out) throws SQLException;

    public abstract List<E> getAll();

    public abstract E getEntityById(K id);

    public abstract void update(E entity);

    public abstract boolean delete(K id);

    public abstract boolean create(E entity);

    ResultSet sendQuery(String sql) throws SQLException {
        Statement statement = db.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        statement.close();
        return rs;
    }
}