package ru.nsu.fit.sokolova.dis.dao.impl;

import ru.nsu.fit.sokolova.dis.dao.ModelDao;
import ru.nsu.fit.sokolova.dis.models.Tag;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TagDao implements ModelDao {

    private final Tag tag;
    private final BigInteger nodeId;

    private final String PARAMETRIZED_STATEMENT =  "insert into tags(node_id, key, value) " +
            "values (?,       ?,   ?)";


    public TagDao(Tag tag, BigInteger nodeID) {
        this.tag = tag;
        this.nodeId = nodeID;
    }

    /*<tag k="name:ca" varchar(50) not null
     v="Novossibirsk"/> varchar(256) not null
     */

    @Override
    public String getInsertStatement() {
       String statement =  "insert into tags (node_id, key, value) " +
               "values (" +
               nodeId + ", " +
               "'" + tag.getK() + "'" + ", " +
               "'" + tag.getV() + "'" + ")";
        return statement;
    }

    /*<tag k="name:ca" varchar(50) not null
   v="Novossibirsk"/> varchar(256) not null
   */

    @Override
    public PreparedStatement getPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps =  connection.prepareStatement(PARAMETRIZED_STATEMENT);
        ps.setInt(1, nodeId.intValue());
        ps.setString(2, tag.getK());
        ps.setString(3, tag.getV());
        return ps;
    }
}
