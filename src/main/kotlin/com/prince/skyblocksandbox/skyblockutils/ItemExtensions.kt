package com.prince.skyblocksandbox.skyblockutils

import com.google.gson.Gson
import com.prince.skyblocksandbox.skyblockabilities.AbilityTypes
import com.prince.skyblocksandbox.skyblockabilities.ItemAbility
import com.prince.skyblocksandbox.skyblockitems.SkyblockSword
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getNbtTag
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getNms
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.isSkyblockSword
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
    fun ItemStack.isSkyblockSword(): Boolean{
        if(this.type==Material.AIR){
            return false
        }
        val nmsItem = getNms()
        val itemCompound = nmsItem.getNbtTag()
        return itemCompound.hasKey("SkyblockSword")
    }
    fun net.minecraft.server.v1_8_R3.ItemStack.getNbtTag(): NBTTagCompound{
        return  if (hasTag()) tag else NBTTagCompound()
    }
    fun ItemStack.getSwordData(): SkyblockSword{
        if(isSkyblockSword()){
            val gson = Gson()
            return gson.fromJson(getNms().getNbtTag().getString("SkyblockSword"),SkyblockSword::class.java)
        }else{
            throw Exception("Not a sword bruh ur so dumb")
        }
    }
}