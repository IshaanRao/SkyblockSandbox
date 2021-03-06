package com.prince.skyblocksandbox.skyblockenchants

import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import org.bukkit.entity.Player

interface SkyblockEnchant {
    val levelRange: IntRange
    val name: String
    val items:ItemTypes
    fun descAtLevel(level: Int) : List<String>
    val isUltimate: Boolean
        get() = false
    val affectsAbility: Boolean
        get() = false
    fun getAddedDamage(mob:SkyblockMob,player: Player,level:Int):Double
}