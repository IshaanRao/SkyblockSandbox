package com.prince.skyblocksandbox.skyblockutils

import net.minecraft.server.v1_8_R3.EntityInsentient
import net.minecraft.server.v1_8_R3.EntityPlayer
import net.minecraft.server.v1_8_R3.IAttribute
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.util.BlockIterator


object Extensions {
    /**
     * **Warning!** If you are going to set attribute to player, refer to Player.setAttribute!<br></br>
     *
     * Sets attribute of current entity to provided value if it doesnt exist,<br></br>
     * otherwise adds this value.
     * @author maxus
     * @param attribute Attribute to be modified. Get one with <code> GenericAttributes.NAME </code>
     */
    fun Entity.setAttribute(attribute: IAttribute, value: Double) {
        val nmsEntity = (this as CraftEntity).handle as EntityInsentient
        val attr = nmsEntity.getAttributeInstance(attribute)
        attr.value += value
    }

    /**
     * Sets attribute of current player to provided value if it doesnt exist,<br></br>
     * otherwise adds this value.
     * @author maxus
     * @param attribute Attribute to be modified. Get one with <code> GenericAttributes.NAME </code>
     */
    fun Player.setAttribute(attribute: IAttribute, value: Double) {
        val nmsEntity = (this as CraftEntity).handle as EntityPlayer
        val attr = nmsEntity.getAttributeInstance(attribute)
        attr.value += value
    }

    // dont ask me where i got this number
    const val DEFAULT_PLAYER_SPEED = 0.320025F
    /**
     * Makes it easier to set speed of player
     * @author maxus
     */
    fun Player.addSpeed(amount: Int) {
        val trueAmount = amount / 300f
        this.walkSpeed = if(trueAmount + walkSpeed >= 1f) walkSpeed else if(walkSpeed + trueAmount < DEFAULT_PLAYER_SPEED) DEFAULT_PLAYER_SPEED else walkSpeed + trueAmount
    }

    /**
     * Checks whether the player is in creative mode
     * @author maxus
     */
    fun Player.creative() : Boolean {
        return this.gameMode == GameMode.CREATIVE
    }

    /**
     * Teleports player `distance` ahead, without him going through blocks and in blocks<br></br>
     * Most of the code is adapted from my project `SkyblockD` (see https://github.com/Maxuss/SkyblockD )
     * @author maxus
     */
    fun Player.fancyTeleport(distance: Int) {
        teleport(this raycast distance)
    }

    /**
     * Raycasts `distance` blocks ahead and returns farthest non-solid block.
     * Infix, cus it look fancy
     * @author maxus
     */
    infix fun LivingEntity.raycast(distance: Int) : Location {
        return try {
            val eyes: Location = eyeLocation;
            val iterator = BlockIterator(location, 1.0, distance)
            while (iterator.hasNext()) {
                val loc = iterator.next().location
                if (loc.block.type.isSolid) {
                    if (loc == location) return location
                    loc.pitch = eyes.pitch
                    loc.yaw = eyes.yaw
                    loc.y = loc.y + 1
                    return loc
                }
            }
            val n: Location = eyeLocation.clone().add(eyeLocation.direction.multiply(distance))
            n.pitch = eyes.pitch
            n.yaw = eyes.yaw
            n.y = n.y + 1
            n
        } catch (e: IllegalStateException) {
            // Usually doesnt happen
            return location
        }
    }
}