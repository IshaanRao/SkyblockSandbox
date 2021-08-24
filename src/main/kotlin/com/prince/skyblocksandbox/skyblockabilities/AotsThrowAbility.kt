package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockabilities.SalvationAbility.getNearbyEntities
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler.Companion.isSkyblockMob
import com.prince.skyblocksandbox.skyblockhandlers.StatisticHandler
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Arrow
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

object AotsThrowAbility : ItemAbility() {
    override val manaCost=20
    override val actions = listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)
    override val AbilityType=AbilityTypes.AOTSTHROW
    override val title = "§6Ability: Throw §e§lRIGHT CLICK"
    override val name: String = "Throw"
    override val itemType = ItemTypes.SWORD
    override val specialAbility = true
    override val noUseMessage: Boolean
        get() = true
    override fun getDesc(stats: StatsData) : List<String> { return listOf("§7Throw your axe damaging all","§7enemies in its path dealing","§c10% §7melee damage.","§7Consecutive throws stack §c2x","§7damage but cost §92x §7mana up","§7to §a16x")}
    val playerMultipliers = HashMap<Player,Int>()
    override fun execute(e: PlayerInteractEvent) {
        val multiplier = getMultipliers(e.player)
        if (StatisticHandler.getPlayerStats(e.player).mana < manaCost.toBigInteger()*multiplier.toBigInteger()) {
            e.player.sendMessage("§cYou do not have enough mana to use this ability")
            return
        }
        StatisticHandler.removeMana(e.player,manaCost.toBigInteger()*multiplier.toBigInteger())
        startCooldown(e.player,2)
        addUse(e.player)
        val player = e.player
        val hologram: ArmorStand = player.world.spawnEntity(player.location, EntityType.ARMOR_STAND) as ArmorStand
        hologram.setGravity(false)
        hologram.canPickupItems = false
        hologram.isCustomNameVisible = false
        hologram.isVisible = false
        hologram.isMarker = true
        hologram.itemInHand = ItemStack(Material.DIAMOND_AXE)
        hologram.setArms(true)
        var timesRan = 0
        object : BukkitRunnable() {
            override fun run() {
                timesRan++
                hologram.teleport(hologram.location.add(hologram.location.direction.multiply(1.05)))
                if(hologram.location.add(0.0,1.0,0.0).block!=null&&hologram.location.add(0.0,1.0,0.0).block.type!= Material.AIR){
                    hologram.remove()
                    cancel()
                    return
                }
                val nearbyMobs = hologram.location.add(0.0,1.0,0.0).getNearbyEntities(0.3,0.3,0.3).filter { entity -> entity.isSkyblockMob() != null }
                for(mob in nearbyMobs){
                    SkyblockSandbox.instance.damageHandler.swordDamage(mob.isSkyblockMob()!!,player,(0.1*multiplier))
                }
                hologram.rightArmPose = hologram.rightArmPose.add(-75.0,0.0,0.0)
                if(timesRan>=70){
                    hologram.remove()
                    cancel()
                    return
                }
            }
        }.runTaskTimer(SkyblockSandbox.instance,0,1)
    }
    fun addUse(player: Player){
        val prevMult:Int = playerMultipliers[player] ?: 0
        val currentMult = prevMult+1
        playerMultipliers[player] = currentMult
        Bukkit.getScheduler().scheduleSyncDelayedTask(SkyblockSandbox.instance,{
            val mult = playerMultipliers[player] ?: return@scheduleSyncDelayedTask
            if(mult == currentMult){
                playerMultipliers.remove(player)
                return@scheduleSyncDelayedTask
            }
        },60)
    }
    fun getMultipliers(player: Player):Int{
        val current = playerMultipliers[player] ?: 0
        return when (current) {
            0 -> 1
            1 -> 2
            2 -> 4
            3 -> 8
            4 -> 16
            else -> 16
        }
    }
}