package com.prince.skyblocksandbox.skyblockcommands

import com.prince.skyblocksandbox.skyblockhandlers.MobHandler
import com.prince.skyblocksandbox.skyblockitems.SkyblockArmor
import com.prince.skyblocksandbox.skyblockitems.data.ArmorTypes
import com.prince.skyblocksandbox.skyblockitems.data.ItemData
import com.prince.skyblocksandbox.skyblockitems.data.ItemStackData
import com.prince.skyblocksandbox.skyblockmobs.SkyblockZombie
import com.prince.skyblocksandbox.skyblockutils.ActionBar
import com.prince.skyblocksandbox.skyblockutils.SkyblockRarities
import org.bukkit.Material
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
                mobHandler.spawnMob(SkyblockZombie(BigInteger.valueOf(args[0].toLong())), sender.location)
            }catch (e:Exception){
                return true
            }
        }
        return true
    }
}