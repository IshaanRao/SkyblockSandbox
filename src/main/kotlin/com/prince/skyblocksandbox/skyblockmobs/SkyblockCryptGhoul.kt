package com.prince.skyblocksandbox.skyblockmobs

import net.minecraft.server.v1_8_R3.EntityInsentient
import net.minecraft.server.v1_8_R3.GenericAttributes
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Zombie
import org.bukkit.inventory.ItemStack
import java.math.BigInteger

class SkyblockCryptGhoul : SkyblockMob() {
    override var startingHealth: BigInteger = BigInteger.valueOf(2000)
    override val name = "Crypt Ghoul"
    override val level = 30
    override val damage: Int
        get() = 350
    override val entityType = EntityType.ZOMBIE
    override fun load() {
        val zombieEntity = entity as Zombie
        if(zombieEntity.isBaby){
            zombieEntity.isBaby = false
        }
        if(zombieEntity.isVillager) {
            zombieEntity.isVillager = false
        }
        zombieEntity.equipment.boots = ItemStack(Material.CHAINMAIL_BOOTS)
        zombieEntity.equipment.leggings = ItemStack(Material.CHAINMAIL_LEGGINGS)
        zombieEntity.equipment.chestplate = ItemStack(Material.CHAINMAIL_CHESTPLATE)
        zombieEntity.equipment.itemInHand = ItemStack(Material.IRON_SWORD)
        val attributes = ((zombieEntity as CraftEntity).handle as EntityInsentient).getAttributeInstance(
            GenericAttributes.MOVEMENT_SPEED)
        attributes.value =  0.3450000062584877
    }
}