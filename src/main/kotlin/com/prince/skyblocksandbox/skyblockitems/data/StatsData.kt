package com.prince.skyblocksandbox.skyblockitems.data

import java.math.BigInteger

data class StatsData(var defense: BigInteger = 0.toBigInteger(),
                     var health: BigInteger = 0.toBigInteger(),
                     var damage: BigInteger = 0.toBigInteger(),
                     var strength: BigInteger =0.toBigInteger(),
                     var critDamage: BigInteger = 0.toBigInteger(),
                     var critChance:Int = 0,
                     var intelligence: BigInteger =0.toBigInteger(),
                     var speed:Int = 0,
                     var abilityDamage:Double = 0.0,
                     var extra:Int = 0,
){
    fun add(statsData:StatsData){
        defense += statsData.defense
        health += statsData.health
        damage += statsData.damage
        strength += statsData.strength
        critDamage += statsData.critDamage
        critChance += statsData.critChance
        speed += statsData.speed
        intelligence += statsData.intelligence
        abilityDamage += statsData.abilityDamage
        extra += statsData.extra
    }
}