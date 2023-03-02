package me.gabytm.minecraft.mastereconomy.cache.impl;

import me.gabytm.minecraft.mastereconomy.cache.Cache;
import me.gabytm.minecraft.mastereconomy.common.config.RedisConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.Protocol;

public class RedisCache extends Cache<RedisConfig> {

    private JedisPooled jedis;

    @Override
    public boolean connect(@NotNull final RedisConfig config) {
        if (jedis != null) {
            jedis.close();
        }

        this.jedis = new JedisPooled(
                new GenericObjectPoolConfig<>(),
                config.getHost(),
                config.getPort(),
                config.getTimeout(),
                config.getUser(),
                config.getPassword(),
                Protocol.DEFAULT_DATABASE,
                config.isSsl()
        );

        return true;
    }

}
