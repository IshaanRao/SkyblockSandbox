package com.prince.skyblocksandbox.skyblockutils

import com.google.gson.Gson
import com.prince.skyblocksandbox.SkyblockSandbox.Companion.log
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getSkyblockData
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.isSkyblockItem
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.UUID

object SkyblockStats {

    fun Player.getStats():StatsData {
        var data = StatsData(
            critChance = 30,
            intelligence = 100.toBigInteger(),
            speed = 100,
            health = 100.toBigInteger()
        )
        if(itemInHand.type!=Material.AIR) {
            if (itemInHand.isSkyblockItem()) {
                if(itemInHand.getSkyblockData().itemType == ItemTypes.SWORD){
                    data.add(itemInHand.getSkyblockData().trueStats.getStatsData())
                }
            }
        }
        if(inventory.helmet!=null){
            if(inventory.helmet.isSkyblockItem()){
                if(inventory.helmet.getSkyblockData().itemType == ItemTypes.ARMOR){
                    data.add(inventory.helmet.getSkyblockData().trueStats.getStatsData())
                }
            }
        }
        if(inventory.chestplate!=null){
            if(inventory.chestplate.isSkyblockItem()){
                if(inventory.chestplate.getSkyblockData().itemType == ItemTypes.ARMOR){
                    data.add(inventory.chestplate.getSkyblockData().trueStats.getStatsData())
                }
            }
        }
        if(inventory.leggings!=null){
            if(inventory.leggings.isSkyblockItem()){
                if(inventory.leggings.getSkyblockData().itemType == ItemTypes.ARMOR){
                    data.add(inventory.leggings.getSkyblockData().trueStats.getStatsData())
                }
            }
        }
        if(inventory.boots!=null){
            if(inventory.boots.isSkyblockItem()){
                if(inventory.boots.getSkyblockData().itemType == ItemTypes.ARMOR){
                    data.add(inventory.boots.getSkyblockData().trueStats.getStatsData())
                }
            }
        }
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