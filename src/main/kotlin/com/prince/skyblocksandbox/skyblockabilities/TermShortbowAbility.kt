package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import org.bukkit.Location
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector
import sun.audio.AudioPlayer.player


object TermShortbowAbility : ItemAbility() {
    override val manaCost=0
    override val actions = listOf(Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK)
    override val AbilityType=AbilityTypes.TERMSHORTBOW
    override val title = "§6Shortbow: Instantly shoots!"
    override val name: String = "N/A"
    override val itemType = ItemTypes.BOW
    override val specialAbility = true
    override fun getDesc(stats: StatsData) : List<String> { return listOf("§7Shoots §b3 §7arrows at once","§7Can damage endermen")}
    override fun execute(e: PlayerInteractEvent) {
        if(playerOnCooldown(e.player)){
            return
        }
        if(e.action=== Action.RIGHT_CLICK_BLOCK||e.action=== Action.RIGHT_CLICK_AIR){
            e.isCancelled = true
        }
        val p = e.player

        val arrow = p.launchProjectile(Arrow::class.java,p.location.direction)
        val arrowLeft = p.launchProjectile(Arrow::class.java,p.location.direction)
        val arrowRight  = p.launchProjectile(Arrow::class.java,p.location.direction)

        arrow.isCritical = true
        arrowLeft.isCritical = true
        arrowRight.isCritical = true

        val locationLeft = p.eyeLocation.add(getLeftHeadDirection(p).multiply(.75))
        val locationRight = p.eyeLocation.add(getRightHeadDirection(p).multiply(.75))

        arrowLeft.teleport(Location(locationLeft.world,locationLeft.x,locationLeft.y,locationLeft.z))
        arrowRight.teleport(Location(locationRight.world,locationRight.x,locationRight.y,locationRight.z))

        arrow.velocity = arrow.velocity.multiply(3)
        arrowLeft.velocity = arrowLeft.velocity.multiply(3)
        arrowRight.velocity = arrowRight.velocity.multiply(3)

        startCooldown(e.player,5)
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