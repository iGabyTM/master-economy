package me.gabytm.minecraft.mastereconomy.economy;

public abstract class Economy {

    private final String name;

    public Economy(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
