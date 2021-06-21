package com.prince.skyblocksandbox.skyblockabilities

enum class AbilityTypes(private var ability:ItemAbility){
    AOTE(AoteAbility()),WITHER_IMPACT(WitherImpact());
    fun getAbility() : ItemAbility{
        return ability
    }
}