package com.prince.skyblocksandbox.skyblockcommands

import com.prince.skyblocksandbox.skyblockinventories.EnchantInventory
import com.prince.skyblocksandbox.skyblockmobs.SkyblockZombie
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getSkyblockData
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class EnchantCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd1: Command, label: String, args: Array<out String>): Boolean {
        if(sender !is Player){
            return true
        }
        println(sender.itemInHand.getSkyblockData().itemData.damage)
        //sender.openInventory(EnchantInventory.getInventory())
        return true
    }
}