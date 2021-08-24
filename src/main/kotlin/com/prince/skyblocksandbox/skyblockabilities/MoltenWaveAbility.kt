package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockabilities.SalvationAbility.getNearbyEntities
import com.prince.skyblocksandbox.skyblockhandlers.DamageHandler
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler.Companion.isSkyblockMob
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

object MoltenWaveAbility : ItemAbility(){
    override val manaCost=500
    override val actions = listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)
    override val abilityType=AbilityTypes.MOLTENWAVE
    override val title = "§6Ability: Molten Wave §e§lRIGHT CLICK"
    override val name: String = "Molten Wave"
    override val itemType = ItemTypes.SWORD
    override val ability = SkyblockAbility(32000,0.3)
    override fun getDesc(stats: StatsData) : List<String> {
        val damage = ((stats.abilityDamage+ ability.abilityDamage) * ((1+(stats.intelligence.toDouble()/100))* ability.multiplier)).toBigDecimal().toBigInteger()
        return listOf("§7Cast a wave of molten gold","§7direction you are facing!","§7Deals up to §c${damage} §7damage.")
    }

    override fun execute(e: PlayerInteractEvent) {
        val player = e.player
        val playerDir = Vector(player.location.direction.x,0.0,player.location.direction.z)
        val locationLeft = player.eyeLocation.add(TermShortbowAbility.getLeftHeadDirection(player).multiply(1))
        val locationRight = player.eyeLocation.add(TermShortbowAbility.getRightHeadDirection(player).multiply(1))
        val blockLocLeft = Location(locationLeft.world,locationLeft.x,locationLeft.y,locationLeft.z)
        val blockLocMiddle = player.eyeLocation
        val blockLocRight = Location(locationRight.world,locationRight.x,locationRight.y,locationRight.z)
        var timesRan = 0
        var damageDone = 0.toBigInteger()
        var entitiesHit = 0
        object : BukkitRunnable() {
            override fun run() {
                if(timesRan==10){
                    if(entitiesHit!=0) {
                        player.sendMessage(
                            "§7Your Molten Wave hit §c$entitiesHit §7${if (entitiesHit == 1) "enemy" else "enemies"} for §c${
                                "%,d".format(
                                    damageDone
                                )
                            }§7 damage"
                        )
                    }
                    cancel()
                    return
                }
                val leftLoc = blockLocLeft.add(playerDir)
                val midLoc = blockLocMiddle.add(playerDir)
                val rightLoc = blockLocRight.add(playerDir)
                addBlock(leftLoc)
                addBlock(midLoc)
                addBlock(rightLoc)
                val entities = leftLoc.getNearbyEntities(0.5,0.5,0.5).plus(midLoc.getNearbyEntities(0.5,0.5,0.5).plus(rightLoc.getNearbyEntities(0.5,0.5,0.5)))
                for(entity in entities){
                    val sbMob = entity.isSkyblockMob() ?: continue
                    damageDone+=DamageHandler.magicDamage(sbMob,player,MoltenWaveAbility).damage
                    entitiesHit++
                }
                timesRan++
            }
        }.runTaskTimer(SkyblockSandbox.instance,0,1)
    }
    fun addBlock(loc:Location){
        val item = ItemStack(Material.GOLD_BLOCK)
        val fallingBlock = loc.world.spawnFallingBlock(loc,item.type,item.durability.toByte())
        fallingBlock.dropItem = false
    }
    fun getRightHeadDirection(player: Player): Vector {
        val direction = player.location.direction.normalize()
        return Vector(-direction.z, 0.0, direction.x).normalize()
    }

    fun getLeftHeadDirection(player: Player): Vector {
        val direction = player.location.direction.normalize()
        return Vector(direction.z, 0.0, -direction.x).normalize()
    }
}