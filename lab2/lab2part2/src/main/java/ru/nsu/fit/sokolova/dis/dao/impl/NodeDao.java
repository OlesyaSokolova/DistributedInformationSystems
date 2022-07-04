package ru.nsu.fit.sokolova.dis.dao.impl;

import ru.nsu.fit.sokolova.dis.dao.ModelDao;
import ru.nsu.fit.sokolova.dis.models.Node;

import java.sql.*;

public class NodeDao implements ModelDao {

    private final Node node;
    private final String PARAMETRIZED_STATEMENT = "insert into nodes(id, version, timestamp, uid, username, changeset, lat, lon, visible) " +
            "values (?,  ?,       ?,         ?,   ?,        ?,         ?,   ?,   ?)";

    public NodeDao(Node node) {
        this.node = node;
    }

    /*<node id="27503928" bigint not null primary key
      version="36" bigint not null
      timestamp="2015-01-29T11:42:06Z" date not null
      uid="54441" bigint not null
      user="SMP" varchar (50) not null
      changeset="28484183" bigint not null
      lat="55.0282215" double precision not null
      lon="82.9234476"> double precision not null
      visible boolean
     */

    @Override
    public String getInsertStatement() {
        String statement =  "insert into nodes(id, version, timestamp, uid, username, changeset, lat, lon, visible) " +
                "values (" +
                node.getId() + ", " +
                node.getVersion() + ", " +
                "'" + node.getTimestamp() + "'" + ", " +
                node.getUid() + ", " +
                "'" + node.getUser() + "'" + ", " +
                node.getChangeset() + ", " +
                node.getLat() + ", " +
                node.getLat() + ", " +
                node.isVisible() + ")";
        return statement;
    }

    /*<node id="27503928" bigint not null primary key
     version="36" bigint not null
     timestamp="2015-01-29T11:42:06Z" date not null
     uid="54441" bigint not null
     user="SMP" varchar (50) not null
     changeset="28484183" bigint not null
     lat="55.0282215" double precision not null
     lon="82.9234476"> double precision not null
     visible boolean
    */
    @Override
    public PreparedStatement getPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps =  connection.prepareStatement(PARAMETRIZED_STATEMENT);
        ps.setInt(1, node.getId().intValue());
        ps.setInt(2, node.getVersion().intValue());
        ps.setDate(3, new Date(node.getTimestamp().toGregorianCalendar().getTime().getTime()));
        ps.setInt(4, node.getUid().intValue());
        ps.setString(5, node.getUser());
        ps.setInt(6, node.getChangeset().intValue());
        ps.setDouble(7, node.getLat());
        ps.setDouble(8, node.getLon());
        ps.setObject(9, node.isVisible(), Types.BOOLEAN);
        return ps;
    }
}
