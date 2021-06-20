package com.prince.skyblocksandbox.skyblockenchants

import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.ItemStack

abstract class CustomEnchantment(id: Int) : Enchantment(id) {
    abstract override fun canEnchantItem(item: ItemStack): Boolean
    abstract override fun getItemTarget(): EnchantmentTarget
    abstract override fun getMaxLevel(): Int
    abstract override fun getName(): String
    override fun conflictsWith(other: Enchantment): Boolean {
        return false
    }

    override fun getStartLevel(): Int {
        return 1
    }
}