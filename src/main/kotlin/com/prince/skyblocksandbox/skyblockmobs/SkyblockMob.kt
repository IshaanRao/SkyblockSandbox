package com.prince.skyblocksandbox.skyblockmobs

import com.prince.skyblocksandbox.skyblockexceptions.skyblockmobs.SkyblockMobSpawnException
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import java.math.BigInteger

abstract class SkyblockMob(mobHandler :MobHandler){
    var currentHealth: BigInteger =  BigInteger.valueOf(1)
    abstract var startingHealth: BigInteger
    abstract val level: Int
    abstract val name: String
    abstract val entityType: EntityType
    var hasSpawned = false
    var entity: LivingEntity? = null
    init {
        mobHandler.registerMob(this)
    }
    fun spawn(loc: Location){
        if(!hasSpawned) {
            hasSpawned = true
            currentHealth = startingHealth
            entity = loc.world.spawnEntity(loc, entityType) as LivingEntity?
            defaultLoad()
            load()
            return
        }
        throw SkyblockMobSpawnException("Tried to spawn mob that has already been spawned")

    }
    private fun defaultLoad(){
        if(hasSpawned){
            loadName()
            entity!!.isCustomNameVisible = true
            return
        }
        throw SkyblockMobSpawnException("Tried to load mob that hasnt been spawned")
    }
    fun loadName(){
        if(hasSpawned){
            entity!!.customName = "§8[§7Lv$level§8] §c$name ${if(currentHealth<=(startingHealth/BigInteger.valueOf(2))) "§e" else "§a"}${if(currentHealth<=BigInteger.valueOf(0)) 0 else currentHealth}§f/§a$startingHealth"
        }
    }
    open fun load() = Unit

}