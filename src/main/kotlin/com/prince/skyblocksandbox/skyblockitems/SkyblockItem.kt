package com.prince.skyblocksandbox.skyblockitems

import com.google.gson.Gson
import com.prince.skyblocksandbox.skyblockitems.data.ItemData
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import com.prince.skyblocksandbox.skyblockutils.ColorUtils
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getBukkit
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getNbtTag
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getNms
import com.prince.skyblocksandbox.skyblockutils.SkyblockColors
import net.minecraft.server.v1_8_R3.NBTTagCompound
import net.minecraft.server.v1_8_R3.NBTTagInt
import net.minecraft.server.v1_8_R3.NBTTagList
import net.minecraft.server.v1_8_R3.NBTTagString
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta

abstract class SkyblockItem(var itemData: ItemData, var itemType: ItemTypes) {
    fun createItem(p: Player,stats:StatsData?=null) : ItemStack{
        var item = ItemStack(itemData.item.mat)
        val meta = item.itemMeta
        if(itemData.item.isColored){
            (meta as LeatherArmorMeta).color = ColorUtils.hex2Rgb(itemData.item.color)
        }
        meta.spigot().isUnbreakable = true
        meta.displayName =
            "${itemData.rarity.getColor()}${if (itemsReforged()) "${itemData.reforge.name} " else ""}${itemData.name}"
        meta.lore = createLore(p,stats)
        item.itemMeta = meta
        item = setTags(item)
        return item
    }

    fun generateCategory(catname:String,value:Any,color:SkyblockColors,reforge:Int,hpb:Boolean=false):String{
        val additions = StringBuilder()
        if(itemData.hpbs!=0&&hpb){
            additions.append(" §e(+${(2*itemData.hpbs)})")
        }
        if(reforge!=0){
            additions.append(" §9(${this.itemData.reforge.name} +$reforge)")
        }
        return "§7$catname: $color+$value$additions"
    }
    val statsWithReforge: ItemData
        get() {
            return ItemData(
                name = itemData.name,
                rarity = itemData.rarity,
                item = itemData.item,
                reforgable = itemData.reforgable,
                defense = itemData.defense + itemData.reforge.defense[itemData.rarity]!!.toBigInteger(),
                health = itemData.health + itemData.reforge.health[itemData.rarity]!!.toBigInteger(),
                damage = itemData.damage + itemData.reforge.damage[itemData.rarity]!!.toBigInteger(),
                strength = itemData.strength + itemData.reforge.strength[itemData.rarity]!!.toBigInteger(),
                critDamage = itemData.critDamage + itemData.reforge.critDamage[itemData.rarity]!!.toBigInteger(),
                critChance = itemData.critChance + itemData.reforge.critChance[itemData.rarity]!!,
                intelligence = itemData.intelligence + itemData.reforge.intelligence[itemData.rarity]!!.toBigInteger(),
                speed = itemData.speed + itemData.reforge.speed[itemData.rarity]!!,
                abilityDamage = itemData.abilityDamage,
                extra = itemData.extra,
                ability = itemData.ability,
                reforge = itemData.reforge,
                hpbs = itemData.hpbs,
                enchants = itemData.enchants
            )
        }
    fun itemsReforged():Boolean{
        return(itemData.reforge.name!="")
    }
    abstract val trueStats:ItemData
    abstract fun createLore(p:Player,stats:StatsData?=null):List<String>
    fun setTags(item:ItemStack):ItemStack{
        val gson = Gson()
        val nmsItem = item.getNms()
        val itemCompound = nmsItem.getNbtTag()
        itemCompound.set("SkyblockItem",NBTTagString(gson.toJson(this)))
        val modifiers = NBTTagList()
        val damage = NBTTagCompound()
        modifiers.add(damage)
        itemCompound.set("AttributeModifiers",modifiers)
        itemCompound.set("HideFlags", NBTTagInt(4))
        nmsItem.tag = itemCompound
        return nmsItem.getBukkit()
    }
}