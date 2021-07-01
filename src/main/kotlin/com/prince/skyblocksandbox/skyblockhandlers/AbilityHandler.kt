package com.prince.skyblocksandbox.skyblockhandlers

import com.prince.skyblocksandbox.skyblockabilities.AbilityTypes
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getSkyblockData
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.isSkyblockItem
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class AbilityHandler : Listener{
    @EventHandler
    fun onInteract(e: PlayerInteractEvent){
        if(e.player.itemInHand.type==Material.AIR){
            return
        }
        if(!e.player.itemInHand.isSkyblockItem()){
            return
        }
        val item = e.player.itemInHand.getSkyblockData()
        if(item.itemData.ability==AbilityTypes.NONE){
            return
        }
        val ability = item.itemData.ability.getAbility()
        for (action in ability.actions){
            if(action==e.action){
                if(StatisticHandler.getPlayerStats(e.player).mana<ability.manaCost.toBigInteger()){
                    return e.player.sendMessage("§cYou do not have enough mana to use this ability")
                }
                StatisticHandler.removeMana(e.player,ability.manaCost.toBigInteger())
                e.player.sendMessage("§aUsed §6${ability.title}§a! §b(${ability.manaCost} Mana)")
                return ability.execute(e)
            }
        }
    }
}