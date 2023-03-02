package me.gabytm.minecraft.mastereconomy.config;

import me.gabytm.minecraft.mastereconomy.api.platform.Platform;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    private final Path configPath;
    private final YamlConfigurationLoader loader;
    private final Logger logger;

    private Config config;

    public ConfigManager(@NotNull final Platform platform) {
        this.configPath = platform.getDataFolderPath().resolve("config.yml");
        this.loader = YamlConfigurationLoader.builder()
                .path(configPath)
                .indent(2)
                .nodeStyle(NodeStyle.BLOCK)
                .build();
        this.logger = platform.logger();
        reload();
    }

    public void reload() {
        try {
            final CommentedConfigurationNode node = loader.load();
            config = node.get(Config.class);

            if (!Files.exists(configPath)) {
                logger.warn("Could not find config, creating it @ " + configPath);
                node.set(Config.class, config);
                loader.save(node);
            }
        } catch (ConfigurateException e) {
            logger.error("Could not load config", e);
        }
    }

    public Config get() {
        return config;
    }

}
