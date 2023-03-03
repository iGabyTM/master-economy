package me.gabytm.minecraft.mastereconomy.api.util;

import java.text.DecimalFormat;

public final class Constant {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.####");

    static {
        DECIMAL_FORMAT.setMaximumFractionDigits(4);
    }

    private Constant() {

    }

}
