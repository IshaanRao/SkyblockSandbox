package com.prince.skyblocksandbox.skyblockcommands

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockutils.MathUtils
import com.prince.skyblocksandbox.skyblockutils.SkyblockHolograms
import org.bukkit.*
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.material.Wool
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class TestCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd1: Command, label: String, args: Array<out String>): Boolean {
        if(!sender.isOp){
            return true
        }

        return true
    }
}