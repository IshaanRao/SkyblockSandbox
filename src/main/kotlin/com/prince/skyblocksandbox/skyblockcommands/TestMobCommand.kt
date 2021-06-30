package com.prince.skyblocksandbox.skyblockcommands

import com.prince.skyblocksandbox.skyblockhandlers.MobHandler
import com.prince.skyblocksandbox.skyblockitems.SkyblockArmor
import com.prince.skyblocksandbox.skyblockitems.data.ArmorTypes
import com.prince.skyblocksandbox.skyblockitems.data.ItemData
import com.prince.skyblocksandbox.skyblockitems.data.ItemStackData
import com.prince.skyblocksandbox.skyblockmobs.SkyblockZombie
import com.prince.skyblocksandbox.skyblockutils.ActionBar
import com.prince.skyblocksandbox.skyblockutils.ColorUtils
import com.prince.skyblocksandbox.skyblockutils.SkyblockRarities
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import sun.plugin2.util.ColorUtil
import java.math.BigInteger

class TestMobCommand(var mobHandler: MobHandler) : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd1: Command, label: String, args: Array<out String>): Boolean {
        if(!sender.isOp || sender !is Player){
            return true
        }
        if(args.size==1){
            ActionBar("testsss").sendToPlayer(sender)
            try{
                mobHandler.spawnMob(SkyblockZombie(BigInteger.valueOf(args[0].toLong())), sender.location)
                sender.inventory.addItem(SkyblockArmor(ItemData(
                    name = "Necron's ${ArmorTypes.CHESTPLATE.getDispName()}",
                    rarity = SkyblockRarities.LEGENDARY,
                    item = ItemStackData(Material.LEATHER_CHESTPLATE,true,"#E7413C"),
                    health = 260.toBigInteger(),
                    defense = 140.toBigInteger(),
                    strength = 40.toBigInteger(),
                    critDamage = 30.toBigInteger(),
                    intelligence = 10.toBigInteger()
                ),
                ArmorTypes.CHESTPLATE).createItem(sender))
            }catch (e:Exception){
                return true
            }
        }
        return true
    }
}