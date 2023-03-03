package me.gabytm.minecraft.mastereconomy.common.redis;

import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisPooled;

public abstract class ProxyMessenger extends Messenger {

    public ProxyMessenger(@NotNull final JedisPooled jedis) {
        super(jedis);
    }

    public abstract void broadcastBalance(
            @NotNull final String uuid, @NotNull final String economy,
            @NotNull final String balance
    );

}
