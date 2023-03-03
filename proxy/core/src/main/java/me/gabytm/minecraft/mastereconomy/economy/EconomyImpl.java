package me.gabytm.minecraft.mastereconomy.economy;

import me.gabytm.minecraft.mastereconomy.api.economy.Economy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

public class EconomyImpl implements Economy {

    private final String name;
    private final double startingBalance;

    @TestOnly
    public static Economy of(@NotNull final String name) {
        return new EconomyImpl(name, 0);
    }

    public EconomyImpl(@NotNull final String name, final double startingBalance) {
        this.name = name;
        this.startingBalance = startingBalance;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public double getStartingBalance() {
        return startingBalance;
    }

}
