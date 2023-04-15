package me.casper.ffa.mysql;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class MySQL {
    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;

    private HikariDataSource dataSource;

    public void connect() {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(10);
        dataSource = new HikariDataSource(config);
    }

    private synchronized static List<Map<String, JsonElement>> parseResultSetToList(ResultSet resultSet) throws SQLException {
        List<Map<String, JsonElement>> list = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            JsonObject jsonObject = new JsonObject();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(i);
                if (value != null) {
                    jsonObject.addProperty(columnName, value.toString());
                } else {
                    jsonObject.add(columnName, JsonNull.INSTANCE);
                }
            }
            Map<String, JsonElement> map = new Gson().fromJson(jsonObject, new TypeToken<Map<String, JsonElement>>(){}.getType());
            list.add(map);
        }

        return list;
    }

    public CompletableFuture<List<Map<String, JsonElement>>> getResult(@NonNull String query, Object... values) {
        if (isClosed()) {
            return CompletableFuture.failedFuture(new RuntimeException("Not connected to MySQL!"));
        }

        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement stmt = connection.prepareStatement(query)) {
                for (int i = 0; i < values.length; i++) {
                    stmt.setObject(1 + i, values[i].toString());
                }

                ResultSet resultSet = stmt.executeQuery();
                final List<Map<String, JsonElement>> result = parseResultSetToList(resultSet);
                resultSet.close();

                return result;
            } catch (SQLException exception) {
                if (exception.getMessage().contains("Unknown character set")) {
                    // Try again with utf8
                    return getResult(query.replace("utf8mb4", "utf8"), values).join();
                } else {
                    throw new RuntimeException("Failed to execute query: " + query, exception);
                }
            }
        });
    }

    public CompletableFuture<Void> update(@NonNull String query, Object... values) {
        if (isClosed()) {
            return CompletableFuture.failedFuture(new RuntimeException("Not connected to MySQL!"));
        }

        return CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement stmt = connection.prepareStatement(query)) {
                for (int i = 0; i < values.length; i++) {
                    stmt.setObject(1 + i, values[i]);
                }
                stmt.executeUpdate();
            } catch (SQLException exception) {
                if (exception.getMessage().contains("Unknown character set")) {
                    // Try again with utf8
                    update(query.replace("utf8mb4", "utf8"), values).join();
                } else {
                    throw new RuntimeException("Failed to execute query: " + query, exception);
                }
            }
        }).exceptionally(throwable -> {
            throw new RuntimeException("Failed to execute query: " + query, throwable.getCause());
        });
    }


    public void createTables() {
        update("""
                CREATE TABLE IF NOT EXISTS `userdata`
                (
                    `uniqueId` VARCHAR(36) NOT NULL PRIMARY KEY,
                    `money`    INT(10)     NOT NULL,
                    `exp`      INT(10)     NOT NULL
                ) DEFAULT CHARSET = utf8mb4""").join();
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to get MySQL connection!", exception);
        }
    }

    public boolean isClosed() {
        return dataSource.isClosed();
    }

    public void disconnect() {
        dataSource.close();
    }
}