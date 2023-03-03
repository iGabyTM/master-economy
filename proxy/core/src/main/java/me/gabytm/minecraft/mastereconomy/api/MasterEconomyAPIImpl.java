package me.gabytm.minecraft.mastereconomy.api;

import me.gabytm.minecraft.mastereconomy.api.economy.Economy;
import me.gabytm.minecraft.mastereconomy.api.util.Constant;
import me.gabytm.minecraft.mastereconomy.cache.CacheManager;
import me.gabytm.minecraft.mastereconomy.economy.EconomyImpl;
import me.gabytm.minecraft.mastereconomy.storage.StorageManager;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MasterEconomyAPIImpl implements MasterEconomyAPI {

    private final StorageManager storageManager;
    private final CacheManager cacheManager;

    public MasterEconomyAPIImpl(StorageManager storageManager, CacheManager cacheManager) {
        this.storageManager = storageManager;
        this.cacheManager = cacheManager;
    }

    @Override
    public @Nullable Economy getEconomy(@NotNull final String name) {
        return getEconomies().stream()
                .filter(economy -> economy.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public @NotNull List<@NotNull Economy> getEconomies() {
        return Arrays.asList(EconomyImpl.of("gems"), EconomyImpl.of("tokens"));
    }

    @Blocking
    @Override
    public double getBalance(@NotNull final UUID uuid, @NotNull final String economy) {
        final String cached = cacheManager.getBalance(uuid.toString(), economy);

        if (cached != null) {
            return Double.parseDouble(cached);
        }

        final Double stored = storageManager.getStorage().getBalance(uuid, economy);

        if (stored != null) {
            cacheManager.cacheBalance(uuid.toString(), economy, Constant.DECIMAL_FORMAT.format(stored));
        }

        return stored == null ? 0.0d : stored;
    }

    @Blocking
    @Override
    public @Nullable Map<@NotNull String, @NotNull Double> getBalances(@NotNull final UUID uuid) {
        final Map<String, Double> map = new HashMap<>();

        for (Economy economy : getEconomies()) {
            map.put(economy.getName(), getBalance(uuid, economy.getName()));
        }

        return map;
    }

    @Override
    public void setBalance(@NotNull UUID uuid, @NotNull String economy, double balance) {
        cacheManager.cacheBalance(uuid.toString(), economy, Constant.DECIMAL_FORMAT.format(balance));
        storageManager.getStorage().updateBalance(uuid, economy, balance);
    }

}
