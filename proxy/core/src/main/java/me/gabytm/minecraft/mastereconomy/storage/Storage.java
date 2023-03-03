package me.gabytm.minecraft.mastereconomy.storage;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public abstract class Storage {

    @Internal
    public abstract boolean enable();

    @Internal
    public abstract void disable();

    @Internal
    public abstract void updateBalance(@NotNull final UUID uuid, @NotNull final String economy, final double balance);

    @Internal
    public abstract @Nullable Double getBalance(@NotNull final UUID uuid, @NotNull final String economy);

    @Internal
    public abstract @Nullable Map<@NotNull String, @NotNull Double> getUserBalances(@NotNull final UUID uuid);

}
