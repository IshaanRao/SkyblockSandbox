package com.prince.skyblocksandbox.skyblockcommands

import com.prince.skyblocksandbox.skyblockabilities.AbilityTypes
import com.prince.skyblocksandbox.skyblockinput.ChatInput
import com.prince.skyblocksandbox.skyblockinput.InputChecks
import com.prince.skyblocksandbox.skyblockitems.SkyblockSword
import com.prince.skyblocksandbox.skyblockitems.data.ItemData
import com.prince.skyblocksandbox.skyblockitems.data.ItemStackData
import com.prince.skyblocksandbox.skyblockutils.SkyblockRarities
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*


class CreateSwordCommand : CommandExecutor{
    override fun onCommand(sender: CommandSender, cmd1: Command, label: String, args: Array<out String>): Boolean {

        if ( sender !is Player) {
            return true
        }
        Thread {
            try {
                sender.sendMessage("§2Welcome to the Sword Creater type `cancel` at any time to cancel (Automatically cancels after 30 seconds without a response)")
                val itemName:String = (ChatInput(sender,InputChecks.STRING,"§aPlease enter your weapon name").start() as String)
                sender.sendMessage("§6Set weapon name to `$itemName`")
                val mat:Material = Material.valueOf((ChatInput(sender,InputChecks.MAT,"§aPlease enter your material, \nAccepted Materials: diamond_sword,diamond_axe,diamond_spade,gold_sword,gold_axe,gold_spade,iron_sword,iron_axe,iron_spade,stone_sword,stone_axe,stone_spade,wood_sword,wood_axe,wood_spade").start() as String).uppercase(
                    Locale.getDefault()
                ))
                sender.sendMessage("§6Set weapon material to §a${mat.name}")
                val damage:Int = (ChatInput(sender, InputChecks.INT, "§aPlease enter your weapon damage").start() as String).toInt()
                sender.sendMessage("§6Set weapon damage to §a$damage")
                val strength:Int = (ChatInput(sender, InputChecks.INT, "§aPlease enter your weapons strength").start() as String).toInt()
                sender.sendMessage("§6Set weapon strength to §a$strength")
                val critDamage:Int = (ChatInput(sender, InputChecks.INT, "§aPlease enter your weapons crit damage").start() as String).toInt()
                sender.sendMessage("§6Set weapon crit damage to §a$critDamage")
                val critChance:Int = (ChatInput(sender, InputChecks.INT, "§aPlease enter your weapons crit chance").start() as String).toInt()
                sender.sendMessage("§6Set weapon crit chance to §a$critChance")
                val intel:Int = (ChatInput(sender, InputChecks.INT, "§aPlease enter your weapons intelligence").start() as String).toInt()
                sender.sendMessage("§6Set weapon intelligence to §a$intel")
                val abilityStr:String = ChatInput(sender,InputChecks.ABILITY,"§aPlease enter what ability you want ('none' for no ability)").start() as String//TODO(ABILITY LIST)
                var ability:AbilityTypes
                try{
                    ability = AbilityTypes.valueOf(abilityStr.uppercase(Locale.getDefault()))
                }catch (e:Exception){
                    ability = AbilityTypes.NONE
                }
                sender.sendMessage("§6Set weapon ability to §a${if (ability==AbilityTypes.NONE) ability.name else "None"}")
                val rarity:SkyblockRarities = SkyblockRarities.valueOf((ChatInput(sender,InputChecks.RARITY,"§aPlease enter what rarity you want, common,uncommon,rare,epic,legendary,mythic").start() as String).uppercase(Locale.getDefault()))
                sender.sendMessage("§6Set weapon rarity to §a${rarity.name}")
                var reforgeable:Boolean = (ChatInput(sender,InputChecks.BOOLEAN,"§aPlease enter if you want this item to be reforgeable (true or false)").start() as String).toBoolean()
                sender.sendMessage("§6Set if weapon is reforgeable to §a$reforgeable")
                val sword = SkyblockSword(ItemData(
                    name = itemName,
                    rarity = rarity,
                    item = ItemStackData(mat),
                    reforgable = reforgeable,
                    damage = damage.toBigInteger(),
                    strength = strength.toBigInteger(),
                    critDamage = critDamage.toBigInteger(),
                    critChance = critChance,
                    intelligence = intel.toBigInteger(),
                    ability = ability
                ))
                sender.sendMessage("You have received your "+itemName)
                sender.inventory.addItem(sword.createItem(sender))
            }catch(e:NullPointerException){
                if(sender.isOnline){
                    sender.sendMessage("§cSword Creator cancelled after waiting for 30 seconds (Or manually cancelled)!")
                }
            }
        }.start()
        return true
    }


}