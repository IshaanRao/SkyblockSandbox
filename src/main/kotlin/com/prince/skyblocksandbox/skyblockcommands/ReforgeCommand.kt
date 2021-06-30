package com.prince.skyblocksandbox.skyblockcommands

import com.google.gson.Gson
import com.prince.skyblocksandbox.skyblockitems.data.ReforgeStats
import com.prince.skyblocksandbox.skyblockmobs.SkyblockZombie
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getNbtTag
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getNms
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getSkyblockData
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.isSkyblockItem
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.math.BigInteger

class ReforgeCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd1: Command, label: String, args: Array<out String>): Boolean {
        if(!sender.isOp || sender !is Player){
            return true
        }
        var item = sender.itemInHand
        if(item.type == Material.AIR){
            sender.sendMessage("§cPlease hold a skyblock item")
            return true
        }
        if(!item.isSkyblockItem()){
            sender.sendMessage("§cPlease hold a skyblock item")
            return true
        }
        val sbItem = item.getSkyblockData()
        if(!sbItem.itemData.reforgable){
            sender.sendMessage("§cThis item isn't reforgeable")
            return true
        }
        val reforgeStats = ReforgeStats(
            damage = ReforgeStats.createMap(100,100,100,100,100,100),
            intelligence = ReforgeStats.createMap(100,100,100,100,100,100),
            name = "Test"
        )
        sbItem.itemData.reforge = reforgeStats
        sender.inventory.itemInHand = sbItem.createItem(sender)
        sender.updateInventory()
        return true
    }
}