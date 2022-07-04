package ru.nsu.fit.sokolova.dis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ModelDao {
    String getInsertStatement();
    PreparedStatement getPreparedStatement(Connection connection) throws SQLException;
}

