package com.prince.skyblocksandbox.skyblockenchants

enum class SkyblockEnchants(var obj: SkyblockEnchant){
    SHARP(EnchantSharpness),SMITE(EnchantSmite),BANE(EnchantBaneOfArthropods),CUBISM(EnchantCubism),ENDER_SLAYER(EnchantEnderSlayer),DRAGON_HUNTER(EnchantDragonHunter),IMPALING(EnchantImpaling),GIANT_KILLER(EnchantGiantKiller);
    companion object {
        fun getEnchantFromName(name: String): SkyblockEnchant?{
            for(ench in values()){
                if(ench.obj.name.equals(name,true)){
                    return ench.obj
                }
            }
            return null
        }
        fun getEnumFromEnchant(enchant: SkyblockEnchant):SkyblockEnchants?{
            for(ench in values()){
                if(ench.obj==enchant){
                    return ench
                }
            }
            return null
        }
    }
}