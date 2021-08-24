package com.prince.skyblocksandbox.skyblockitems.data

import java.util.*

enum class ArmorTypes {
    HELMET,CHESTPLATE,LEGGINGS,BOOTS;
    fun getDispName():String {
        var str = this.name
        str = str.lowercase(Locale.getDefault())
        str = str.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        return str
    }
}