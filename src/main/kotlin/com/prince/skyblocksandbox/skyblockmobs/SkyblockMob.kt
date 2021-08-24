package com.prince.skyblocksandbox.skyblockmobs

import com.prince.skyblocksandbox.skyblockexceptions.skyblockmobs.SkyblockMobSpawnException
import net.minecraft.server.v1_8_R3.GenericAttributes
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import java.math.BigInteger


abstract class SkyblockMob(){
    var currentHealth: BigInteger =  BigInteger.valueOf(1)
    abstract var startingHealth: BigInteger
    abstract val level: Int
    abstract val name: String
    abstract val entityType: EntityType
    abstract val damage: Int
    var hasSpawned = false
    var entity: LivingEntity? = null

    fun defaultLoad(){
        if(hasSpawned){
            entity!!.canPickupItems = false
            (entity!! as CraftLivingEntity).handle.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).value = 10.0
            loadName()
            entity!!.isCustomNameVisible = true
            return
        }
        throw SkyblockMobSpawnException("Tried to load mob that hasnt been spawned")
    }
    open fun loadName(){
        if(hasSpawned){
            entity!!.customName = "§8[§7Lv$level§8] §c$name ${if(currentHealth<=(startingHealth/BigInteger.valueOf(2))) "§e" else "§a"}${if(currentHealth<=BigInteger.valueOf(0)) 0 else currentHealth}§f/§a$startingHealth§c❤"
        }
    }
    open fun load() = Unit

}