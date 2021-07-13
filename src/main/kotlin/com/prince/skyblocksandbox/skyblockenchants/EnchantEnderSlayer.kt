package com.prince.skyblocksandbox.skyblockenchants

import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

object EnchantEnderSlayer : SkyblockEnchant {
    override val levelRange: IntRange = 1..7
    override val name: String = "Ender Slayer"
    override fun descAtLevel(level: Int): List<String> {
        return listOf("§7Increases damage dealt to","§7Ender Dragons and","§7Endermen by §a${12*level}%")
    }
    override val items: ItemTypes = ItemTypes.SWORD
    override fun getAddedDamage(mob: SkyblockMob, player: Player, level:Int): Double {
        if (mob.entityType == EntityType.ENDER_DRAGON || mob.entityType == EntityType.ENDERMAN || mob.entityType == EntityType.ENDERMITE) {
            return (0.12 * level)
        }
        return 0.0
    }
}