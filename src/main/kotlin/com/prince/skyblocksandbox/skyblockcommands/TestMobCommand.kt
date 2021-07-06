package com.prince.skyblocksandbox.skyblockcommands

import com.prince.skyblocksandbox.skyblockhandlers.MobHandler
import com.prince.skyblocksandbox.skyblockinventories.EnchantInventory
import com.prince.skyblocksandbox.skyblockmobs.SkyblockZombie
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.math.BigInteger

class TestMobCommand(var mobHandler: MobHandler) : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd1: Command, label: String, args: Array<out String>): Boolean {
        if(!sender.isOp || sender !is Player){
            return true
        }
        if(args.size==1){
            try{
                mobHandler.spawnMob(SkyblockZombie(), sender.location)
                EnchantInventory.inventory.open(sender)
                sender.sendMessage("§aSpawned test zombie with a health of "+ args[0].toBigInteger())
            }catch (e:Exception){
                return true
            }
        }
        return true
    }
}