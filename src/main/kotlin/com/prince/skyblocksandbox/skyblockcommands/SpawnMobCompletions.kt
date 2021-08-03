package com.prince.skyblocksandbox.skyblockcommands

import com.prince.skyblocksandbox.skyblockmobs.SkyblockMobs
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.util.StringUtil
import java.util.ArrayList

class SpawnMobCompletions : TabCompleter {
    override fun onTabComplete(sender: CommandSender?, command: Command?, alias: String?, args: Array<String?>): List<String> {
        val completions: ArrayList<String> = ArrayList()
        StringUtil.copyPartialMatches(args[0], SkyblockMobs.getValues(), completions)
        completions.sort()
        return completions
    }
}