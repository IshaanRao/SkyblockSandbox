package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.skyblockhandlers.DamageHandler
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler.Companion.isSkyblockMob
import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.getStats
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import java.math.BigInteger

class WitherImpact : ItemAbility {
    override val manaCost=300
    override val actions = listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)
    override val AbilityType=AbilityTypes.WITHER_IMPACT
    override val action = "RIGHT CLICK"
    override val prefix = "Item Ability"
    override val title = "Wither Impact"
    override val desc= listOf("§7Teleport §a10 Blocks§7 ahead of","§7you. Then implode dealing","§c%%dmg%%§7 damage to nearby","§7enemies. Also applies the wither","§7shield scroll abilities reducing","§7damage taken and granting an","§7absorption shield for §e5§7 seconds.")
    override val ability = SkyblockAbility(10000,0.3)
    override fun execute(e: PlayerInteractEvent) {
        if(!playerOnCooldown(e.player)) {
            startCooldown(e.player,150)
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
        }

    }
}