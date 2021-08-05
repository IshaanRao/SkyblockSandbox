package com.prince.skyblocksandbox.skyblockcommands

import com.prince.skyblocksandbox.skyblockabilities.AbilityTypes
import com.prince.skyblocksandbox.skyblockabilities.ItemAbility
import com.prince.skyblocksandbox.skyblockabilities.JujuShortbowAbility
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler.Companion.getId
import com.prince.skyblocksandbox.skyblockitems.SkyblockBow
import com.prince.skyblocksandbox.skyblockitems.data.ItemData
import com.prince.skyblocksandbox.skyblockitems.data.ItemStackData
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMobs
import com.prince.skyblocksandbox.skyblockutils.SkyblockRarities
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.StringUtil
import java.math.BigInteger
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
            val abilities = ArrayList<AbilityTypes>()
            abilities.add(AbilityTypes.TERMSHORTBOW)
            abilities.add(AbilityTypes.SALVATION)
            sender.inventory.addItem(SkyblockBow(ItemData(
                name = "Terminator",
                rarity = SkyblockRarities.LEGENDARY,
                item = ItemStackData(Material.BOW),
                strength = 50.toBigInteger(),
                damage = 310.toBigInteger(),
                critDamage = 250.toBigInteger(),
                abilities = abilities
            )).createItem(sender))
        }catch(e:IllegalArgumentException) {
            sender.sendMessage("${ChatColor.RED}Invalid Mob: /spawnmob <mob>")
            return true
        }
        return true
    }
}