package com.prince.skyblocksandbox.skyblockenchants

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.ItemStack

class EnchantExec : Enchantment(104) {
    override fun getName(): String {
        return "Execute"
    }

    override fun getMaxLevel(): Int {
        return 6
    }

    override fun getStartLevel(): Int {
        return 1
    }

    override fun getItemTarget(): EnchantmentTarget {
        return EnchantmentTarget.WEAPON
    }

    override fun conflictsWith(other: Enchantment): Boolean {
        return other === SBEnchants.prosecute
    }

    override fun canEnchantItem(item: ItemStack): Boolean {
        return item.type == Material.DIAMOND_SWORD
    }
}