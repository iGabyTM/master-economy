package me.gabytm.minecraft.mastereconomy.spigot;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import me.gabytm.minecraft.mastereconomy.common.redis.ServerMessenger;
import me.gabytm.minecraft.mastereconomy.spigot.commands.BalanceCommand;
import me.gabytm.minecraft.mastereconomy.spigot.listener.PlayerJoinListener;
import me.gabytm.minecraft.mastereconomy.spigot.redis.ServerMessengerImpl;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.Protocol;

import java.util.concurrent.TimeUnit;

public class MasterEconomySpigot extends JavaPlugin {

    private Cache<String, Double> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(30, TimeUnit.SECONDS)
            .removalListener(notification -> getLogger().info(String.format("%s:%s removed with reason %s", notification.getKey(), notification.getValue(), notification.getCause())))
            .build();

    private JedisPooled jedis;
    private ServerMessenger serverMessenger;

    @Override
    public void onEnable() {
        this.jedis = new JedisPooled(
                new GenericObjectPoolConfig<>(),
                Protocol.DEFAULT_HOST,
                Protocol.DEFAULT_PORT,
                "default",
                "test_password"
        );
        this.serverMessenger = new ServerMessengerImpl(jedis, this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        final BukkitCommandManager<CommandSender> commandManager = BukkitCommandManager.create(this);
        commandManager.registerCommand(new BalanceCommand(serverMessenger));
    }

    public Cache<String, Double> getCache() {
        return cache;
    }

    public ServerMessenger getServerMessenger() {
        return serverMessenger;
    }

}
