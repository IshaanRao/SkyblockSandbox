package com.prince.skyblocksandbox.skyblockitems.data

import com.prince.skyblocksandbox.skyblockabilities.AbilityTypes
import com.prince.skyblocksandbox.skyblockutils.SkyblockRarities
import java.math.BigInteger


data class ItemData(
    var name:String,
    var rarity:SkyblockRarities,
    var item:ItemStackData,
    var reforgable:Boolean = false,
    var defense: BigInteger = 0.toBigInteger(),
    var health:BigInteger = 0.toBigInteger(),
    var damage:BigInteger = 0.toBigInteger(),
    var strength:BigInteger =0.toBigInteger(),
    var critDamage:BigInteger = 0.toBigInteger(),
    var critChance:Int = 0,
    var intelligence:BigInteger =0.toBigInteger(),
    var speed:Int = 0,
    var abilityDamage:Double = 0.0,
    var extra:Int = 0,
    var ability:AbilityTypes = AbilityTypes.NONE,
    var reforge:ReforgeStats = ReforgeStats(),
    var hpbs:Int = 0
){
   fun getStatsData():StatsData {
       return StatsData(
           defense = defense,
           health = health,
           damage = damage,
           strength = strength,
           critDamage = critDamage,
           critChance = critChance,
           intelligence = intelligence,
           speed = speed,
           abilityDamage = abilityDamage,
       )
   }
}
