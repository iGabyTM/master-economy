package me.gabytm.minecraft.mastereconomy.cache;

import me.gabytm.minecraft.mastereconomy.cache.impl.RedisCache;
import org.jetbrains.annotations.NotNull;

public class CacheManager {

    private Cache<?> cache;

    public CacheManager() {
        cache = new RedisCache();
    }

    public <C> void connect(@NotNull final C config) {
        ((Cache<C>) cache).connect(config);
    }

}
