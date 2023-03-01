package me.gabytm.minecraft.mastereconomy.platform.bungee;

import me.gabytm.minecraft.mastereconomy.api.platform.Platform;
import me.gabytm.minecraft.mastereconomy.storage.StorageManager;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class MasterEconomyBungee extends Plugin implements Platform {

    private Logger slf4jLogger;

    private StorageManager storageManager;

    @Override
    public void onLoad() {
        slf4jLogger = LoggerFactory.getLogger(MasterEconomyBungee.class);
    }

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();
        storageManager = new StorageManager(this);
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
