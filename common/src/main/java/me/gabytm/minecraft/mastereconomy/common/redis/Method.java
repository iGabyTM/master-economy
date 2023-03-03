package me.gabytm.minecraft.mastereconomy.common.redis;

public enum Method {

    ADD{
        @Override
        public double apply(double balance, double amount) {
            return balance + amount;
        }
    },
    SUBTRACT{
        @Override
        public double apply(double balance, double amount) {
            return balance - amount;
        }
    },
    SET{
        @Override
        public double apply(double balance, double amount) {
            return amount;
        }
    };

    public abstract double apply(final double balance, final double amount);

}
