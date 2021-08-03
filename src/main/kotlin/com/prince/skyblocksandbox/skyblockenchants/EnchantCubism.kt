package com.prince.skyblocksandbox.skyblockenchants

import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

object EnchantCubism : SkyblockEnchant {
    override val levelRange: IntRange = 1..6
    override val name: String = "Cubism"
    override fun descAtLevel(level: Int): List<String> {
        return listOf("§7Increases damage dealt to,","§7Slimes, Magma Cubes, and","§7Creepers by §a${10*level}%")
    }
    override val items: ItemTypes = ItemTypes.SWORD
    override fun getAddedDamage(mob: SkyblockMob, player: Player, level:Int): Double {
        if (mob.entityType == EntityType.CREEPER || mob.entityType == EntityType.MAGMA_CUBE || mob.entityType == EntityType.SLIME) {
            return (0.1 * level)
        }
        return 0.0
    }
}