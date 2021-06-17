package com.prince.skyblocksandbox.skyblockmobs

import com.prince.skyblocksandbox.skyblockhandlers.MobHandler
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Zombie
import org.bukkit.metadata.MetadataValue
import java.math.BigInteger
class SkyblockZombie(mobHandler: MobHandler,override var startingHealth: BigInteger) : SkyblockMob(mobHandler) {
    override val name = "zomber"
    override val level = 10
    override val entityType = EntityType.ZOMBIE
    override fun load() {

    }

}