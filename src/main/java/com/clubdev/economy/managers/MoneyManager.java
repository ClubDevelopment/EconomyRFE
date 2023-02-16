package com.clubdev.economy.managers;

import cn.nukkit.IPlayer;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.scheduler.TaskHandler;
import com.clubdev.economy.EconomyRFE;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MoneyManager {
    private EconomyRFE main;
    public MoneyManager(EconomyRFE main) {
        this.main = main;
        this.databaseSetup();
    }
    //Methods with UUID
    private @Nullable CompletableFuture<Money> getRawMoney(UUID uuid) {
        CompletableFuture<Connection> db = main.getDatabase().getConnection();
        return db.thenApplyAsync(connection -> {
            try {
                PreparedStatement pre = connection.prepareStatement("SELECT * FROM `Money` WHERE `uuid`=?");
                pre.setString(1, uuid.toString());
                ResultSet result = pre.executeQuery();
                double money = result.getDouble(2);

                return new Money(uuid, money);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        });
    }

   public double getMoney(UUID uuid) {
        return this.getRawMoney(uuid).join().getMoney();
   }

    public void giveMoney(UUID uuid, double addedMoney) {
        double newMoney = this.getMoney(uuid) + addedMoney;
        this.setMoney(uuid, newMoney);
    }

    public void reduceMoney(UUID uuid, double reducedMoney) {
        double newMoney = this.getMoney(uuid) - reducedMoney;
        this.setMoney(uuid, newMoney);
    }

    public TaskHandler setMoney(UUID uuid, double money) {
        return main.getServer().getScheduler().scheduleAsyncTask(main, new AsyncTask() {
            @Override
            public void onRun() {
                CompletableFuture<Connection> db = main.getDatabase().getConnection();
                db.thenAcceptAsync((connection -> {
                    try {
                        PreparedStatement inspre = connection.prepareStatement("INSERT INTO 'Money'('uuid', 'money') VALUES (?,?)");
                        inspre.setString(1, String.valueOf(uuid));
                        inspre.setDouble(2, money);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                })).join();
            }
        });
    }

    //Methods with player class
    public double getMoney(IPlayer player) {
        return this.getMoney(player.getUniqueId());
    }

    public void setMoney(IPlayer player, double money) {
        this.setMoney(player.getUniqueId(), money);
    }

    public void giveMoney(IPlayer player, double money) {
        this.giveMoney(player.getUniqueId(), money);
    }

    public void reduceMoney(IPlayer player, double money) {
        this.reduceMoney(player.getUniqueId(), money);
    }

    private void databaseSetup() {
        main.getServer().getScheduler().scheduleAsyncTask(main, new AsyncTask() {
            @Override
            public void onRun() {
                main.getDatabase().getConnection().thenAcceptAsync((connection -> {
                    try {
                        connection.createStatement().executeUpdate(String.join("",
                                "CREATE TABLE if not exists `Money` ",
                                "( `uuid` TEXT NOT NULL UNIQUE , `money` DOUBLE NOT NULL );"
                        ));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                })).join();
            }
        });
    }
}
