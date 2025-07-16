package com.example.it;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class DbUtil {
    private static final Logger LOGGER = Logger.getLogger(DbUtil.class.getName());
    final DataSource dataSource;

    public DbUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void clearTables() throws SQLException {
        clearTable("todos");
    }

    public void clearTable(String tableName) throws SQLException {
        Connection conn = dataSource.getConnection();
        int deleted = conn.prepareStatement("delete from " + tableName + "")
                .executeUpdate();
        LOGGER.log(Level.INFO, "deleted {0} records from {1} ", new Object[] { deleted, tableName });
    }

    public void assertCount(String tableName, int count) throws SQLException {
        Connection conn = dataSource.getConnection();
        ResultSet rs = conn.prepareStatement("select count(*) from " + tableName + "")
                .executeQuery();
        int rowCount = 0;
        if (rs.next()) {
            rowCount = rs.getInt(1);
        }
        assertEquals(count, rowCount);
    }
}
