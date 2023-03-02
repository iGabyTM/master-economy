package me.gabytm.minecraft.mastereconomy.platform.bungee;

import me.gabytm.minecraft.mastereconomy.api.platform.Platform;
import me.gabytm.minecraft.mastereconomy.cache.CacheManager;
import me.gabytm.minecraft.mastereconomy.config.ConfigManager;
import me.gabytm.minecraft.mastereconomy.storage.StorageManager;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class MasterEconomyBungee extends Plugin implements Platform {

    private Logger slf4jLogger;

    private ConfigManager configManager;
    private StorageManager storageManager;
    private CacheManager cacheManager;

    @Override
    public void onLoad() {
        slf4jLogger = LoggerFactory.getLogger(MasterEconomyBungee.class);
    }

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();
        configManager = new ConfigManager(this);
        storageManager = new StorageManager(this);
        cacheManager = new CacheManager();

        cacheManager.connect(configManager.get().cache().getRedis());
        storageManager.getStorage().enable();
    }

    @Override
    public void onDisable() {
        storageManager.getStorage().disable();
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
