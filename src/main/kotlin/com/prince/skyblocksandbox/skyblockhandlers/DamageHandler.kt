package com.prince.skyblocksandbox.skyblockhandlers

import com.prince.skyblocksandbox.skyblockabilities.AbilityTypes
import com.prince.skyblocksandbox.skyblockabilities.ItemAbility
import com.prince.skyblocksandbox.skyblockabilities.SkyblockAbility
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getSkyblockData
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.isSkyblockItem
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.isSkyblockSword
import com.prince.skyblocksandbox.skyblockutils.SkyblockHolograms
import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.getStats
import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.getStatsForArrow
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import java.math.BigDecimal
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
    fun swordDamage(mob:SkyblockMob,player:Player,multiplier:Double){
        if(!mob.entity!!.isDead) {
            val damage = calculateDamage(mob, player)
            damage.damage = (damage.damage.toBigDecimal()*multiplier.toBigDecimal()).toBigInteger()
            createDmgHolo(mob.entity!!.location,damage)
            mob.currentHealth-=damage.damage
            mob.loadName()
        }
    }
    fun bowDamage(mob:SkyblockMob,player:Player,canCrit:Boolean){
        if(!mob.entity!!.isDead) {
            val damage = calculateBowDamage(mob, player,canCrit)
            createDmgHolo(mob.entity!!.location,damage)
            mob.currentHealth-=damage.damage
            mob.loadName()
        }
    }
    fun bowDamage(mob:SkyblockMob,player:Player,canCrit:Boolean,multiplier: Double){
        if(!mob.entity!!.isDead) {
            val damage = calculateBowDamage(mob, player,canCrit)
            damage.damage = (damage.damage.toBigDecimal()*multiplier.toBigDecimal()).toBigInteger()
            createDmgHolo(mob.entity!!.location,damage)
            mob.currentHealth-=damage.damage
            mob.loadName()
        }
    }
    companion object {


        fun termAbilityDamage(mob:SkyblockMob,player:Player){
            if(!mob.entity!!.isDead) {
                val damage = calculateTermDamage(mob, player)
                createDmgHolo(mob.entity!!.location,damage)
                mob.currentHealth-=damage.damage
                mob.loadName()
            }
        }
        fun calculateTermDamage(mob: SkyblockMob, player: Player): DamageData{
            val stats = player.getStatsForArrow()
            var enchantMultiplier = 0.0
            val itemInHand = player.itemInHand
            if(itemInHand.isSkyblockItem()){
                val swordData = itemInHand.getSkyblockData()
                val enchants = swordData.itemData.enchants
                for(enchant in enchants.keys){
                    val enchantObj = enchant.obj
                    if(enchantObj.items==ItemTypes.BOW){
                        enchantMultiplier+=enchantObj.getAddedDamage(mob,player, enchants[enchant]!!)
                    }
                }
            }
            var damage: Double = (5.0+stats.damage.toDouble())*(1.0+(stats.strength.toDouble()/100.0))*(1.0+(stats.extra/100)+enchantMultiplier)
            val isCrit = true
            if(isCrit) {
                damage*=(1+(stats.critDamage.toDouble()/100))
            }
            damage*=2
            return DamageData(isCrit,damage.toBigDecimal().toBigInteger())
        }
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
            val damageString: String = if (damage.isCrit) {
                val string = "✧${damage.damage}✧"
                val sb = StringBuilder()
                string.forEachIndexed { index, c ->
                    sb.append(getColorFromIndex(index)).append(c)
                }
                sb.toString()
            } else {
                "§7${damage.damage}"
            }
            SkyblockHolograms.createHologramAndDelete(loc.add(0.0, 1.0, 0.0), damageString, 500)

        }

        fun getColorFromIndex(index:Int):String{
            return when(index%6){
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

    fun calculateBowDamage(mob: SkyblockMob, player: Player, canCrit: Boolean): DamageData{
        val stats = player.getStatsForArrow()
        var enchantMultiplier = 0.0
        val itemInHand = player.itemInHand
        if(itemInHand.isSkyblockItem()){
            val swordData = itemInHand.getSkyblockData()
            val enchants = swordData.itemData.enchants
            for(enchant in enchants.keys){
                val enchantObj = enchant.obj
                if(enchantObj.items==ItemTypes.BOW){
                    enchantMultiplier+=enchantObj.getAddedDamage(mob,player, enchants[enchant]!!)
                }
            }
        }
        var damage: Double = (5.0+stats.damage.toDouble())*(1.0+(stats.strength.toDouble()/100.0))*(1.0+(stats.extra/100)+enchantMultiplier)
        var isCrit = (1..100).random()<=stats.critChance
        if(!canCrit){
            isCrit = false
        }
        if(isCrit) {
            damage*=(1+(stats.critDamage.toDouble()/100))
        }
        return DamageData(isCrit,damage.toBigDecimal().toBigInteger())
    }

    fun calculateDamage(mob:SkyblockMob,player: Player): DamageData{
        val stats = player.getStats()
        var enchantMultiplier = 0.0
        val itemInHand = player.itemInHand
        if(itemInHand.isSkyblockSword()){
            val swordData = itemInHand.getSkyblockData()
            val enchants = swordData.itemData.enchants
            for(enchant in enchants.keys){
                val enchantObj = enchant.obj
                if(enchantObj.items==ItemTypes.SWORD){
                    enchantMultiplier+=enchantObj.getAddedDamage(mob,player, enchants[enchant]!!)
                }
            }
        }
        var damage: BigDecimal = (5.0.toBigDecimal()+stats.damage.toBigDecimal())*(1.0.toBigDecimal()+(stats.strength.toBigDecimal()/100.0.toBigDecimal()))*(1.0.toBigDecimal()+(stats.extra.toBigDecimal()/100.toBigDecimal())+enchantMultiplier.toBigDecimal())
        val isCrit = (1..100).random()<=stats.critChance
        if(isCrit) {
            damage*=(1.0.toBigDecimal()+(stats.critDamage.toBigDecimal()/100.0.toBigDecimal()))
        }
        if(player.itemInHand.isSkyblockItem()){
            val item = player.itemInHand.getSkyblockData().itemData
            val itemAbilities = item.abilities
            if(itemAbilities!=null) {
                if (itemAbilities.contains(AbilityTypes.AOTSLORE)) {
                    if(mob.entityType==EntityType.ZOMBIE){
                        damage*=2.5.toBigDecimal()
                    }
                    StatisticHandler.healPlayer(player,50.toBigInteger())
                }
            }
        }
        return DamageData(isCrit,damage.toBigInteger())
    }


    data class DamageData(val isCrit:Boolean, var damage:BigInteger)
}
