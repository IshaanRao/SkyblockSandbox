package com.prince.skyblocksandbox.skyblockmobs

import org.bukkit.entity.EntityType
import java.math.BigInteger

class SkyblockZombie(override var startingHealth: BigInteger) : SkyblockMob() {
    override val name = "zomber"
    override val level = 10
    override val entityType = EntityType.ZOMBIE
    override fun load() {

    }

}