package me.gabytm.minecraft.mastereconomy.storage;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public abstract class Storage {

    @ApiStatus.Internal
    public abstract boolean enable();

    @ApiStatus.Internal
    public abstract void disable();

    @ApiStatus.Internal
    public abstract void updateBalance(@NotNull final UUID uuid, @NotNull String economy, final double balance);

    @ApiStatus.Internal
    public abstract @Nullable Map<@NotNull String, @NotNull Double> getUserBalances(@NotNull final UUID uuid);

}
