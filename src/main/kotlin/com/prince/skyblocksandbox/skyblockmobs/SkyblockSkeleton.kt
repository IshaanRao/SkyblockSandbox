package com.prince.skyblocksandbox.skyblockmobs

import org.bukkit.entity.EntityType
import org.bukkit.entity.Skeleton
import java.math.BigInteger

class SkyblockSkeleton : SkyblockMob() {
    override var startingHealth: BigInteger = BigInteger.valueOf(200)
    override val name = "Skeleton"
    override val level = 6
    override val damage: Int
        get() = 47
    override val entityType = EntityType.SKELETON
    override fun load() {
        val skeletonEntity = entity as Skeleton
        skeletonEntity.skeletonType = Skeleton.SkeletonType.NORMAL
    }
}