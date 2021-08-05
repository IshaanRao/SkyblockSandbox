package com.prince.skyblocksandbox.skyblockabilities

enum class AbilityTypes(private var ability:ItemAbility?=null){
    AOTE(AoteAbility),WITHER_IMPACT(WitherImpact),JUJUSHORTBOW(JujuShortbowAbility),TERMSHORTBOW(TermShortbowAbility),SALVATION(SalvationAbility),NONE;
    fun getAbility() : ItemAbility{
        return ability!!
    }
}