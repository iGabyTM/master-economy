package me.gabytm.minecraft.mastereconomy.api.economy;

import org.jetbrains.annotations.NotNull;

public interface Economy {

    @NotNull String getName();

    double getStartingBalance();

}
