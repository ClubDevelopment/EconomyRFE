package com.clubdev.economy;

import cn.nukkit.scheduler.TaskHandler;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class API {
    private final EconomyRFE main;
    //Methods with UUID
    public double getMoney(UUID uuid) {
        return this.main.getMoneyManager().getMoney(uuid);
    }

    public TaskHandler setMoney(UUID uuid, double money) {
        return this.main.getMoneyManager().setMoney(uuid, money);
    }

    public void reduceMoney(UUID uuid, double money) {
        this.main.getMoneyManager().reduceMoney(uuid, money);
    }

    public void giveMoney(UUID uuid, double money) {
        this.main.getMoneyManager().giveMoney(uuid, money);
    }
    //Methods with Player

}
