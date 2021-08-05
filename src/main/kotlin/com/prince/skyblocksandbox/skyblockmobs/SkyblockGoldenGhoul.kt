package com.prince.skyblocksandbox.skyblockmobs

import net.minecraft.server.v1_8_R3.EntityInsentient
import net.minecraft.server.v1_8_R3.GenericAttributes
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Zombie
import org.bukkit.inventory.ItemStack
import java.math.BigInteger

class SkyblockGoldenGhoul : SkyblockMob() {
    override var startingHealth: BigInteger = BigInteger.valueOf(45000)
    override val name = "Golden Ghoul"
    override val level = 60
    override val damage: Int
        get() = 800
    override val entityType = EntityType.ZOMBIE
    override fun load() {
        val zombieEntity = entity as Zombie
        if(zombieEntity.isBaby){
            zombieEntity.isBaby = false
        }
        if(zombieEntity.isVillager) {
            zombieEntity.isVillager = false
        }
        zombieEntity.equipment.boots = ItemStack(Material.GOLD_BOOTS)
        zombieEntity.equipment.leggings = ItemStack(Material.GOLD_LEGGINGS)
        zombieEntity.equipment.chestplate = ItemStack(Material.GOLD_CHESTPLATE)
        zombieEntity.equipment.itemInHand = ItemStack(Material.GOLD_SWORD)
        ((zombieEntity as CraftEntity).handle as EntityInsentient).getAttributeInstance(
            GenericAttributes.MOVEMENT_SPEED).value =  0.18
    }

    override fun loadName(){
        if(hasSpawned){
            entity!!.customName = "§8[§7Lv$level§8] §6$name ${if(currentHealth<=(startingHealth/BigInteger.valueOf(2))) "§e" else "§a"}${if(currentHealth<=BigInteger.valueOf(0)) 0 else currentHealth}§f/§a$startingHealth§c❤"
        }
    }
}