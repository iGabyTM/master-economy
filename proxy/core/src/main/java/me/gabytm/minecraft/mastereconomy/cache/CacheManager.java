package me.gabytm.minecraft.mastereconomy.cache;

import me.gabytm.minecraft.mastereconomy.cache.impl.RedisCache;

public class CacheManager {

    private Cache cache;

    public CacheManager() {
        cache = new RedisCache();
    }

}
