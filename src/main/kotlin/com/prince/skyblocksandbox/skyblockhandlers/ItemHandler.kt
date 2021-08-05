package com.prince.skyblocksandbox.skyblockhandlers

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getSkyblockData
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.isSkyblockItem
import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.statsWithItemInHand
import org.bukkit.Bukkit
import org.bukkit.entity.Arrow
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.PlayerInventory

class ItemHandler : Listener {
    @EventHandler
    fun inventoryInteract(e:PlayerItemHeldEvent){
        val inventory:PlayerInventory = e.player.inventory
        val item = e.player.inventory.getItem(e.newSlot) ?: return
        if(item.isSkyblockItem()){
            val sbData = item.getSkyblockData()
            e.player.inventory.setItem(e.newSlot,sbData.createItem(e.player,e.player.statsWithItemInHand(item)))
            e.player.updateInventory()
        }
    }
    @EventHandler
    fun removeArrows(e:ProjectileHitEvent){
        val arrow = e.entity
        if(arrow is Arrow){
            Bukkit.getScheduler().scheduleSyncDelayedTask(SkyblockSandbox.instance, {
                if (arrow.isOnGround || arrow.isInsideVehicle) {
                    arrow.remove()
                }
            },1)
        }
    }
}