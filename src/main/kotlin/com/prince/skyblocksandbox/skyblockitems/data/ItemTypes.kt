package com.prince.skyblocksandbox.skyblockitems.data

import com.prince.skyblocksandbox.skyblockitems.SkyblockItem
import com.prince.skyblocksandbox.skyblockitems.SkyblockSword
import com.prince.skyblocksandbox.skyblockitems.SkyblockArmor

enum class ItemTypes {
    SWORD,ARMOR;
    fun getItemClass():Class<out SkyblockItem>{
        return when(this){
            SWORD -> SkyblockSword::class.java
            ARMOR -> SkyblockArmor::class.java
        }
    }
}