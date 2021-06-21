package com.prince.skyblocksandbox.skyblockenchants

import org.bukkit.enchantments.EnchantmentTarget
import com.prince.skyblocksandbox.skyblockenchants.SBEnchants
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class EnchantTK : Enchantment(102) {
    override fun getName(): String {
        return "Titan Killer"
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
        return other === SBEnchants.giantKiller
    }

    override fun canEnchantItem(item: ItemStack): Boolean {
        return item.type == Material.DIAMOND_SWORD
    }
}