package com.prince.skyblocksandbox.skyblockenchants

import com.prince.skyblocksandbox.skyblockhandlers.StatisticHandler
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.getStats
import org.bukkit.entity.Player

object EnchantGiantKiller : SkyblockEnchant {
    override val levelRange: IntRange = 1..7
    override val name: String = "Giant Killer"
    override fun descAtLevel(level: Int): List<String> {
        return listOf("§7Increases damage dealt by §a0.${1*level}%","§7for each percent of extra","§7health that your target has","§7above you up to §a${5*level}%")
    }
    override val items: ItemTypes = ItemTypes.SWORD
    override fun getAddedDamage(mob: SkyblockMob, player: Player, level:Int): Double {
        return minOf((0.001*level)*(mob.currentHealth.toBigDecimal()*100.toBigDecimal()/player.getStats().health.toBigDecimal()-100.toBigDecimal()).toDouble(),0.05*level)
    }
}