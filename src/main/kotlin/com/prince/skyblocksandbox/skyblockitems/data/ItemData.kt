package com.prince.skyblocksandbox.skyblockitems.data

import com.prince.skyblocksandbox.skyblockutils.SkyblockRarities
import org.bukkit.Material


data class ItemData(var name:String,var rarity:SkyblockRarities, var material:Material, var reforgable:Boolean)
