package me.gabytm.minecraft.mastereconomy.api.platform;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public interface Platform {

    @NotNull Path getDataFolderPath();

}
