package com.prince.skyblocksandbox.skyblockutils

import com.google.gson.Gson
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getSkyblockData
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.isSkyblockItem
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

object SkyblockStats {
    val baseData : StatsData
    get() = StatsData(
        critChance = 30,
        intelligence = 100.toBigInteger(),
        speed = 100,
        health = 100.toBigInteger()
    )
    fun Player.getStats():StatsData {
        val data = baseData
        data.add(itemInHand.getStats(ItemTypes.SWORD))
        data.add(inventory.helmet.getStats(ItemTypes.ARMOR))
        data.add(inventory.chestplate.getStats(ItemTypes.ARMOR))
        data.add(inventory.leggings.getStats(ItemTypes.ARMOR))
        data.add(inventory.boots.getStats(ItemTypes.ARMOR))
        return data
    }
    fun Player.getStatsForArrow():StatsData {
        val data = baseData
        data.add(itemInHand.getStats(ItemTypes.BOW))
        data.add(inventory.helmet.getStats(ItemTypes.ARMOR))
        data.add(inventory.chestplate.getStats(ItemTypes.ARMOR))
        data.add(inventory.leggings.getStats(ItemTypes.ARMOR))
        data.add(inventory.boots.getStats(ItemTypes.ARMOR))
        return data
    }
    fun ItemStack?.getStats(typeNeeded:ItemTypes):StatsData{
        if(this==null) return StatsData()
        if(isSkyblockItem()){
            if(getSkyblockData().itemType == typeNeeded){
                return getSkyblockData().trueStats.getStatsData()
            }
        }
        return StatsData()
    }
    fun Player.statsWithItemInHand(item:ItemStack):StatsData{
        var data = StatsData(
            critChance = 30,
            intelligence = 100.toBigInteger(),
            speed = 100,
            health = 100.toBigInteger()
        )
        data.add(item.getStats(ItemTypes.SWORD))
        data.add(item.getStats(ItemTypes.BOW))
        data.add(inventory.helmet.getStats(ItemTypes.ARMOR))
        data.add(inventory.chestplate.getStats(ItemTypes.ARMOR))
        data.add(inventory.leggings.getStats(ItemTypes.ARMOR))
        data.add(inventory.boots.getStats(ItemTypes.ARMOR))
        return data
    }
    fun json(player: Player) {
        val uuid: UUID = player.getUniqueId()
        data class Stats(
            val id: UUID,
            val souls: Int,
            val combat: Int,
            val foraging: Int
        )
        var jsonStats = Gson().toJson(Stats(uuid, 0, 0, 0))
    }

}