
package com.prince.skyblocksandbox.skyblockenchants

import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import org.bukkit.entity.Player

object EnchantGiantKiller : SkyblockEnchant {
    override val levelRange: IntRange = 1..7
    override val name: String = "Giant Killer"
    override val items: ItemTypes = ItemTypes.SWORD
    override fun getAddedDamage(mob: SkyblockMob, player: Player, level:Int): Double {
        return minOf((0.001*level)*(mob.startingHealth.toInt()/player.maxHealth*100),0.05*level)
    }
}