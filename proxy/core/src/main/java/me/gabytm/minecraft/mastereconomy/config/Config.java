package me.gabytm.minecraft.mastereconomy.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class Config {

    private Cache cache;

    public Cache cache() {
        return cache;
    }

    @ConfigSerializable
    public static class Cache {

        private RedisConfig redis;

        public RedisConfig getRedis() {
            return redis;
        }

    }

}
