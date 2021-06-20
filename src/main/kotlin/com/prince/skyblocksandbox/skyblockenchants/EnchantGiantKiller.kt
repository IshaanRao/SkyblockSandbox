package com.prince.skyblocksandbox.skyblockenchants

import com.prince.skyblocksandbox.skyblockenchants.CustomEnchantment
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.ItemStack

class EnchantGiantKiller : CustomEnchantment(101) {
    override fun canEnchantItem(item: ItemStack): Boolean {
        return item.type == Material.DIAMOND_SWORD
    }

    override fun getItemTarget(): EnchantmentTarget {
        return EnchantmentTarget.WEAPON
    }

    override fun getMaxLevel(): Int {
        return 7
    }

    override fun getName(): String {
        return "Giant Killer"
    }

    override fun conflictsWith(other: Enchantment): Boolean {
        return false
    }
}