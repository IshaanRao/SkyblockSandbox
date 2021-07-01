package com.prince.skyblocksandbox.skyblockenchants

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.ItemStack

class EnchantFS : Enchantment(105) {
    override fun getName(): String {
        return "First Strike"
    }

    override fun getMaxLevel(): Int {
        return 5
    }

    override fun getStartLevel(): Int {
        return 1
    }

    override fun getItemTarget(): EnchantmentTarget {
        return EnchantmentTarget.WEAPON
    }

    override fun conflictsWith(other: Enchantment): Boolean {
        return other === SBEnchants.tripleStrike
    }

    override fun canEnchantItem(item: ItemStack): Boolean {
        return item.type == Material.DIAMOND_SWORD
    }
}