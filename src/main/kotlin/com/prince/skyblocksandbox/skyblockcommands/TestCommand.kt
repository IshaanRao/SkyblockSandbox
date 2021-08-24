package com.prince.skyblocksandbox.skyblockcommands

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockabilities.SalvationAbility.getNearbyEntities
import com.prince.skyblocksandbox.skyblockabilities.SkyblockAbility
import com.prince.skyblocksandbox.skyblockabilities.TermShortbowAbility
import com.prince.skyblocksandbox.skyblockabilities.YetiSwordAbility.addStand
import com.prince.skyblocksandbox.skyblockhandlers.DamageHandler
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler.Companion.isSkyblockMob
import com.prince.skyblocksandbox.skyblockutils.MathUtils
import com.prince.skyblocksandbox.skyblockutils.SkyblockHolograms
import org.bukkit.*
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.*
import org.bukkit.inventory.ItemStack
import org.bukkit.material.Wool
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.EulerAngle
import org.bukkit.util.Vector

class TestCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd1: Command, label: String, args: Array<out String>): Boolean {
        if(!sender.isOp){
            return true
        }
        return true
    }
}