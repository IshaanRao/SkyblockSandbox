package com.prince.skyblocksandbox.skyblockhandlers

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class SpawnHandler : Listener {
    @EventHandler
    fun blockBreak(event:BlockBreakEvent){
        event.isCancelled = true
    }
    @EventHandler
    fun blockPlace(event:BlockPlaceEvent){
        event.isCancelled = true
    }
}