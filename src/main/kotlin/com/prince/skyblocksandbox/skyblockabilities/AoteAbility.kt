package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object AoteAbility : ItemAbility() {
    override val manaCost=50
    override val actions = listOf(Action.RIGHT_CLICK_BLOCK,Action.RIGHT_CLICK_AIR)
    override val abilityType=AbilityTypes.AOTE
    override val title = "§6Item Ability: Instant Transmission §e§lRIGHT CLICK"
    override val name: String = "Instant Transmission"
    override val itemType = ItemTypes.SWORD
    override fun getDesc(stats: StatsData) : List<String> { return listOf("§7Teleport §a8 Blocks §7ahead of","§7you and gain §a+50 §f✦ Speed","§7for §a3 seconds")}
    override fun execute(e: PlayerInteractEvent) {
        val loc:Location
        try {
            loc = e.player.getTargetBlock(null as Set<Material?>?, 8).location
        }catch (e:Exception){
            return
        }
        val tpLoc = Location(loc.world,loc.x,loc.y,loc.z,e.player.location.yaw,e.player.location.pitch)
        e.player.teleport(tpLoc)
    }
}