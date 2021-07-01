package com.prince.skyblocksandbox.skyblockhandlers

import com.prince.skyblocksandbox.skyblockabilities.ItemAbility
import com.prince.skyblocksandbox.skyblockabilities.SkyblockAbility
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import com.prince.skyblocksandbox.skyblockutils.SkyblockHolograms
import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.getStats
import org.bukkit.Location
import org.bukkit.entity.Player
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
    companion object {


        operator fun Int.plus(bint:BigInteger):BigInteger {
            return this.toBigInteger()+bint
        }

        fun magicDamage(mob:SkyblockMob,player: Player,ability:ItemAbility): DamageData{
            if(!mob.entity!!.isDead) {
                val damage = calculateMagicDamage(player = player,mob = mob,skyblockAbility = ability.ability)
                createDmgHolo(mob.entity!!.location,damage)
                mob.currentHealth-=damage.damage
                mob.loadName()
                return damage
            }
            return DamageData(false, BigInteger.valueOf(0))
        }
        fun createDmgHolo(loc: Location, damage: DamageData) {
            val damageString: String;
            if (damage.isCrit) {
                val string = "✧${damage.damage}✧"
                val sb = StringBuilder()
                string.forEachIndexed { index, c ->
                    sb.append(getColorFromIndex(index)).append(c)
                }
                damageString = sb.toString()
            } else {
                damageString = "§7${damage.damage}"
            }
            SkyblockHolograms.createHologramAndDelete(loc.add(0.0, 1.0, 0.0), damageString, 500)

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
        fun calculateMagicDamage(mob:SkyblockMob,player: Player,skyblockAbility: SkyblockAbility): DamageData{
            val stats = player.getStats()
            val damage: Double = ((stats.abilityDamage+skyblockAbility.abilityDamage) * ((1+(stats.intelligence.toDouble()/100)) * skyblockAbility.multiplier))
            return DamageData(false,BigInteger.valueOf(damage.toLong()))
        }
    }

    fun calculateDamage(mob:SkyblockMob,player: Player): DamageData{
        val stats = player.getStats()
        var damage: Double = (5.0+stats.damage.toDouble())*(1.0+(stats.strength.toDouble()/100.0))*(1.0+(stats.extra/100))
        println(damage)
        val isCrit = (1..100).random()<=stats.critChance
        if(isCrit) {
            damage*=(1+(stats.critDamage.toDouble()/100))
        }
        return DamageData(isCrit,damage.toBigDecimal().toBigInteger())
    }
    data class DamageData(val isCrit:Boolean,val damage:BigInteger)
}
