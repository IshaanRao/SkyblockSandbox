package com.prince.skyblocksandbox.skyblockitems.data

import com.prince.skyblocksandbox.skyblockutils.SkyblockRarities

data class ReforgeStats(var defense:Map<SkyblockRarities,Int> = createEmptyMap(),var health:Map<SkyblockRarities,Int> = createEmptyMap(),var damage:Map<SkyblockRarities,Int> = createEmptyMap(), var strength:Map<SkyblockRarities,Int> = createEmptyMap(), var critDamage:Map<SkyblockRarities,Int> = createEmptyMap(), var critChance: Map<SkyblockRarities, Int> = createEmptyMap(), var intelligence: Map<SkyblockRarities, Int> = createEmptyMap(),var speed : Map<SkyblockRarities,Int> = createEmptyMap(),var name: String = ""){
    companion object {
        fun createEmptyMap(): Map<SkyblockRarities,Int>{
            val map = HashMap<SkyblockRarities,Int>()
            for(rarity in SkyblockRarities.values()){
                map[rarity] = 0
            }
            return map
        }
        fun createMap(common:Int,uncommon:Int,rare:Int,epic:Int,leg:Int,mythic:Int):Map<SkyblockRarities,Int>{
            val map = HashMap<SkyblockRarities,Int>()
            map[SkyblockRarities.COMMON] = common
            map[SkyblockRarities.UNCOMMON] = uncommon
            map[SkyblockRarities.RARE] = rare
            map[SkyblockRarities.EPIC] = epic
            map[SkyblockRarities.LEGENDARY] = leg
            map[SkyblockRarities.MYTHIC] = mythic
            return map
        }
    }
}
