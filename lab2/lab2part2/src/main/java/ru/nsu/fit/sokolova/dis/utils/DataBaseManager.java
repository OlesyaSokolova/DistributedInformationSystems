package ru.nsu.fit.sokolova.dis.utils;

import lombok.Getter;
import ru.nsu.fit.sokolova.dis.dao.impl.NodeDao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@Getter
public class DataBaseManager {
    private final static String DB_PROPERTIES_FILE = "/db.properties";
    private final static String DDL_FILE_PATH = "src/main/resources/create_db.sql";

    private final Connection connection;

    public DataBaseManager() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(DataBaseManager.class.getResourceAsStream(DB_PROPERTIES_FILE));
        String url = properties.getProperty("URL");
        String login = properties.getProperty("LOGIN");
        String password = properties.getProperty("PASSWORD");
        connection = DriverManager.getConnection(url, login, password);
        connection.setAutoCommit(false);
    }

    public void initDB() throws IOException, SQLException {
        byte[] DDLFileByteArray = Files.readAllBytes(Paths.get(DDL_FILE_PATH));
        int DDLFileByteArraySize = DDLFileByteArray.length;
        Statement statement = connection.createStatement();
        String SQLRequest = new String(DDLFileByteArray, 0, DDLFileByteArraySize);
        statement.execute(SQLRequest);
    }

    public void clearDB() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "truncate table tags cascade;" +
                "truncate table nodes cascade;";
        statement.execute(query);
        connection.commit();
    }
}
