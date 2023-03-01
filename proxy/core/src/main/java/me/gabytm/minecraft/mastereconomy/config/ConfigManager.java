package me.gabytm.minecraft.mastereconomy.config;

import me.gabytm.minecraft.mastereconomy.api.platform.Platform;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

public class ConfigManager {

    private final YamlConfigurationLoader loader;
    private final Logger logger;

    private Config config;

    public ConfigManager(@NotNull final Platform platform) {
        this.loader = YamlConfigurationLoader.builder()
                .path(platform.getDataFolderPath().resolve("config.yml"))
                .build();
        this.logger = platform.logger();
    }

    public void reload() {
        try {
            config = loader.load().get(Config.class);
        } catch (ConfigurateException e) {
            logger.error("Could not load config", e);
        }
    }

    public Config get() {
        return config;
    }

}
