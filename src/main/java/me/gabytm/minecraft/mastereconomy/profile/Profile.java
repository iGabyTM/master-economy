package me.gabytm.minecraft.mastereconomy.profile;

import me.gabytm.minecraft.mastereconomy.economy.Economy;

import java.util.Map;
import java.util.UUID;

public final class Profile {

    private final UUID uuid;
    private final Map<Economy, Double> balances;

    public Profile(UUID uuid, Map<Economy, Double> balances) {
        this.uuid = uuid;
        this.balances = balances;
    }

}
