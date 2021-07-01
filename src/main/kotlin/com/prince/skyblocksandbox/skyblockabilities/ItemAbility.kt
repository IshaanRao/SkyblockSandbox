package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import java.util.*

abstract class ItemAbility {
    abstract val AbilityType:AbilityTypes
    abstract val action:String
    abstract val prefix:String
    abstract val title:String
    abstract fun getDesc(stats:StatsData):List<String>
    abstract val actions:List<Action>
    abstract val manaCost:Int
    open val ability:SkyblockAbility
        get() =  SkyblockAbility(0,0.0)
    val onCooldown: ArrayList<UUID> = ArrayList()
    abstract fun execute(e:PlayerInteractEvent)
    fun startCooldown(p: Player, cd:Long){
        onCooldown.add(p.uniqueId)
        Bukkit.getServer().scheduler.scheduleAsyncDelayedTask(SkyblockSandbox.instance, {
            onCooldown.remove(p.uniqueId)
        },cd)
    }
    fun playerOnCooldown(p: Player): Boolean{
        return onCooldown.contains(p.uniqueId)
    }
}