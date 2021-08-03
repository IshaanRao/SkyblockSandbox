package com.prince.skyblocksandbox.skyblockmobs

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler
import com.prince.skyblocksandbox.skyblockutils.SkyblockWorlds
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.entity.EntityType
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.properties.Delegates


object MobSpawning {
    val spawningConfig = SkyblockSandbox.instance.spawningNodeConfig
    var scheduler = 0
    val mobs = HashMap<SpawningNode,SkyblockMob>()
    init {
        if(spawningConfig.config.get("SpawningNodes")==null){
            spawningConfig.config.set("SpawningNodes.${UUID.randomUUID()}",SpawningNode(SkyblockMobs.ZOMBIE,SkyblockWorlds.SkyblockHub.getSpawnLocation()))
            spawningConfig.save()
        }
        start()
    }
    fun stop(){
        Bukkit.getScheduler().cancelTask(scheduler)
    }
    fun restart(){
        stop()
        start()
    }
    fun start() {
        for(mob in mobs.values){
            mob.entity?.remove()
        }
        mobs.clear()
        for(key in spawningConfig.config.getConfigurationSection("SpawningNodes").getKeys(false)){
            val node = spawningConfig.config.get("SpawningNodes.$key") as SpawningNode
            val mob = node.mob.getMob()
            SkyblockSandbox.instance.mobHandler.spawnMob(mob,node.location)
            mobs[node] = mob
        }
        scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyblockSandbox.instance,{
            val mobEntries = mobs.entries
            for(set in mobEntries){
                if(set.value.entity?.isDead == true){
                    val mob = set.key.mob.getMob()
                    SkyblockSandbox.instance.mobHandler.spawnMob(mob,set.key.location)
                    mobs[set.key] = mob
                }
            }
        },0L,600L)
    }
    @SerializableAs("SpawningNode")
    class SpawningNode(val mob:SkyblockMobs,val location: Location) : ConfigurationSerializable{
        override fun serialize(): MutableMap<String, Any> {
            val map: MutableMap<String, Any> = HashMap()
            map["mob"] = mob.name
            map["location"] = location
            return map
        }
        constructor(map: MutableMap<String, Any>):this(SkyblockMobs.valueOf(map["mob"] as String), map["location"] as Location)
        companion object {
            fun deserialize(map:MutableMap<String, Any>):SpawningNode{
                return SpawningNode(SkyblockMobs.valueOf(map["mob"] as String) , map["location"] as Location)
            }
        }


    }
}