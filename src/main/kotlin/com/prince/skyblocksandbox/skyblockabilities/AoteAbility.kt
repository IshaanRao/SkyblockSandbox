package com.prince.skyblocksandbox.skyblockabilities

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class AoteAbility : ItemAbility {
    override val manaCost=50
    override val actions = listOf(Action.RIGHT_CLICK_BLOCK,Action.RIGHT_CLICK_AIR)
    override val AbilityType=AbilityTypes.AOTE
    override val action = "RIGHT CLICK"
    override val prefix = "Item Ability"
    override val title = "Instant Transmission"
    override val desc= listOf("§7Teleport §a8 Blocks §7ahead of","§7you and gain §a+50 §f✦ Speed","§7for §a3 seconds")
    override fun execute(e: PlayerInteractEvent) {
        val loc = e.player.getTargetBlock(null as Set<Material?>?,8).location
        val tpLoc = Location(loc.world,loc.x,loc.y,loc.z,e.player.location.yaw,e.player.location.pitch)
        e.player.teleport(tpLoc)
    }
}