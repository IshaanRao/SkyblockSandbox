package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.SkyblockSandbox
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import java.util.*
import kotlin.collections.ArrayList

interface ItemAbility {
    val AbilityType:AbilityTypes
    val action:String
    val prefix:String
    val title:String
    val desc:List<String>
    val actions:List<Action>
    val manaCost:Int
    val ability:SkyblockAbility
        get() =  SkyblockAbility(0,0.0)
    val cd: Int
        get() = 0
    val onCooldown: ArrayList<UUID>
        get() = ArrayList()

    fun execute(e:PlayerInteractEvent)
    fun startCooldown(p: Player, cd:Long){
        onCooldown.add(p.uniqueId)
        Bukkit.getServer().scheduler.scheduleSyncDelayedTask(SkyblockSandbox.instance,{
            onCooldown.remove(p.uniqueId)
        },cd)
    }
    fun playerOnCooldown(p: Player): Boolean{
        return onCooldown.contains(p.uniqueId)
    }
}