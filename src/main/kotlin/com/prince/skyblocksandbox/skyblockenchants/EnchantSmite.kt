package com.prince.skyblocksandbox.skyblockenchants

import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

object EnchantSmite : SkyblockEnchant {
    override val levelRange: IntRange = 1..7
    override val name: String = "Smite"
    override fun descAtLevel(level: Int): List<String> {
        return listOf("§7Increases damage dealt to","§7Zombies, Withers,","§7Zombie Pigmen, and","§7Skeletons by §a${8*level}%")
    }
    override val items: ItemTypes = ItemTypes.SWORD
    override fun getAddedDamage(mob: SkyblockMob, player: Player, level:Int): Double {
        if (mob.entityType == EntityType.ZOMBIE || mob.entityType == EntityType.PIG_ZOMBIE || mob.entityType == EntityType.WITHER || mob.entityType == EntityType.WITHER_SKULL || mob.entityType == EntityType.SKELETON) {
            return (0.08 * level)
        }
        return 0.0
    }
}