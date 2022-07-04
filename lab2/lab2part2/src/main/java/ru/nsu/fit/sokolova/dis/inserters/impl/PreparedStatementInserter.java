package ru.nsu.fit.sokolova.dis.inserters.impl;

import ru.nsu.fit.sokolova.dis.dao.impl.NodeDao;
import ru.nsu.fit.sokolova.dis.dao.impl.TagDao;
import ru.nsu.fit.sokolova.dis.inserters.Inserter;
import ru.nsu.fit.sokolova.dis.models.Node;
import ru.nsu.fit.sokolova.dis.models.Tag;

import java.sql.*;

public class PreparedStatementInserter extends Inserter {

    @Override
    public void insert(Node node, Connection connection) throws SQLException {
        this.connection = connection;

        NodeDao nodeDao = new NodeDao(node);
        PreparedStatement nodePreparedStatement = nodeDao.getPreparedStatement(connection);

        long start = System.currentTimeMillis();
        nodePreparedStatement.execute();
        long end =  System.currentTimeMillis();
        timeSpentMillis += (end - start);
        insertionsCounter++;

        for (Tag tag : node.getTag()) {
            TagDao tagDao = new TagDao(tag, node.getId());
            PreparedStatement tagPreparedStatement = tagDao.getPreparedStatement(connection);

            start = System.currentTimeMillis();
            tagPreparedStatement.execute();
            end =  System.currentTimeMillis();
            timeSpentMillis += (end - start);
            insertionsCounter++;
        }
    }
}
