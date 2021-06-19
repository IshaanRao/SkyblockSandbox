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
        val gson = Gson()
        var sword = ItemStack(itemData.material)
        val meta = sword.itemMeta
        meta.spigot().isUnbreakable = true
        meta.displayName = "${itemData.rarity.getColor()}${itemData.name}"
        val lore = ArrayList<String>()
        if(swordStats.damage!=0){
            lore.add(generateCategory("Damage",swordStats.damage, SkyblockColors.RED))
        }
        if(swordStats.strength!=0){
            lore.add(generateCategory("Strength",swordStats.strength, SkyblockColors.RED))
        }
        if(swordStats.critChance!=0){
            lore.add(generateCategory("Crit Chance","${swordStats.critChance}%", SkyblockColors.RED))
        }
        if(swordStats.critDamage!=0){
            lore.add(generateCategory("Crit Damage","${swordStats.critDamage}%", SkyblockColors.RED))
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
        meta.lore = lore
        sword.itemMeta = meta
        val nmsSword = sword.getNms()
        val swordCompound = nmsSword.getNbtTag()
        swordCompound.set("SkyblockSword",NBTTagString(gson.toJson(this)))
        val modifiers = NBTTagList()
        val damage = NBTTagCompound()
        modifiers.add(damage)
        swordCompound.set("AttributeModifiers",modifiers)
        swordCompound.set("HideFlags",NBTTagInt(4))
        nmsSword.tag = swordCompound
        sword = nmsSword.getBukkit()
        return sword
    }
    fun generateCategory(catname:String,value:Any,color:SkyblockColors):String{
        return "§7$catname: $color+$value"
    }
}