package com.prince.skyblocksandbox.skyblockutils

import com.google.gson.Gson
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getSwordData
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.isSkyblockSword
import org.bukkit.Material
import org.bukkit.entity.Player
import java.math.BigInteger
import java.util.UUID

object SkyblockStats {

    fun Player.getStats():Stats {
        var str:Int = 0
        var dmg:Int = 0
        var cd:Int = 0
        var cc:Int = 30
        var intel:Int= 100
        val abilityDamage:Double = 0.0
        if(itemInHand.type!=Material.AIR) {
            if (itemInHand.isSkyblockSword()) {
                dmg += itemInHand.getSwordData().statswithreforge.damage
                str += itemInHand.getSwordData().statswithreforge.strength
                cd += itemInHand.getSwordData().statswithreforge.critDamage
                cc += itemInHand.getSwordData().statswithreforge.critChance
                intel += itemInHand.getSwordData().statswithreforge.intel
            }
        }
        return Stats(
            str = str,
            damage = dmg,
            critDmg = cd,
            critChance = cc,
            intel = intel,
            abilityDamage = abilityDamage,
            extra = 0
        )
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
    data class Stats(var str:Int=0,var damage:Int=0,var critDmg:Int=0,var critChance:Int=0,var intel:Int=100,var abilityDamage:Double=0.0,var extra:Int=0){

    }

}