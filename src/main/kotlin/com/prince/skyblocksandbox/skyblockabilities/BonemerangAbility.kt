package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockabilities.SalvationAbility.getNearbyEntities
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler.Companion.isSkyblockMob
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

object BonemerangAbility : ItemAbility() {
    override val manaCost=0
    override val actions = listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)
    override val abilityType=AbilityTypes.BONEMERANG
    override val title = "§6Ability: Swing §e§lRIGHT CLICK"
    override val name: String = "Swing"
    override val itemType = ItemTypes.BOW
    override fun getDesc(stats: StatsData) : List<String> { return listOf("§7Throw the bone a short distance","§7dealing the damage an arrow","§7would.","","§7Deals §cdouble damage§7 when","§7coming back.")}
    override val noUseMessage = true
    override val specialAbility = true
    override fun execute(e: PlayerInteractEvent) {
        startCooldown(e.player,41)
        val player = e.player
        val playerDirection = player.location.direction
        val hologram = player.world.spawnEntity(player.location, EntityType.ARMOR_STAND) as ArmorStand
        hologram.setGravity(false)
        hologram.canPickupItems = false
        hologram.isCustomNameVisible = false
        hologram.isVisible = false
        hologram.isMarker = true
        hologram.itemInHand = ItemStack(Material.BONE)
        hologram.setArms(true)
        var timesRan = 0
        object : BukkitRunnable() {
            override fun run() {
                timesRan++
                val nearbyEntities = hologram.getNearbyEntities(.250, 1.250, .250)
                for(entity in nearbyEntities){
                    val mob = entity.isSkyblockMob() ?: continue
                    SkyblockSandbox.instance.damageHandler.bowDamage(mob,player,true,if(timesRan>20) 2.0 else 1.0)
                }
                if(timesRan<=20){
                    hologram.rightArmPose = hologram.rightArmPose.add(0.0,-60.0,0.0)
                    hologram.teleport(hologram.location.add(playerDirection.clone().multiply(0.700)))
                }else if(timesRan<=40){
                    hologram.rightArmPose = hologram.rightArmPose.add(0.0,-60.0,0.0)
                    hologram.teleport(hologram.location.add(player.location.toVector().subtract(hologram.location.toVector()).normalize().multiply(0.700)))
                }else{
                    hologram.remove()
                    cancel()
                    return
                }
            }
        }.runTaskTimer(SkyblockSandbox.instance,0,1)
    }
}
