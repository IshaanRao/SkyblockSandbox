package com.prince.skyblocksandbox.skyblockcommands

import com.google.gson.Gson
import com.prince.skyblocksandbox.skyblockitems.data.SwordReforgeStats
import com.prince.skyblocksandbox.skyblockitems.data.SwordStats
import com.prince.skyblocksandbox.skyblockmobs.SkyblockZombie
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getNbtTag
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getNms
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getSwordData
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.isSkyblockSword
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
        if(!item.isSkyblockSword()){
            sender.sendMessage("§cPlease hold a skyblock item")
            return true
        }
        val sword = item.getSwordData()
        val stats = sword.swordStats
        val reforgeStats = SwordReforgeStats(
            damage = SwordReforgeStats.createMap(100,100,100,100,100,100),
            intel = SwordReforgeStats.createMap(100,100,100,100,100,100),
            name = "Test"
        )
        sword.swordStats = SwordStats(
            damage = stats.damage,
            strength = stats.strength,
            critDamage = stats.critDamage,
            critChance = stats.critChance,
            intel = stats.intel,
            reforge = reforgeStats
        )
        item = sword.setTags(item)
        val meta = item.itemMeta
        meta.lore = sword.generateLore()
        meta.displayName ="${sword.itemData.rarity.getColor()}${reforgeStats.name} ${sword.itemData.name}"
        item.itemMeta = meta
        sender.inventory.itemInHand = item
        sender.updateInventory()
        return true
    }
}