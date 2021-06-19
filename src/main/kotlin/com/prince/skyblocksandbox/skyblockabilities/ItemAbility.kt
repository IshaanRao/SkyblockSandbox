package com.prince.skyblocksandbox.skyblockabilities

import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

interface ItemAbility {
    val AbilityType:AbilityTypes
    val action:String
    val prefix:String
    val title:String
    val desc:List<String>
    val actions:List<Action>
    val manaCost:Int
    val cd: Int
        get() = 0
    fun execute(e:PlayerInteractEvent)
}