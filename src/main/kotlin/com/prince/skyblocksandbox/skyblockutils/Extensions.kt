package com.prince.skyblocksandbox.skyblockutils

import net.minecraft.server.v1_8_R3.EntityInsentient
import net.minecraft.server.v1_8_R3.EntityPlayer
import net.minecraft.server.v1_8_R3.GenericAttributes
import net.minecraft.server.v1_8_R3.IAttribute
import org.bukkit.GameMode
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

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
}