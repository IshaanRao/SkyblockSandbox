package com.prince.skyblocksandbox.skyblockhandlers

import org.bukkit.GameMode
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityChangeBlockEvent


class SpawnHandler : Listener {
    @EventHandler
    fun blockBreak(event:BlockBreakEvent){
        if(event.player.gameMode!=GameMode.CREATIVE) {
            event.isCancelled = true
        }
    }
    @EventHandler
    fun blockPlace(event:BlockPlaceEvent){
        if(event.player.gameMode!=GameMode.CREATIVE) {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    fun EntityChangeBlock(event: EntityChangeBlockEvent) {
        if (event.entityType == EntityType.FALLING_BLOCK) {
            event.isCancelled = true
        }
    }
}