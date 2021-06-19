package com.prince.skyblocksandbox.skyblockabilities

enum class AbilityTypes(private var ability:ItemAbility){
    AOTE(AoteAbility());
    fun getAbility() : ItemAbility{
        return ability
    }
}