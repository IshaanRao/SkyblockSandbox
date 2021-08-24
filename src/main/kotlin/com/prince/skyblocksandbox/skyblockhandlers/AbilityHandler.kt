package com.prince.skyblocksandbox.skyblockhandlers

import com.prince.skyblocksandbox.skyblockutils.Extensions.creative
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getSkyblockData
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.isSkyblockItem
import org.bukkit.GameMode
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
        if(item.itemData.abilities == null){
            return
        }else if(item.itemData.abilities!!.isEmpty()){
            return
        }
        val abilities = item.itemData.abilities!!
        for(abilityEnum in abilities) {
            val ability = abilityEnum.getAbility()
            if(ability.playerOnCooldown(e.player)){
                if(!ability.noUseMessage && !e.player.creative()) {
                    e.player.sendMessage("§cThis ability is currently on cooldown")
                }
                return
            }
            for (action in ability.actions) {
                if (action == e.action) {
                    if(!ability.specialAbility) {
                        if (!e.player.creative() && StatisticHandler.getPlayerStats(e.player).mana < ability.manaCost.toBigInteger()) {
                            e.player.sendMessage("§cYou do not have enough mana to use this ability")
                            continue
                        }
                        if(!e.player.creative()) StatisticHandler.removeMana(e.player, ability.manaCost.toBigInteger())
                        if(!ability.noUseMessage) {
                            e.player.sendMessage("§aUsed §6${ability.name}§a! §b(${ability.manaCost} Mana)")
                        }
                    }
                    ability.execute(e)
                }
            }
        }
    }
}