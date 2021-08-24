package com.prince.skyblocksandbox.skyblockcommands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class TestCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd1: Command, label: String, args: Array<out String>): Boolean {
        if(!sender.isOp){
            return true
        }
        return true
    }
}