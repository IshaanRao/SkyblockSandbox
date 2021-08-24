package com.prince.skyblocksandbox.skyblockutils

import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack

object SkyblockHolograms {
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
    @Suppress("DEPRECATION")
    fun createFloatingBlock(loc: Location,item: ItemStack):ArmorStand{
        val hologram: ArmorStand = loc.world.spawnEntity(loc, EntityType.ARMOR_STAND) as ArmorStand
        hologram.setGravity(false)
        hologram.canPickupItems = false
        hologram.isVisible = false
        hologram.isMarker = true
        val fallingBlock = loc.world.spawnFallingBlock(loc,item.type,item.durability.toByte())
        fallingBlock.dropItem = false
        hologram.passenger = fallingBlock
        return hologram
    }
}