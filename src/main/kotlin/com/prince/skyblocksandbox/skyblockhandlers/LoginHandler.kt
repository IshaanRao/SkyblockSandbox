package com.prince.skyblocksandbox.skyblockhandlers

import com.prince.skyblocksandbox.skyblockutils.SkyblockWorlds
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class LoginHandler: Listener {
    @EventHandler
    fun onPlayerJoin(e:PlayerJoinEvent){
        SkyblockWorlds.spawnPlayer(e.player)
        e.player.health = 20.0
        e.player.saturation = 20.0F
    }
}