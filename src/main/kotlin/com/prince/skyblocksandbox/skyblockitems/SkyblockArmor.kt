package com.prince.skyblocksandbox.skyblockitems

import com.prince.skyblocksandbox.skyblockitems.data.ArmorTypes
import com.prince.skyblocksandbox.skyblockitems.data.ItemData
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import com.prince.skyblocksandbox.skyblockutils.SkyblockColors
import org.bukkit.entity.Player

class SkyblockArmor(itemData: ItemData,val armorType: ArmorTypes) : SkyblockItem(itemData,ItemTypes.ARMOR) {
    override val trueStats: ItemData
        get() {
            val data = statsWithReforge
            data.health = data.health + (itemData.hpbs*4).toBigInteger()
            data.defense = data.defense + (itemData.hpbs*2).toBigInteger()
            return data
        }
    override fun createLore(p: Player,stats:StatsData?): List<String> {
        val lore = ArrayList<String>()
        if(trueStats.damage!=0.toBigInteger()){
            lore.add(generateCategory("Damage",trueStats.damage, SkyblockColors.RED,trueStats.reforge.damage[itemData.rarity]!!))
        }
        if(trueStats.strength!=0.toBigInteger()){
            lore.add(generateCategory("Strength",trueStats.strength, SkyblockColors.RED,trueStats.reforge.strength[itemData.rarity]!!))
        }
        if(trueStats.critChance!=0){
            lore.add(generateCategory("Crit Chance","${trueStats.critChance}%", SkyblockColors.RED,trueStats.reforge.critChance[itemData.rarity]!!))
        }
        if(trueStats.critDamage!=0.toBigInteger()){
            lore.add(generateCategory("Crit Damage","${trueStats.critDamage}%", SkyblockColors.RED,trueStats.reforge.critDamage[itemData.rarity]!!))
        }
        if(trueStats.intelligence!=0.toBigInteger()||trueStats.speed!=0){
            lore.add("")
        }
        if(trueStats.health!=0.toBigInteger()){
            lore.add(generateCategory("Health",trueStats.health, SkyblockColors.GREEN,trueStats.reforge.health[itemData.rarity]!!))
        }
        if(trueStats.defense!=0.toBigInteger()){
            lore.add(generateCategory("Defense",trueStats.defense, SkyblockColors.GREEN,trueStats.reforge.defense[itemData.rarity]!!))
        }
        if(trueStats.speed!=0){
            lore.add(generateCategory("Speed",trueStats.intelligence, SkyblockColors.GREEN,trueStats.reforge.speed[itemData.rarity]!!))
        }
        if(trueStats.intelligence!=0.toBigInteger()){
            lore.add(generateCategory("Intelligence",trueStats.intelligence, SkyblockColors.GREEN,trueStats.reforge.intelligence[itemData.rarity]!!))
        }
        lore.add(" ")
        if(itemData.reforgable){
            lore.add("§8This item can be reforged!")
        }
        lore.add("${itemData.rarity.getColor()}§l${itemData.rarity.name} ${armorType.name}")
        return lore
    }
}