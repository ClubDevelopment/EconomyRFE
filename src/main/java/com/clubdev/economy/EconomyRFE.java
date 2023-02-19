package com.clubdev.economy;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import com.clubdev.economy.commands.*;
import com.clubdev.economy.database.IDatabase;
import com.clubdev.economy.database.SQLite;
import com.clubdev.economy.managers.MoneyManager;
import lombok.Getter;

import java.io.File;
import java.util.Arrays;

public class EconomyRFE extends PluginBase {
    public static EconomyRFE instance;
    public PluginLogger log;
    @Getter IDatabase database;
    @Getter MoneyManager moneyManager;

    @Override
    public void onEnable() {
        this.getDataFolder().mkdir();
        database = new SQLite(new File(getDataFolder(), "database.db"), this);
        moneyManager = new MoneyManager(this);
        getServer().getCommandMap().registerAll("Economy", Arrays.asList(
                new MoneyCommand(this),
                new PayCommand(this),
                new AddMoneyCommand(this),
                new ReduceMoneyCommand(this),
                new SeeMoneyCommand(this),
                new SeeMoneyCommand(this)
        ));
    }
}