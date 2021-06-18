package com.prince.skyblocksandbox.skyblockhandlers

import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import com.prince.skyblocksandbox.skyblockutils.SkyblockHolograms
import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
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
            println(damageString)
        }else{
            damageString = "§7${damage.damage}"
        }
        SkyblockHolograms.createHologramAndDelete(loc.add(0.0,1.0,0.0),damageString,500)

    }
    fun getColorFromIndex(index:Int):String{
        val indexUsed = index%6
        return when(index){
            0,1->"§f"
            2->"§e"
            3->"§6"
            4,5->"§c"
            else -> ""
        }
    }
    fun calculateDamage(mob:SkyblockMob,player: Player): DamageData{
        return DamageData(true,BigInteger.valueOf(1234))
    }
    data class DamageData(val isCrit:Boolean,val damage: BigInteger )
}