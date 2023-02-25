package me.gabytm.minecraft.mastereconomy.api.platform;

import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public interface Platform {

    @NotNull Path getDataFolderPath();

    @NotNull Logger logger();

}
