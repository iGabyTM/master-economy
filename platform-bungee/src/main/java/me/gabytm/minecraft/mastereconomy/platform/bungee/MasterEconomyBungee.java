package me.gabytm.minecraft.mastereconomy.platform.bungee;

import me.gabytm.minecraft.mastereconomy.api.platform.Platform;
import me.gabytm.minecraft.mastereconomy.storage.StorageManager;
import net.md_5.bungee.api.plugin.Plugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class MasterEconomyBungee extends Plugin implements Platform {

    private Logger log4jLogger;

    private StorageManager storageManager;

    @Override
    public void onLoad() {
        log4jLogger = LogManager.getLogger(MasterEconomyBungee.class);
    }

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();
        storageManager = new StorageManager(this);
    }

    @Override
    public @NotNull Path getDataFolderPath() {
        return getDataFolder().toPath();
    }

    @Override
    public @NotNull Logger logger() {
        return log4jLogger;
    }

}
