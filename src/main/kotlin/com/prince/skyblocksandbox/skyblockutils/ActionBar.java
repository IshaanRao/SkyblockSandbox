package com.prince.skyblocksandbox.skyblockutils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

//https://bukkit.org/threads/1-8-actionbar-packets.385244/
public class ActionBar {

    private final PacketPlayOutChat packet;

    public ActionBar(String text) {
        this.packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2);
    }

    public ActionBar(String text, ActionBarColor color) {
        this.packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\", \"color\": \""+color+"\"}"), (byte) 2);
    }

    public void sendToPlayer(Player p) {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }
}
