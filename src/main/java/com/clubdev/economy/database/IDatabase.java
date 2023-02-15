package com.clubdev.economy.database;

import java.sql.Connection;
import java.util.concurrent.CompletableFuture;

public interface IDatabase {

    CompletableFuture<Connection> getConnection();

}