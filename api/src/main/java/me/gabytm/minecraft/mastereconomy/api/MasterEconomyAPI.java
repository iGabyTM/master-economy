package me.gabytm.minecraft.mastereconomy.api;

import me.gabytm.minecraft.mastereconomy.api.economy.Economy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MasterEconomyAPI {

    @Nullable Economy getEconomy(@NotNull final String name);

    @NotNull List<@NotNull Economy> getEconomies();

    double getBalance(@NotNull final UUID uuid, @NotNull String economy);

    @Nullable Map<@NotNull String, @NotNull Double> getBalances(@Nullable final UUID uuid);

}
