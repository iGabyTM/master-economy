package me.gabytm.minecraft.mastereconomy.cache;

import me.gabytm.minecraft.mastereconomy.common.config.RedisConfig;
import me.gabytm.minecraft.mastereconomy.storage.StorageManager;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.args.ExpiryOption;

public class CacheManager {

    private final StorageManager storageManager;

    private JedisPooled jedis;

    public CacheManager(@NotNull final StorageManager storageManager) {
        this.storageManager = storageManager;
    }

    private @NotNull String createKey(@NotNull final String uuid, @NotNull final String economy) {
        return String.format("%s:%s", uuid, economy);
    }

    public void connect(@NotNull final RedisConfig config) {
        disconnect();
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

        jedis.set("test", "hey");
    }

    public void disconnect() {
        if (jedis != null) {
            jedis.close();
        }
    }

    public JedisPooled getJedis() {
        return jedis;
    }

    public void cacheBalance(@NotNull final String uuid, @NotNull final String economy, @NotNull final String balance) {
        jedis.hset(uuid, economy, balance);
        jedis.expire(uuid, 300, ExpiryOption.NX);
    }

    public @Nullable String getBalance(@NotNull final String uuid, @NotNull final String economy) {
        return jedis.hget(uuid, economy);
    }

}
