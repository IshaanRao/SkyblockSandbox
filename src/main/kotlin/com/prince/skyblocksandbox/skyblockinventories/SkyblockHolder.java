package com.prince.skyblocksandbox.skyblockinventories;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class SkyblockHolder implements InventoryHolder {
    private String name;
    public SkyblockHolder(String name){
        this.name = name;
    }
    @Override
    public Inventory getInventory() {
        return null;
    }
    public String getName() {
        return name;
    }
}
