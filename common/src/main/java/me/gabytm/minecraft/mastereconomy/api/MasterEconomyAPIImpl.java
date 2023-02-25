package me.gabytm.minecraft.mastereconomy.api;

import me.gabytm.minecraft.mastereconomy.api.economy.Economy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MasterEconomyAPIImpl implements MasterEconomyAPI {

    @Override
    public @Nullable Economy getEconomy(@NotNull String name) {
        return null;
    }

    @Override
    public @NotNull List<@NotNull Economy> getEconomies() {
        return Collections.emptyList();
    }

    @Override
    public double getBalance(@NotNull UUID uuid, @NotNull String economy) {
        return 0;
    }

    @Override
    public @Nullable Map<@NotNull String, @NotNull Double> getBalances(@Nullable UUID uuid) {
        return null;
    }

}
