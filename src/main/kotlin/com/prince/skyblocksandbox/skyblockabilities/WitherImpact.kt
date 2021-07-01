package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockhandlers.DamageHandler
import com.prince.skyblocksandbox.skyblockhandlers.DamageHandler.Companion.plus
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler.Companion.isSkyblockMob
import com.prince.skyblocksandbox.skyblockhandlers.StatisticHandler
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.getStats
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import sun.audio.AudioPlayer.player
import java.math.BigInteger
import java.util.*
import kotlin.collections.ArrayList

object WitherImpact : ItemAbility() {
    override val manaCost=300
    override val actions = listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)
    override val AbilityType=AbilityTypes.WITHER_IMPACT
    override val action = "RIGHT CLICK"
    override val prefix = "Item Ability"
    override val title = "Wither Impact"
    override fun getDesc(stats:StatsData) : List<String> {
        val damage = ((stats.abilityDamage+ ability.abilityDamage) * ((1+(stats.intelligence.toDouble()/100)) * ability.multiplier)).toBigDecimal().toBigInteger()
        return listOf("§7Teleport §a10 Blocks§7 ahead of","§7you. Then implode dealing","§c${damage}§7 damage to nearby","§7enemies. Also applies the wither","§7shield scroll abilities reducing","§7damage taken and granting an","§7absorption shield for §e5§7 seconds.")
    }
    override val ability = SkyblockAbility(10000,0.3)
    override fun execute(e: PlayerInteractEvent) {
        if(!playerOnCooldown(e.player)) {
            startCooldown(e.player,2)
            val loc = e.player.getTargetBlock(null as Set<Material?>?, 10).location
            val tpLoc = Location(loc.world, loc.x, loc.y, loc.z, e.player.location.yaw, e.player.location.pitch)
            e.player.teleport(tpLoc)
            val nearbyEntities =
                e.player.getNearbyEntities(6.0, 6.0, 6.0).filter { entity -> entity.isSkyblockMob() != null }
            var damage = BigInteger.valueOf(0)
            for (entity in nearbyEntities) {
                val mob = entity.isSkyblockMob()!!
                damage += DamageHandler.magicDamage(mob, e.player, this).damage
            }
            if (nearbyEntities.isNotEmpty()) {
                e.player.sendMessage(
                    "§7Your Implosion hit §c${nearbyEntities.size} §7${if (nearbyEntities.size == 1) "enemy" else "enemies"} for §c${
                        "%,d".format(
                            damage
                        )
                    }§7 damage"
                )
            }
            if(!onWitherCooldown.contains(e.player.uniqueId)){
                onWitherCooldown.add(e.player.uniqueId)
                val amtHealed = e.player.getStats().critDamage.toDouble()*1.5
                StatisticHandler.addAbsorption(e.player,(amtHealed).toBigDecimal().toBigInteger())
                Bukkit.getServer().scheduler.scheduleAsyncDelayedTask(SkyblockSandbox.instance, {
                    StatisticHandler.healPlayer(e.player,StatisticHandler.getPlayerStats(e.player).absorption/2.toBigInteger())
                    StatisticHandler.removeAbsorption(e.player,amtHealed.toBigDecimal().toBigInteger())
                    onWitherCooldown.remove(e.player.uniqueId)
                },100)
            }
        }else{
            e.player.sendMessage("")
        }

    }


    val onWitherCooldown: ArrayList<UUID> = ArrayList()
}