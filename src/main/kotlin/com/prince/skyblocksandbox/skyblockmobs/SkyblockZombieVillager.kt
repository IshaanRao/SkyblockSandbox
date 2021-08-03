package com.prince.skyblocksandbox.skyblockmobs

import com.prince.skyblocksandbox.SkyblockSandbox.Companion.log
import net.minecraft.server.v1_8_R3.AchievementList.d
import net.minecraft.server.v1_8_R3.EntityInsentient
import net.minecraft.server.v1_8_R3.GenericAttributes
import net.minecraft.server.v1_8_R3.IMonster.d
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Zombie
import org.bukkit.inventory.ItemStack
import java.math.BigInteger


class SkyblockZombieVillager : SkyblockMob() {
    override var startingHealth: BigInteger = BigInteger.valueOf(120)
    override val name = "Zombie Villager"
    override val level = 1
    override val damage: Int
        get() = 24
    override val entityType = EntityType.ZOMBIE
    override fun load() {
        val zombieEntity = entity as Zombie
        if(zombieEntity.isBaby){
            zombieEntity.isBaby = false
        }
        zombieEntity.isVillager = true
        zombieEntity.equipment.boots = ItemStack(Material.LEATHER_BOOTS)
        zombieEntity.equipment.leggings = ItemStack(Material.LEATHER_LEGGINGS)
        zombieEntity.equipment.chestplate = ItemStack(Material.LEATHER_CHESTPLATE)
        zombieEntity.equipment.helmet = ItemStack(Material.LEATHER_HELMET)
        val attributes = ((zombieEntity as CraftEntity).handle as EntityInsentient).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED)
        attributes.value =  0.3450000062584877
    }

}