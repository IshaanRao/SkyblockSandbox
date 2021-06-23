package com.prince.skyblocksandbox

import com.prince.skyblocksandbox.skyblockcommands.CreateSwordCommand
import com.prince.skyblocksandbox.skyblockcommands.ReforgeCommand
import com.prince.skyblocksandbox.skyblockcommands.TestMobCommand
import com.prince.skyblocksandbox.skyblockenchants.SBEnchants
import com.prince.skyblocksandbox.skyblockhandlers.AbilityHandler
import com.prince.skyblocksandbox.skyblockhandlers.DamageHandler
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler
import com.prince.skyblocksandbox.skyblockinput.InputHandler
import org.bukkit.enchantments.Enchantment
import org.bukkit.plugin.java.JavaPlugin
import java.lang.reflect.Field

class SkyblockSandbox : JavaPlugin() {

    lateinit var mobHandler: MobHandler
    lateinit var damageHandler: DamageHandler

    override fun onEnable() {
        instance = this
        log("--------------------------")
        loadVariables()
        registerEvents()
        loadCommands()
        log("--------------------------")
        SBEnchants.customEnchants.add(SBEnchants.cubism); SBEnchants.customEnchants.add(SBEnchants.dragonHunter); SBEnchants.customEnchants.add(SBEnchants.enderSlayer); SBEnchants.customEnchants.add(SBEnchants.execute); SBEnchants.customEnchants.add(SBEnchants.firstStrike); SBEnchants.customEnchants.add(SBEnchants.giantKiller); SBEnchants.customEnchants.add(SBEnchants.impaling); SBEnchants.customEnchants.add(SBEnchants.prosecute); SBEnchants.customEnchants.add(SBEnchants.titanKiller); SBEnchants.customEnchants.add(SBEnchants.tripleStrike)
        for (enchantment in SBEnchants.customEnchants) {
            registerEnchantment(enchantment)
        }
    }

    fun registerEvents(){
        server.pluginManager.registerEvents(mobHandler, this)
        server.pluginManager.registerEvents(AbilityHandler(),this)
        server.pluginManager.registerEvents(InputHandler,this)
        log("Registered Events")
    }

    override fun onDisable() {
        mobHandler.killAllMobs()
        try {
            val keyField = Enchantment::class.java.getDeclaredField("byID")
            keyField.isAccessible = true
            val byKey = keyField[null] as HashMap<Int, Enchantment>
            for (enchantment in SBEnchants.customEnchants) {
                if (byKey.containsKey(enchantment.id)) {
                    byKey.remove(enchantment.id)
                }
            }
            val nameField = Enchantment::class.java.getDeclaredField("byName")
            nameField.isAccessible = true
            val byName = nameField[null] as HashMap<String, Enchantment>
            for (enchantment in SBEnchants.customEnchants) {
                if (byName.containsKey(enchantment.name)) {
                    byName.remove(enchantment.name)
                }
            }
        } catch (ignored: java.lang.Exception) {}
    }

    fun loadCommands(){
        getCommand("testmob").setExecutor(TestMobCommand(mobHandler))
        getCommand("createsword").setExecutor(CreateSwordCommand())
        getCommand("reforge").setExecutor(ReforgeCommand())
        log("Loaded commands")
    }

    fun loadVariables(){
        damageHandler = DamageHandler()
        mobHandler = MobHandler(this,damageHandler)
        log("Loaded variables")
    }

    fun registerEnchantment(enchantment: Enchantment?) {
        var registered = true
        try {
            val f: Field = Enchantment::class.java.getDeclaredField("acceptingNew")
            f.setAccessible(true)
            f.set(null, true)
            Enchantment.registerEnchantment(enchantment)
        } catch (e: Exception) {
            registered = false
            e.printStackTrace()
        }
        if (registered) {}
    }

    companion object {
        lateinit var instance: SkyblockSandbox
        @JvmStatic
        fun log(msg:Any?) {
            println("[SkyblockSandbox] $msg")
        }
    }
}