package com.clubdev.economy.database;

import lombok.Getter;
import com.clubdev.economy.EconomyRFE;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@Getter
public class SQLite implements IDatabase {

    private final File databaseFile;
    private final EconomyRFE core;
    private Connection connection;


    public SQLite(@NotNull File databaseFile, @NotNull EconomyRFE core) {

        this.databaseFile = databaseFile;
        if (!(databaseFile.exists())) {
            try {
                databaseFile.createNewFile();
            } catch (IOException e) {
                core.getLogger().critical(e.getMessage());
            }
        }
        this.core = core;
    }

    @Override
    public @Nullable CompletableFuture<Connection> getConnection() {
        return CompletableFuture.supplyAsync(() -> {

            try {

                if (connection != null && !(connection.isClosed())){
                    return connection;
                }

                this.connection = DriverManager.getConnection(String.join("",
                        "jdbc:sqlite:",
                        databaseFile.getAbsolutePath()
                ));
                return connection;
            } catch (SQLException e) {
                core.getLogger().critical(e.getMessage());
                return null;
            }

        });
    }

}