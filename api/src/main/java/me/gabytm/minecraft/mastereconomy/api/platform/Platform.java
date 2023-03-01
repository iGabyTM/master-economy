package me.gabytm.minecraft.mastereconomy.api.platform;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.nio.file.Path;

public interface Platform {

    @NotNull Path getDataFolderPath();

    @NotNull Logger logger();

}
