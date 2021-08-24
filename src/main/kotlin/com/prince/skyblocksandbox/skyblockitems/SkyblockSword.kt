package com.prince.skyblocksandbox.skyblockitems

import com.prince.skyblocksandbox.skyblockenchants.SkyblockEnchant
import com.prince.skyblocksandbox.skyblockitems.data.ItemData
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import com.prince.skyblocksandbox.skyblockutils.SkyblockColors
import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.getStats
import org.bukkit.entity.Player

//a
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
        if(itemData.enchants.size!=0){
            lore.add(" ")
            val enchantLore = ArrayList<String>()
            val firstEnch = itemData.enchants.keys.first()
            var string = createEnchantString(firstEnch.obj, itemData.enchants[firstEnch]!!)
            itemData.enchants.keys.forEachIndexed { index, skyblockEnchant ->
                if(index!=0){
                    if(index%3==0){
                        enchantLore.add(string)
                        string=""
                    }
                    string+=", ${createEnchantString(skyblockEnchant.obj, itemData.enchants[skyblockEnchant]!!)}"
                }
            }
            enchantLore.add(string)
            lore.addAll(enchantLore)
        }
        if(itemData.abilities!=null) {
            for (abilityEnum in itemData.abilities!!) {
                val ability = abilityEnum.getAbility()
                lore.add(" ")
                lore.add(ability.title)
                lore.addAll(ability.getDesc(stats ?: p.getStats()))
                if(!ability.specialAbility&&ability.manaCost!=0) {
                    lore.add("§8Mana Cost: §3" + ability.manaCost)
                }
            }
        }
        lore.add(" ")
        if(itemData.reforgable){
            lore.add("§8This item can be reforged!")
        }
        lore.add("${itemData.rarity.getColor()}§l${itemData.rarity} SWORD")
        return lore
    }
    fun createEnchantString(enchant:SkyblockEnchant,level:Int):String{
        return "§9${enchant.name} $level"
    }
}