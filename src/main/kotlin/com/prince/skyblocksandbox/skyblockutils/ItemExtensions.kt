package com.prince.skyblocksandbox.skyblockutils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.prince.skyblocksandbox.skyblockitems.SkyblockItem
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import net.minecraft.server.v1_8_R3.NBTTagCompound
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

object ItemExtensions {
    fun ItemStack.getNms(): net.minecraft.server.v1_8_R3.ItemStack {
        return CraftItemStack.asNMSCopy(this)
    }
    fun net.minecraft.server.v1_8_R3.ItemStack.getBukkit(): ItemStack {
        return CraftItemStack.asBukkitCopy(this)
    }
    fun ItemStack.isSkyblockItem(): Boolean{
        if(this.type==Material.AIR){
            return false
        }
        val nmsItem = getNms()
        val itemCompound = nmsItem.getNbtTag()
        return itemCompound.hasKey("SkyblockItem")
    }
    fun net.minecraft.server.v1_8_R3.ItemStack.getNbtTag(): NBTTagCompound{
        return  if (hasTag()) tag else NBTTagCompound()
    }
    fun ItemStack.getSkyblockData(): SkyblockItem {
        if(isSkyblockItem()){
            val gson = Gson()
            val json = gson.fromJson(getNms().getNbtTag().getString("SkyblockItem"),JsonObject::class.java)
            val itemType = ItemTypes.valueOf(json.get("itemType").toString().replace("\"",""))
            return gson.fromJson(getNms().getNbtTag().getString("SkyblockItem"),itemType.getItemClass())
        }else{
            throw Exception("Not a skyblock item bruh ur so dumb")
        }
    }
}