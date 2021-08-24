package com.prince.skyblocksandbox.skyblockcommands

import com.prince.skyblocksandbox.skyblockutils.Extensions.addSpeed
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TestCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd1: Command, label: String, args: Array<out String>): Boolean {
        if(sender !is Player) return false
        sender.addSpeed(50)
        return true
    }
}