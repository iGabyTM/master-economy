package me.gabytm.minecraft.mastereconomy.storage.impl;

import me.gabytm.minecraft.mastereconomy.api.platform.Platform;
import me.gabytm.minecraft.mastereconomy.storage.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class SQLiteStorage extends Storage {

    private final Platform platform;

    private final Path databaseFilePath;
    private Connection connection;

    public SQLiteStorage(@NotNull final Platform platform) {
        this.platform = platform;
        this.databaseFilePath = platform.getDataFolderPath().resolve("database.sqlite.db");
    }

    private boolean connect() {
        // Establish connection
        try {
            if (connection != null && !connection.isClosed()) {
                return true;
            }

            if (!Files.exists(databaseFilePath)) {
                Files.createFile(databaseFilePath);
            }

            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFilePath.toAbsolutePath());
        } catch (ClassNotFoundException e) {
            platform.logger().error("[SQLite] Could not find driver", e);
            return false;
        } catch (SQLException e) {
            platform.logger().error("[SQLite] Could not connect to database", e);
            return false;
        } catch (IOException e) {
            platform.logger().error("[SQLite] Could not create or open " + databaseFilePath.toAbsolutePath(), e);
            return false;
        }

        // Create the table
        try {
            if (connection == null || connection.isClosed()) {
                return false;
            }

            final PreparedStatement statement = Query.CREATE_TABLE.prepare(connection);
            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            platform.logger().error("[SQLite] Could not create the table", e);
            return false;
        }
    }

    @Override
    public boolean enable() {
        return connect();
    }

    @Override
    public void disable() {
        try {
            if (connection == null || connection.isClosed()) {
                return;
            }

            connection.close();
        } catch (SQLException e) {
            platform.logger().error("[SQLite] Could not close the connection with the database", e);
        }
    }

    @Override
    public void updateBalance(@NotNull UUID uuid, @NotNull String economy, double balance) {
        if (!connect()) {
            return;
        }

        try (final PreparedStatement statement = Query.INSERT.prepare(connection)) {
            statement.setString(1, uuid.toString());
            statement.setString(2, economy);
            statement.setDouble(3, balance);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public @Nullable Double getBalance(@NotNull UUID uuid, @NotNull String economy) {
        if (!connect()) {
            return null;
        }

        try (final PreparedStatement query = Query.SELECT_ONE.prepare(connection)) {
            query.setString(1, uuid.toString());
            query.setString(2, economy);
            final ResultSet result = query.executeQuery();

            if (result.next()) {
                return result.getDouble("amount");
            }

            return 0.0;
        } catch (SQLException e) {
            platform.logger().error("[SQLite] Could not ger user's balance (" + uuid + ')', e);
            return null;
        }
    }

    @Override
    public @Nullable Map<@NotNull String, @NotNull Double> getUserBalances(@NotNull UUID uuid) {
        if (!connect()) {
            return null;
        }

        try (final PreparedStatement query = Query.SELECT_ALL.prepare(connection)) {
            query.setString(1, uuid.toString());
            final ResultSet result = query.executeQuery();

            final Map<String, Double> balances = new HashMap<>();

            while (result.next()) {
                balances.put(result.getString("economy"), result.getDouble("amount"));
            }

            return balances;
        } catch (SQLException e) {
            platform.logger().error("[SQLite] Could not ger user's balance (" + uuid + ')', e);
            return null;
        }
    }

    private enum Query {

        CREATE_TABLE,
        INSERT,
        SELECT_ALL,
        SELECT_ONE;

        private String query;

        static {
            final ClassLoader classLoader = Query.class.getProtectionDomain().getClassLoader();

            for (Query it : values()) {
                try (
                        final InputStream in = classLoader.getResourceAsStream("sql/sqlite/" + it.name().toLowerCase() + ".sql");
                        final Reader reader = new InputStreamReader(in);
                        final BufferedReader bufferedReader = new BufferedReader(reader);
                ) {
                    it.query = bufferedReader.lines().collect(Collectors.joining());
                } catch (IOException e) {
                    LoggerFactory.getLogger(Query.class).error("Could not load query " + it.name(), e);
                }
            }
        }

        public @NotNull PreparedStatement prepare(@NotNull Connection connection) throws SQLException {
            return connection.prepareStatement(query);
        }

    }

}
