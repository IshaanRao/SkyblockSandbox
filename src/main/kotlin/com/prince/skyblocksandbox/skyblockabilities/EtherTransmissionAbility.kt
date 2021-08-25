package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object EtherTransmissionAbility : ItemAbility() {
    override val manaCost=180
    override val actions = listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)
    override val abilityType=AbilityTypes.ETHERTRANSMISSION
    override val title = "§6Ability: Ether Transmission §e§lSNEAK RIGHT CLICK"
    override val name: String = "Ether Transmission"
    override val itemType = ItemTypes.SWORD
    override fun getDesc(stats: StatsData) : List<String> { return listOf("§7Teleport to your targeted block","§7up to §a57§7 blocks away.")}
    override val mustSneak = true
    override fun execute(e: PlayerInteractEvent) {
        val loc: Location
        try {
            loc = e.player.getTargetBlock(null as Set<Material?>?, 57).location
        }catch (e:Exception){
            return
        }
        val tpLoc = Location(loc.world,loc.x,loc.y,loc.z,e.player.location.yaw,e.player.location.pitch)
        e.player.teleport(tpLoc)
    }
}