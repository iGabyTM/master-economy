package me.gabytm.minecraft.mastereconomy.spigot.redis;

import me.gabytm.minecraft.mastereconomy.api.util.Constant;
import me.gabytm.minecraft.mastereconomy.common.redis.Channel;
import me.gabytm.minecraft.mastereconomy.common.redis.Method;
import me.gabytm.minecraft.mastereconomy.common.redis.ServerMessenger;
import me.gabytm.minecraft.mastereconomy.spigot.MasterEconomySpigot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import redis.clients.jedis.JedisPooled;

import java.util.UUID;

public class ServerMessengerImpl extends ServerMessenger {

    private final MasterEconomySpigot plugin;

    public ServerMessengerImpl(@NotNull final JedisPooled jedis, @NotNull final MasterEconomySpigot plugin) {
        super(jedis);
        this.plugin = plugin;
    }

    @Override
    public void init() {
        jedis.subscribe(this, Channel.BALANCE_UPDATE);
    }

    @Override
    public void requestBalances(@NotNull UUID uuid) {
        jedis.publish(Channel.BALANCE_REQUEST, uuid.toString());
    }

    @Override
    public void modifyBalance(@NotNull UUID uuid, @NotNull Method method, @NotNull String economy, @Range(from = 0L, to = Long.MAX_VALUE) double amount) {
        jedis.publish(Channel.BALANCE_CHANGE, String.format("%s:%s:%s:%s", uuid, method, Constant.DECIMAL_FORMAT.format(amount), economy));
    }

    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equals(Channel.BALANCE_UPDATE)) {
            return;
        }

        final String[] parts = message.split(":");

        if (parts.length != 3) {
            plugin.getLogger().warning("Malformed message received on channel " + channel + ": '" + message + "'. Expected 3 parts but found " + parts.length);
            return;
        }

        final String uuidString = parts[0];
        final String economy = parts[1];
        final double balance = Double.parseDouble(parts[2]);
        plugin.getCache().put(String.format("%s:%s", uuidString, economy), balance);
    }

}
