package com.prince.skyblocksandbox.skyblockhandlers

import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getSwordData
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.isSkyblockSword
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class AbilityHandler : Listener{
    @EventHandler
    fun onInteract(e: PlayerInteractEvent){
        if(e.player.itemInHand.type==Material.AIR){
            return
        }
        if(!e.player.itemInHand.isSkyblockSword()){
            return
        }
        val sword = e.player.itemInHand.getSwordData()
        if(sword.abilityType==null){
            return
        }
        val ability = sword.abilityType!!.getAbility()
        for (action in ability.actions){
            if(action==e.action){
                return ability.execute(e)
            }
        }
    }
}