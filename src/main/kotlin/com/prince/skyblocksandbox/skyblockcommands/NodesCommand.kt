package com.prince.skyblocksandbox.skyblockcommands

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockmobs.MobSpawning
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMobs
import com.prince.skyblocksandbox.skyblockutils.SkyblockWorlds
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList

class NodesCommand : CommandExecutor{
    override fun onCommand(sender: CommandSender, cmd1: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.isOp || sender !is Player) {
            return true
        }
        if(args.size<1){
            sender.sendMessage("§c/nodes add/remove/list")
            return true
        }
        when(args[0]){
            "add"->{
                if(args.size<2){
                    sender.sendMessage("§c/nodes add (mob)")
                    return true
                }
                return try {
                    val mob = SkyblockMobs.valueOf(args[1].uppercase(Locale.getDefault()))
                    SkyblockSandbox.instance.spawningNodeConfig.config.set("SpawningNodes.${UUID.randomUUID()}", MobSpawning.SpawningNode(mob, sender.location))
                    SkyblockSandbox.instance.spawningNodeConfig.save()
                    sender.sendMessage("§aSpawning node set at your location")
                    true
                }catch(e:IllegalArgumentException){
                    sender.sendMessage("§cInvalid Mob")
                    true
                }
            }
            "remove"->{

            }
            "list"->{

            }
            "reload"->{
                MobSpawning.restart()
            }
        }
        return true
    }
    class NodesCommandCompletions : TabCompleter {
        override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String> {
            val completions = ArrayList<String>()
            if(args.size==2&&args[0]=="add"){
                StringUtil.copyPartialMatches(args[1], SkyblockMobs.getValues(), completions)
                completions.sort()
            }
            return  completions
        }
    }
}