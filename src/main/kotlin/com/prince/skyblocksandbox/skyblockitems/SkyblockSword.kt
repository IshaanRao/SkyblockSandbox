package com.prince.skyblocksandbox.skyblockitems

import com.prince.skyblocksandbox.skyblockabilities.AbilityTypes
import com.prince.skyblocksandbox.skyblockitems.data.ItemData
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import com.prince.skyblocksandbox.skyblockutils.SkyblockColors
import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.getStats
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class SkyblockSword(itemData: ItemData) : SkyblockItem(itemData,ItemTypes.SWORD) {
    override val trueStats: ItemData
        get() {
            val data = statsWithReforge
            data.damage = data.damage + (itemData.hpbs*2).toBigInteger()
            data.strength = data.strength + (itemData.hpbs*2).toBigInteger()
            return data
        }

    override fun createLore(p: Player,stats:StatsData?): List<String> {
        val lore = ArrayList<String>()
        if(trueStats.damage!=0.toBigInteger()){
            lore.add(generateCategory("Damage",trueStats.damage, SkyblockColors.RED,trueStats.reforge.damage[itemData.rarity]!!, true))
        }
        if(trueStats.strength!=0.toBigInteger()){
            lore.add(generateCategory("Strength",trueStats.strength, SkyblockColors.RED,trueStats.reforge.strength[itemData.rarity]!!, true))
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
        if(itemData.ability!=AbilityTypes.NONE){
            lore.add(" ")
            lore.add("§6${itemData.ability.getAbility().prefix}: ${itemData.ability.getAbility().title} §e§l${itemData.ability.getAbility().action}")
            lore.addAll(itemData.ability.getAbility().getDesc(stats ?: p.getStats()))
            lore.add("§8Mana Cost: §3"+itemData.ability.getAbility().manaCost)
        }
        lore.add(" ")
        if(itemData.reforgable){
            lore.add("§8This item can be reforged!")
        }
        lore.add("${itemData.rarity.getColor()}§l${itemData.rarity.name} SWORD")
        return lore
    }

}