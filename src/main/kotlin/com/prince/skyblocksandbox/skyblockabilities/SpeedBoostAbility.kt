package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import com.prince.skyblocksandbox.skyblockutils.Extensions.addSpeed
import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.getStats
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object SpeedBoostAbility : ItemAbility() {
    override val manaCost=20
    override val actions = listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)
    override val abilityType=AbilityTypes.SPEEDBOOST
    override val title = "§6Ability: Speed Boost §e§lRIGHT CLICK"
    override val name: String = "Speed Boost"
    override val itemType = ItemTypes.SWORD
    override val ability = SkyblockAbility(32000,0.3)
    override fun getDesc(stats: StatsData) : List<String> {
        return listOf("§7Increases your movement §f✦","§fSpeed §7by §a+20 §7for §a30","§seconds")
    }

    override fun execute(e: PlayerInteractEvent) {
        val p = e.player
        val stats = p.getStats()
        val cspeed = stats.speed
        val tspeed = if(cspeed+20 > 400) 400-cspeed else 20
        p.getStats().add(StatsData(speed = tspeed))
        p.addSpeed(tspeed)
        p.playSound(p.location, Sound.LAVA_POP, 1f, 1.4f)
        Bukkit.getScheduler().scheduleSyncDelayedTask(SkyblockSandbox.instance, {
            p.getStats().add(StatsData(speed = -tspeed))
            p.addSpeed(-tspeed)
            p.playSound(p.location, Sound.LAVA_POP, 1f, .9f)
        }, 600)
    }
}