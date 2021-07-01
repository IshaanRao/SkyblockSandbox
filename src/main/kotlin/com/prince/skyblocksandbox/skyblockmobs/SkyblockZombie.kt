package com.prince.skyblocksandbox.skyblockmobs

import net.minecraft.server.v1_8_R3.EntityInsentient
import net.minecraft.server.v1_8_R3.GenericAttributes
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Zombie
import java.math.BigInteger


class SkyblockZombie : SkyblockMob() {
    override var startingHealth: BigInteger = BigInteger.valueOf(100)
    override val name = "Zombie"
    override val level = 1
    override val damage: Int
        get() = 20
    override val entityType = EntityType.ZOMBIE
    override fun load() {
        val zombieEntity = entity as Zombie
        if(zombieEntity.isBaby){
            zombieEntity.isBaby = false;
        }
        if(zombieEntity.isVillager){
            zombieEntity.isVillager = false;
        }
    }

}