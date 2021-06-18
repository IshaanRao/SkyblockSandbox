package com.prince.skyblocksandbox.skyblockutils

import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType

class SkyblockHolograms {
    companion object {
        fun createHologram(loc: Location,name: String): ArmorStand {
            val hologram: ArmorStand = loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND) as ArmorStand
            hologram.setGravity(false)
            hologram.canPickupItems = false
            hologram.customName = name
            hologram.isCustomNameVisible = true
            hologram.isVisible = false
            hologram.isMarker = true
            return hologram
        }
        fun createHologramAndDelete(loc: Location,name: String,time:Long){
            val holo = createHologram(loc,name)
            Thread{
                Thread.sleep(time)
                holo.remove()
            }.start()
        }
    }
}