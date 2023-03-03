package me.gabytm.minecraft.mastereconomy.common.redis;

public final class Channel {

    /**
     * The channel where a server will send a message when the balance of a player is modified.
     * <br/>
     * The message has the following format: '{@link java.util.UUID player's uuid}:{@link Method METHOD}:{@code amount}:{@code economy name}'
     * <br/><br/>
     * E.g. player GabyTM (30daf97e-e493-42e1-9003-ee715f078048) receives 30 'gems', the message will look like this
     * {@code 30daf97e-e493-42e1-9003-ee715f078048:ADD:30:gems}
     */
    public static final String BALANCE_CHANGE = "balance_change";
    public static final String BALANCE_REQUEST = "balance_request";
    public static final String BALANCE_UPDATE = "balance_update";

}
