package me.gabytm.minecraft.mastereconomy.common.redis;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import redis.clients.jedis.JedisPooled;

import java.util.UUID;

public abstract class ServerMessenger extends Messenger {

    public ServerMessenger(@NotNull final JedisPooled jedis) {
        super(jedis);
    }

    public abstract void requestBalances(@NotNull final UUID uuid);

    public abstract void modifyBalance(
            @NotNull final UUID uuid, @NotNull final Method method,
            @NotNull final String economy, @Range(from = 0L, to = Long.MAX_VALUE) final double amount
    );

}
