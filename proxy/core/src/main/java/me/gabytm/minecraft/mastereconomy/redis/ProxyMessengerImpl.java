package me.gabytm.minecraft.mastereconomy.redis;

import me.gabytm.minecraft.mastereconomy.api.MasterEconomyAPI;
import me.gabytm.minecraft.mastereconomy.api.util.Constant;
import me.gabytm.minecraft.mastereconomy.common.redis.Channel;
import me.gabytm.minecraft.mastereconomy.common.redis.Method;
import me.gabytm.minecraft.mastereconomy.common.redis.ProxyMessenger;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisPooled;

import java.util.Map;
import java.util.UUID;

public class ProxyMessengerImpl extends ProxyMessenger {

    private final MasterEconomyAPI api;

    public ProxyMessengerImpl(@NotNull final JedisPooled jedis, @NotNull final MasterEconomyAPI api) {
        super(jedis);
        this.api = api;
    }

    @Override
    public void init() {
        jedis.subscribe(this, Channel.BALANCE_CHANGE, Channel.BALANCE_REQUEST, Channel.BALANCE_UPDATE);
    }

    @Override
    public void broadcastBalance(
            @NotNull final String uuid, @NotNull final String economy,
            @NotNull final String balance
    ) {
        jedis.publish(Channel.BALANCE_UPDATE, String.format("%s:%s:%s", uuid, economy, balance));
    }

    @Override
    public void onMessage(String channel, String message) {
        final String[] parts = message.split(":");

        switch (channel) {
            case Channel.BALANCE_CHANGE: {
                final String uuid = parts[0];
                final Method method = Method.valueOf(parts[1]);
                final double amount = Double.parseDouble(parts[2]);
                final String economy = parts[3];

                // Calculate the new balance
                final double balance = api.getBalance(UUID.fromString(uuid), economy);
                final double newBalance = method.apply(balance, amount);
                final String newBalanceString = Constant.DECIMAL_FORMAT.format(newBalance);

                // Update the storage & cache and broadcast the new balance
                api.setBalance(UUID.fromString(uuid), economy, newBalance);
                broadcastBalance(uuid, economy, newBalanceString);
                break;
            }

            case Channel.BALANCE_REQUEST: {
                final UUID uuid = UUID.fromString(parts[0]);

                for (final Map.Entry<String, Double> balance : api.getBalances(uuid).entrySet()) {
                    broadcastBalance(parts[0], balance.getKey(), Constant.DECIMAL_FORMAT.format(balance.getValue()));
                }
                break;
            }

            case Channel.BALANCE_UPDATE: {
                final UUID uuid = UUID.fromString(parts[0]);
            }
        }
    }

}
