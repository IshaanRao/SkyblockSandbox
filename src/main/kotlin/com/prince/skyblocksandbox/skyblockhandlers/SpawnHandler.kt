package com.prince.skyblocksandbox.skyblockhandlers

import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

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
}