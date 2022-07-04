package ru.nsu.fit.sokolova.dis.inserters.impl;

import ru.nsu.fit.sokolova.dis.dao.impl.NodeDao;
import ru.nsu.fit.sokolova.dis.dao.impl.TagDao;
import ru.nsu.fit.sokolova.dis.inserters.Inserter;
import ru.nsu.fit.sokolova.dis.models.Node;
import ru.nsu.fit.sokolova.dis.models.Tag;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchInserter extends Inserter {

    private Statement statement;
    private final int INSERT_LIMIT = 1000;

    @Override
    public void insert(Node node, Connection connection) throws SQLException {
        this.connection = connection;

        if(statement == null) {
            statement = connection.createStatement();
        }
        NodeDao nodeDao = new NodeDao(node);
        statement.addBatch(nodeDao.getInsertStatement());
        insertionsCounter++;

        for (Tag tag : node.getTag()) {
            TagDao tagDao = new TagDao(tag, node.getId());
            statement.addBatch(tagDao.getInsertStatement());
            insertionsCounter++;
        }
        if(insertionsCounter == INSERT_LIMIT) {
            executeBatch();
            statement = null;
        }
    }

    public void executeBatch() throws SQLException {
        if(statement != null) {
            long start = System.currentTimeMillis();
            statement.executeBatch();
            long end =  System.currentTimeMillis();
            timeSpentMillis += (end - start);
        }
    }
}
