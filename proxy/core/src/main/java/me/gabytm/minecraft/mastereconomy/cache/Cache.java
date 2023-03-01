package me.gabytm.minecraft.mastereconomy.cache;

import org.jetbrains.annotations.NotNull;

/**
 * @param <C> config
 */
public abstract class Cache<C> {

    public abstract boolean connect(@NotNull final C config);

}
