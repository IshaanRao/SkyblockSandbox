package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.skyblockhandlers.StatisticHandler
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.getStats
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object ItqxicAbility : ItemAbility() {
    override val manaCost=5
    override val actions = listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)
    override val AbilityType=AbilityTypes.AOTE
    override val action = "RIGHT CLICK"
    override val prefix = "Item Ability"
    override val title = "Itqxic is gay"
    override fun getDesc(stats: StatsData) : List<String> { return listOf("§aHave your braincells reduced to the","§aammount of nanometers in itqxics dick","§acausing you to explode from the negative space")}
    override fun execute(e: PlayerInteractEvent) {
        var healthToKill = e.player.getStats().health+1000.toBigInteger()
        StatisticHandler.damagePlayer(e.player, healthToKill)
    }
}