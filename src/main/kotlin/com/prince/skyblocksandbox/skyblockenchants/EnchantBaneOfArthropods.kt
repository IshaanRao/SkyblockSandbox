package com.prince.skyblocksandbox.skyblockenchants

import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

object EnchantBaneOfArthropods : SkyblockEnchant {
    override val levelRange: IntRange = 1..7
    override val name: String = "Bane of Arthropods"
    override fun descAtLevel(level: Int): List<String> {
        return listOf("§7Increases damage dealt to","§7Silverfish, Spiders, and","§7Cave Spiders by §a${8*level}%")
    }
    override val items: ItemTypes = ItemTypes.SWORD
    override fun getAddedDamage(mob: SkyblockMob, player: Player, level:Int): Double {
        if (mob.entityType == EntityType.CAVE_SPIDER || mob.entityType == EntityType.SPIDER || mob.entityType == EntityType.SILVERFISH) {
            return (0.08 * level)
        }
        return 0.0
    }
}