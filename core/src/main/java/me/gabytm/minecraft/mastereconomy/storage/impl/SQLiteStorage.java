package me.gabytm.minecraft.mastereconomy.storage.impl;

import me.gabytm.minecraft.mastereconomy.api.platform.Platform;
import me.gabytm.minecraft.mastereconomy.storage.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SQLiteStorage extends Storage {

    private final Platform platform;

    private final Path databaseFilePath;
    private Connection connection;

    public SQLiteStorage(@NotNull final Platform platform) {
        this.platform = platform;
        this.databaseFilePath = platform.getDataFolderPath().resolve("database.sqlite.db");
        connect();
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

            this.connection = DriverManager.getConnection("jdbc:sqlite://" + databaseFilePath.toAbsolutePath());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return false;
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
    public @Nullable Map<@NotNull String, @NotNull Double> getUserBalances(@NotNull UUID uuid) {
        if (!connect()) {
            return null;
        }

        try (final PreparedStatement query = Query.SELECT.prepare(connection)) {
            final ResultSet result = query.executeQuery();

            if (result.next()) {
                final Map<String, Double> balances = new HashMap<>();

                while (result.next()) {
                    balances.put(result.getString("economy"), result.getDouble("amount"));
                }

                return balances;
            }

            return Collections.emptyMap();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private enum Query {

        CREATE_TABLE,
        INSERT,
        SELECT;

        private String query;

        static {
            final ClassLoader classLoader = Query.class.getProtectionDomain().getClassLoader();

            for (Query it : values()) {
                try {
                    final Path path = Paths.get(classLoader.getResource("sql/sqlite/" + it.name().toLowerCase()).toURI());
                    it.query = new String(Files.readAllBytes(path));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }

        public @NotNull PreparedStatement prepare(@NotNull Connection connection) throws SQLException {
            return connection.prepareStatement(query);
        }

    }

}
