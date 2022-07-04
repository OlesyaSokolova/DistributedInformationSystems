package ru.nsu.fit.sokolova.dis.inserters.impl;

import ru.nsu.fit.sokolova.dis.dao.impl.NodeDao;
import ru.nsu.fit.sokolova.dis.dao.impl.TagDao;
import ru.nsu.fit.sokolova.dis.inserters.Inserter;
import ru.nsu.fit.sokolova.dis.models.Node;
import ru.nsu.fit.sokolova.dis.models.Tag;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

// statement outside loop
// commit - убрать автокоммит, вынести вне цикла
// batch size == 1000

//100:
//Speed for strategy "STRING": 52.34375 insertions/seconds.
//Speed for strategy "PREPARED_STATEMENT": 95.44159544159545 insertions/seconds.
//Speed for strategy "BATCH": 4620.689655172414 insertions/seconds.

//100:
/*Speed for strategy "STRING": 985.2941176470588 insertions/seconds.
 Speed for strategy "PREPARED_STATEMENT": 1098.360655737705 insertions/seconds.
 Speed for strategy "BATCH": 8933.333333333334 insertions/seconds.

 10000:
Speed for strategy "STRING": 2100.6917755572636 insertions/seconds.
Speed for strategy "PREPARED_STATEMENT": 2748.1146304675717 insertions/seconds.
Speed for strategy "BATCH": 9034.710743801654 insertions/seconds.
 */

public class StringInserter extends Inserter {

    @Override
    public void insert(Node node, Connection connection) throws SQLException {
        this.connection = connection;

        Statement statementForNode = connection.createStatement();
        NodeDao nodeDao = new NodeDao(node);

        long start = System.currentTimeMillis();
        statementForNode.execute(nodeDao.getInsertStatement());
        long end =  System.currentTimeMillis();
        timeSpentMillis += (end - start);
        insertionsCounter++;

        for (Tag tag : node.getTag()) {
            TagDao tagDao = new TagDao(tag, node.getId());
            Statement statementForTag = connection.createStatement();

            start = System.currentTimeMillis();
            statementForTag.execute(tagDao.getInsertStatement());
            end =  System.currentTimeMillis();
            timeSpentMillis += (end - start);
            insertionsCounter++;
        }
    }
}
