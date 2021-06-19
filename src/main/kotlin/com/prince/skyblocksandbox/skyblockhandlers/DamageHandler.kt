package com.prince.skyblocksandbox.skyblockhandlers

import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import com.prince.skyblocksandbox.skyblockutils.SkyblockHolograms
import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.getStats
import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import java.lang.StringBuilder
import java.math.BigInteger

class DamageHandler {
    fun swordDamage(mob:SkyblockMob,player:Player){
        if(!mob.entity!!.isDead) {
            val damage = calculateDamage(mob, player)
            createDmgHolo(mob.entity!!.location,damage)
            mob.currentHealth-=damage.damage
            mob.loadName()
        }
    }
    fun createDmgHolo(loc: Location, damage:DamageData){
        val damageString : String;
        if(damage.isCrit){
            val string = "✧${damage.damage}✧"
            val sb = StringBuilder()
            string.forEachIndexed { index, c ->
                sb.append(getColorFromIndex(index)).append(c)
            }
            damageString = sb.toString()
        }else{
            damageString = "§7${damage.damage}"
        }
        SkyblockHolograms.createHologramAndDelete(loc.add(0.0,1.0,0.0),damageString,500)

    }
    fun getColorFromIndex(index:Int):String{
        val indexUsed = index%6
        return when(indexUsed){
            0,1->"§f"
            2->"§e"
            3->"§6"
            4,5->"§c"
            else -> ""
        }
    }
    fun calculateDamage(mob:SkyblockMob,player: Player): DamageData{
        val stats = player.getStats()
        var damage: BigInteger = (5+stats.damage).toBigInteger()*(1+(stats.str/100)).toBigInteger()*(1+(stats.extra/100)).toBigInteger()
        val isCrit = (1..100).random()<=stats.critChance
        if(isCrit) {
            damage*=(1+(stats.critDmg/100)).toBigInteger()
        }
        return DamageData(isCrit,damage)
    }
    data class DamageData(val isCrit:Boolean,val damage:BigInteger)
}