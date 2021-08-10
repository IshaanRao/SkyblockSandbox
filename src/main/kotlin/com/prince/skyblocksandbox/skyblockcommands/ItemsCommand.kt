package com.prince.skyblocksandbox.skyblockcommands

import com.prince.skyblocksandbox.skyblockinventories.ItemsInventory
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ItemsCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd1: Command, label: String, args: Array<out String>): Boolean {
        if(sender !is Player) return true
        sender.openInventory(ItemsInventory.getInventory())
        return true
    }
}