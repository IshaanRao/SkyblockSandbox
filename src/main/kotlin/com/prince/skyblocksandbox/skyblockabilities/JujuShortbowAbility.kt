package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object JujuShortbowAbility : ItemAbility() {
    override val manaCost=0
    override val actions = listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR,Action.LEFT_CLICK_AIR,Action.LEFT_CLICK_BLOCK)
    override val AbilityType=AbilityTypes.JUJUSHORTBOW
    override val title = "§5Shortbow: Instantly shoots!"
    override val name: String = "N/A"
    override val itemType = ItemTypes.BOW
    override val specialAbility = true
    override fun getDesc(stats: StatsData) : List<String> { return listOf("§7Hits §c3 §7mobs on impact","§7Can damage endermen")}
    override fun execute(e: PlayerInteractEvent) {
        if(playerOnCooldown(e.player)){
            return
        }
        if(e.action===Action.RIGHT_CLICK_BLOCK||e.action===Action.RIGHT_CLICK_AIR){
            e.isCancelled = true
        }
        val p = e.player
        val arrow = p.launchProjectile(Arrow::class.java,p.location.direction)
        arrow.isCritical = true
        arrow.velocity = arrow.velocity.multiply(3)
        startCooldown(e.player,5)
    }
}