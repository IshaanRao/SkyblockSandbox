package com.prince.skyblocksandbox.skyblockcommands

import com.prince.skyblocksandbox.skyblockhandlers.MobHandler
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler.Companion.getId
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMobs
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*


class SpawnMobCommand(var mobHandler: MobHandler) : CommandExecutor{
    override fun onCommand(sender: CommandSender, cmd1: Command, label: String, args: Array<out String>): Boolean {
        if(!sender.isOp || sender !is Player){
            return true
        }
        if(args.size!=1){
            sender.sendMessage("${ChatColor.RED}Invalid Arguments: /spawnmob <mob>")
            return true
        }
        try {
            val mob = SkyblockMobs.valueOf(args[0].uppercase(Locale.getDefault())).getMob()
            mobHandler.spawnMob(mob,sender.location)
            sender.sendMessage("${ChatColor.GREEN}Spawned ${ChatColor.AQUA}${mob.name}${ChatColor.GREEN}, ID: ${ChatColor.AQUA}${mob.getId()}")
        }catch(e:IllegalArgumentException) {
            sender.sendMessage("${ChatColor.RED}Invalid Mob: /spawnmob <mob>")
            return true
        }
        return true
    }
}