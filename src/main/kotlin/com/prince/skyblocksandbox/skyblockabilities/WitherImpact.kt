package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockhandlers.DamageHandler
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler.Companion.isSkyblockMob
import com.prince.skyblocksandbox.skyblockhandlers.StatisticHandler
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import com.prince.skyblocksandbox.skyblockutils.Extensions.fancyTeleport
import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.getStats
import org.bukkit.*
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import java.math.BigInteger
import java.util.*

object WitherImpact : ItemAbility() {
    override val manaCost=300
    override val actions = listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)
    override val abilityType=AbilityTypes.WITHER_IMPACT
    override val title = "§6Item Ability: Wither Impact §e§lRIGHT CLICK"
    override val name = "Wither Impact"
    override val itemType = ItemTypes.SWORD
    override fun getDesc(stats:StatsData) : List<String> {
        val damage = ((stats.abilityDamage+ ability.abilityDamage) * ((1+(stats.intelligence.toDouble()/100)) * ability.multiplier)).toBigDecimal().toBigInteger()
        return listOf("§7Teleport §a10 Blocks§7 ahead of","§7you. Then implode dealing","§c${damage}§7 damage to nearby","§7enemies. Also applies the wither","§7shield scroll abilities reducing","§7damage taken and granting an","§7absorption shield for §e5§7 seconds.")
    }
    override val ability = SkyblockAbility(10000,0.3)
    override fun execute(e: PlayerInteractEvent) {
        if(!playerOnCooldown(e.player)) {
            startCooldown(e.player,2)
            e.player.fancyTeleport(10)
            val nearbyEntities =
                e.player.getNearbyEntities(6.0, 6.0, 6.0).filter { entity -> entity.isSkyblockMob() != null }
            var damage = BigInteger.valueOf(0)
            for (entity in nearbyEntities) {
                val mob = entity.isSkyblockMob()!!
                damage += DamageHandler.magicDamage(mob, e.player, this).damage
            }

            e.player.playEffect(e.player.location, Effect.EXPLOSION_HUGE, null)
            e.player.playSound(e.player.location, Sound.WITHER_SHOOT, 1f, 1f)

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