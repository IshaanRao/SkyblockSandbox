package com.prince.skyblocksandbox.skyblockmobs

import java.util.*
import kotlin.collections.ArrayList

enum class SkyblockMobs {
    ZOMBIE,ZOMBIEVILLAGER,CRYPTGHOUL,GOLDENGHOUL,SKELETON;
    fun getMob() : SkyblockMob {
        return when (this) {
            ZOMBIE -> SkyblockZombie()
            ZOMBIEVILLAGER -> SkyblockZombieVillager()
            CRYPTGHOUL -> SkyblockCryptGhoul()
            GOLDENGHOUL -> SkyblockGoldenGhoul()
            SKELETON -> SkyblockSkeleton()
        }
    }
    companion object {
        fun getValues(): ArrayList<String>{
            val arrayList = ArrayList<String>()
            for(value in values()){
                arrayList.add(value.name.toLowerCase(Locale.getDefault()))
            }
            return arrayList
        }
    }
}