package com.prince.skyblocksandbox.skyblockitems

import com.google.gson.Gson
import com.prince.skyblocksandbox.skyblockabilities.AbilityTypes
import com.prince.skyblocksandbox.skyblockitems.data.ItemData
import com.prince.skyblocksandbox.skyblockitems.data.SwordStats
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getBukkit
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getNbtTag
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getNms
import com.prince.skyblocksandbox.skyblockutils.SkyblockColors
import net.minecraft.server.v1_8_R3.*
import org.bukkit.inventory.ItemStack


class SkyblockSword(itemData: ItemData, swordStats: SwordStats, abilityType: AbilityTypes? = null) {
    var itemData:ItemData
    var swordStats:SwordStats
    var abilityType: AbilityTypes?
    init{
        this.itemData = itemData
        this.swordStats = swordStats
        this.abilityType = abilityType
    }
    fun createItem():ItemStack{
        var sword = ItemStack(itemData.material)
        val meta = sword.itemMeta
        meta.spigot().isUnbreakable = true
        meta.displayName = "${itemData.rarity.getColor()}${itemData.name}"
        meta.lore = generateLore()
        sword.itemMeta = meta
        sword = setTags(sword)
        return sword
    }
    fun setTags(sword:ItemStack) : ItemStack{
        val gson = Gson()
        val nmsSword = sword.getNms()
        val swordCompound = nmsSword.getNbtTag()
        swordCompound.set("SkyblockSword",NBTTagString(gson.toJson(this)))
        val modifiers = NBTTagList()
        val damage = NBTTagCompound()
        modifiers.add(damage)
        swordCompound.set("AttributeModifiers",modifiers)
        swordCompound.set("HideFlags",NBTTagInt(4))
        nmsSword.tag = swordCompound
        return nmsSword.getBukkit()
    }
    fun generateCategory(catname:String,value:Any,color:SkyblockColors,reforge:Int):String{
        if(reforge!=0) {
            return "§7$catname: $color+$value §9(${this.swordStats.reforge.name} +$reforge)"
        }else{
            return "§7$catname: $color+$value"
        }
    }
    val statswithreforge:SwordStats
    get() {
        if(swordStats.reforge==null){
            return swordStats
        }
        return SwordStats(
            damage = (swordStats.damage + swordStats.reforge.damage[itemData.rarity]!!),
            strength = (swordStats.strength + swordStats.reforge.strength[itemData.rarity]!!),
            critDamage = (swordStats.critDamage + swordStats.reforge.critDamage[itemData.rarity]!!),
            critChance = (swordStats.critChance + swordStats.reforge.critChance[itemData.rarity]!!),
            intel = (swordStats.intel + swordStats.reforge.intel[itemData.rarity]!!),
        )
    }
    fun generateLore(): List<String> {
        val lore = ArrayList<String>()
        if(statswithreforge.damage!=0){
            lore.add(generateCategory("Damage",statswithreforge.damage, SkyblockColors.RED,swordStats.reforge.damage[itemData.rarity]!!))
        }
        if(statswithreforge.strength!=0){
            lore.add(generateCategory("Strength",statswithreforge.strength, SkyblockColors.RED,swordStats.reforge.strength[itemData.rarity]!!))
        }
        if(statswithreforge.critChance!=0){
            lore.add(generateCategory("Crit Chance","${statswithreforge.critChance}%", SkyblockColors.RED,swordStats.reforge.critChance[itemData.rarity]!!))
        }
        if(statswithreforge.critDamage!=0){
            lore.add(generateCategory("Crit Damage","${statswithreforge.critDamage}%", SkyblockColors.RED,swordStats.reforge.critDamage[itemData.rarity]!!))
        }

        if(statswithreforge.intel!=0){
            lore.add("")
            lore.add(generateCategory("Intelligence",statswithreforge.intel,SkyblockColors.GREEN,swordStats.reforge.intel[itemData.rarity]!!))
        }
        if(abilityType!=null){
            lore.add(" ")
            lore.add("§6${abilityType!!.getAbility().prefix}: ${abilityType!!.getAbility().title} §e§l${abilityType!!.getAbility().action}")
            lore.addAll(abilityType!!.getAbility().desc)
            lore.add("§8Mana Cost: §3"+abilityType!!.getAbility().manaCost)
        }
        lore.add(" ")
        if(itemData.reforgable){
            lore.add("§8This item can be reforged!")
        }
        lore.add("${itemData.rarity.getColor()}§l${itemData.rarity.name} SWORD")
        return lore
    }
}