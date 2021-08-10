package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockhandlers.DamageHandler
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler.Companion.isSkyblockMob
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import net.minecraft.server.v1_8_R3.NBTTagCompound
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.math.BigInteger


object GiantSwordAbility : ItemAbility() {
    override val manaCost=100
    override val actions = listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)
    override val AbilityType=AbilityTypes.GIANTSSLAM
    override val title = "§6Ability: Giant's Slam §e§lRIGHT CLICK"
    override val name: String = "Giant's Slam"
    override val itemType = ItemTypes.SWORD
    override fun getDesc(stats: StatsData) : List<String> {
        val damage = ((stats.abilityDamage+ability.abilityDamage) * ((1+(stats.intelligence.toDouble()/100))*ability.multiplier)).toBigDecimal().toBigInteger()
        return listOf("§7Slam your sword into the ground","§7dealing §c${damage} §7damage to","§7nearby enemies")
    }
    override val ability = SkyblockAbility(100000,0.05)
    override fun execute(e: PlayerInteractEvent) {
        startCooldown(e.player,600)
        val player = e.player
        val giant = player.world.spawnEntity(Location(player.world,player.location.x,player.location.y,player.location.z), EntityType.GIANT) as LivingEntity
        giant.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY,1000,1))
        var giantPos = giant.location.add(0.0,1.0,0.0)
        giantPos = Location(giantPos.world,giantPos.x,giantPos.y,giantPos.z,giant.location.yaw,giant.location.pitch)
        giant.customName = "Dinnerbone"
        giant.teleport(giantPos)
        giant.equipment.itemInHand = ItemStack(Material.IRON_SWORD)
        setInvulnerable(giant)
        freezeEntity(giant)
        val nearbyEntities =
            e.player.getNearbyEntities(6.0, 6.0, 6.0).filter { entity -> entity.isSkyblockMob() != null }
        var damage = BigInteger.valueOf(0)
        for (entity in nearbyEntities) {
            val mob = entity.isSkyblockMob()!!
            damage += DamageHandler.magicDamage(mob, e.player, this).damage
        }
        if (nearbyEntities.isNotEmpty()) {
            e.player.sendMessage(
                "§7Your Giant's Sword hit §c${nearbyEntities.size} §7${if (nearbyEntities.size == 1) "enemy" else "enemies"} for §c${
                    "%,d".format(
                        damage
                    )
                }§7 damage"
            )
        }
        object : BukkitRunnable() {
            override fun run() {
                giant.remove()
            }
        }.runTaskLater(SkyblockSandbox.instance,50)


    }
    fun setInvulnerable(en: Entity) {
        val nmsEn = (en as CraftEntity).handle
        val compound = NBTTagCompound()
        nmsEn.c(compound)
        compound.setByte("Invulnerable", 1.toByte())
        nmsEn.f(compound)
    }
    fun freezeEntity(en: Entity) {
        val nmsEn = (en as CraftEntity).handle
        val compound = NBTTagCompound()
        nmsEn.c(compound)
        compound.setByte("NoAI", 1.toByte())
        nmsEn.f(compound)
    }
}