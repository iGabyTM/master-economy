package me.gabytm.minecraft.mastereconomy.storage;

import me.gabytm.minecraft.mastereconomy.api.platform.Platform;
import me.gabytm.minecraft.mastereconomy.storage.impl.SQLiteStorage;
import org.jetbrains.annotations.NotNull;

public class StorageManager {

    private final Platform platform;

    private Storage storage;

    public StorageManager(@NotNull final Platform platform) {
        this.platform = platform;
        storage = new SQLiteStorage(platform);
    }

    public Storage getStorage() {
        return storage;
    }

}
