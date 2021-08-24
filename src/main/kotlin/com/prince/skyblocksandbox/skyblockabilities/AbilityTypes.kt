package com.prince.skyblocksandbox.skyblockabilities

enum class AbilityTypes(private var ability:ItemAbility?=null){
    AOTE(AoteAbility),
    WITHER_IMPACT(WitherImpact),
    JUJUSHORTBOW(JujuShortbowAbility),
    TERMSHORTBOW(TermShortbowAbility),
    SALVATION(SalvationAbility),
    GIANTSSLAM(GiantSwordAbility),
    TERRAINTOSS(YetiSwordAbility),
    AOTSTHROW(AotsThrowAbility),
    AOTSLORE(AotsLoreAbility),
    MOLTENWAVE(MoltenWaveAbility),
    BONEMERANG(BonemerangAbility)
    ,NONE;
    fun getAbility() : ItemAbility{
        return ability!!
    }
}