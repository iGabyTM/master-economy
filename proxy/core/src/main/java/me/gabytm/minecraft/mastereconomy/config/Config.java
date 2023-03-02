package me.gabytm.minecraft.mastereconomy.config;

import me.gabytm.minecraft.mastereconomy.common.config.RedisConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@SuppressWarnings({"FieldMayBeFinal", "unused"})
@ConfigSerializable
public class Config {

    private Cache cache = new Cache();

    public Cache cache() {
        return cache;
    }

    @ConfigSerializable
    public static class Cache {

        private RedisConfig redis = new RedisConfig();

        public RedisConfig getRedis() {
            return redis;
        }

    }

}
