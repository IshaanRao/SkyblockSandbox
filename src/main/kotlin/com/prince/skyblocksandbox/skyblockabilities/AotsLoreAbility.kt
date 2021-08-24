package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object AotsLoreAbility : ItemAbility() {
    override val manaCost=0
    override val actions = listOf<Action>()
    override val AbilityType=AbilityTypes.AOTSLORE
    override val title = "§7Heal §c50❤ §7per hit."
    override val name: String = "AotsLore"
    override val itemType = ItemTypes.SWORD
    override val specialAbility = true
    override val noUseMessage = true
    override fun getDesc(stats: StatsData) : List<String> { return listOf("§7Deal §a+250% §7damage to Zombies.","§7Receive §a25% §7less damage","§7from Zombies when held")}
    override fun execute(e: PlayerInteractEvent) {
    }
}