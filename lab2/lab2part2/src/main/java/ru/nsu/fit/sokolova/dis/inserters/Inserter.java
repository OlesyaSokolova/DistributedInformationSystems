package ru.nsu.fit.sokolova.dis.inserters;

import ru.nsu.fit.sokolova.dis.models.Node;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Inserter {

    protected int insertionsCounter = 0;
    protected long timeSpentMillis = 0;
    protected Connection connection;

    public abstract void insert(Node node, Connection connection) throws SQLException;

/*
    Since millis is a shortened engineering term for milliseconds
     and milli stands for 1/1000th there are 1000 milliseconds in one second.
     Therefore to count seconds divide millis by 1000.
*/

    public double getSpeed() {
        double timeSpentSeconds = (timeSpentMillis / 1000.0);
        double speed = -1;
        if(timeSpentSeconds > 0) {
            speed = insertionsCounter/timeSpentSeconds;
        }
        return speed;
    }

    public void commitChanges() throws SQLException {
        if(connection != null) {
            connection.commit();
        }
    }
}
