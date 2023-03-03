package me.gabytm.minecraft.mastereconomy.platform.bungee;

import me.gabytm.minecraft.mastereconomy.api.MasterEconomyAPI;
import me.gabytm.minecraft.mastereconomy.api.MasterEconomyAPIImpl;
import me.gabytm.minecraft.mastereconomy.api.platform.Platform;
import me.gabytm.minecraft.mastereconomy.cache.CacheManager;
import me.gabytm.minecraft.mastereconomy.common.redis.ProxyMessenger;
import me.gabytm.minecraft.mastereconomy.config.ConfigManager;
import me.gabytm.minecraft.mastereconomy.redis.ProxyMessengerImpl;
import me.gabytm.minecraft.mastereconomy.storage.StorageManager;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class MasterEconomyBungee extends Plugin implements Platform {

    private Logger slf4jLogger;

    private ConfigManager configManager;
    private StorageManager storageManager;
    private CacheManager cacheManager;

    private MasterEconomyAPI api;
    private ProxyMessenger proxyMessenger;

    @Override
    public void onLoad() {
        slf4jLogger = LoggerFactory.getLogger(MasterEconomyBungee.class);
        configManager = new ConfigManager(this);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onEnable() {
        getDataFolder().mkdirs();

        storageManager = new StorageManager(this);
        storageManager.getStorage().enable();

        cacheManager = new CacheManager(storageManager);
        cacheManager.connect(configManager.get().cache().getRedis());

        api = new MasterEconomyAPIImpl(storageManager, cacheManager);
        proxyMessenger = new ProxyMessengerImpl(cacheManager.getJedis(), api);
        getProxy().getScheduler().schedule(this, proxyMessenger::init, 2, TimeUnit.SECONDS);
    }

    @Override
    public void onDisable() {
        storageManager.getStorage().disable();
        cacheManager.disconnect();
    }

    @Override
    public @NotNull Path getDataFolderPath() {
        return getDataFolder().toPath();
    }

    @Override
    public @NotNull Logger logger() {
        return slf4jLogger;
    }

}
