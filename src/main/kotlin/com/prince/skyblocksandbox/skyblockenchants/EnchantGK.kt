package com.prince.skyblocksandbox.skyblockenchants

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.ItemStack

class EnchantGK : Enchantment(106) {
    override fun getName(): String {
        return "Giant Killer"
    }

    override fun getMaxLevel(): Int {
        return 7
    }

    override fun getStartLevel(): Int {
        return 1
    }

    override fun getItemTarget(): EnchantmentTarget {
        return EnchantmentTarget.WEAPON
    }

    override fun conflictsWith(other: Enchantment): Boolean {
        return other === SBEnchants.titanKiller
    }

    override fun canEnchantItem(item: ItemStack): Boolean {
        return item.type == Material.DIAMOND_SWORD
    }
}