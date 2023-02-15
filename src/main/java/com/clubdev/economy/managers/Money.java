package com.clubdev.economy.managers;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Money {
    @Getter @Setter private UUID uuid;
    @Getter @Setter private double money;

    public Money(UUID uuid, double money) {
        this.uuid = uuid;
        this.money = money;
    }
}
