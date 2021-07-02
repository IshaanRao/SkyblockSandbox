package com.prince.skyblocksandbox.skyblockenchants

import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import org.bukkit.entity.Player

interface SkyblockEnchant {
    val levelRange: IntRange
    val name: String
    val items:ItemTypes
    fun getAddedDamage(mob:SkyblockMob,player: Player):Double
}