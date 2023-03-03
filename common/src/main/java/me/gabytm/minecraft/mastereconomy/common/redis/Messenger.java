package me.gabytm.minecraft.mastereconomy.common.redis;

import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.JedisPubSub;

public abstract class Messenger extends JedisPubSub {

    protected final JedisPooled jedis;

    public Messenger(@NotNull final JedisPooled jedis) {
        this.jedis = jedis;
    }

    public abstract void init();

}
