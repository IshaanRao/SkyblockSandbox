package com.prince.skyblocksandbox.skyblockutils

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player

enum class SkyblockWorlds {
    SkyblockHub;
    fun getWorld():World{
        return when (this) {
            SkyblockHub -> {
                Bukkit.getWorld("sb_hub")
            }
        }
    }
    fun getSpawnLocation():Location{
        return when (this) {
            SkyblockHub -> {
                Location(getWorld(),-2.5,70.0,-68.5,-180.0F,0.0F)
            }
        }
    }
    companion object {
        fun spawnPlayer(p: Player){
            for(world in values()){
                if(p.world.equals(world.getWorld())){
                    p.teleport(world.getSpawnLocation())
                    return
                }
            }
            p.teleport(SkyblockHub.getSpawnLocation())
        }
    }
}