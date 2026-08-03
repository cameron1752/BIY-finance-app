package com.biy.finance.app.financeapp.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void testConnection() throws SQLException {
        // Assert that the data source bean was successfully injected
        assertThat(dataSource).isNotNull();

        // Attempt to establish a live connection to your local PostgreSQL
        try (Connection connection = dataSource.getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.isValid(2)).isTrue();
            System.out.println("成功！ Database connection established: " + connection.getMetaData().getURL());
        }
    }
}

