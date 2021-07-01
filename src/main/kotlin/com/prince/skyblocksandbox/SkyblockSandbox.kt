package com.prince.skyblocksandbox

import com.prince.skyblocksandbox.skyblockcommands.CreateSwordCommand
import com.prince.skyblocksandbox.skyblockcommands.ReforgeCommand
import com.prince.skyblocksandbox.skyblockcommands.TestMobCommand
import com.prince.skyblocksandbox.skyblockenchants.SBEnchants
import com.prince.skyblocksandbox.skyblockhandlers.*
import com.prince.skyblocksandbox.skyblockinput.InputHandler
import com.prince.skyblocksandbox.skyblockutils.SkyblockApi
import io.javalin.Javalin
import org.bukkit.enchantments.Enchantment
import org.bukkit.plugin.java.JavaPlugin
import java.lang.reflect.Field

class SkyblockSandbox : JavaPlugin() {

    lateinit var mobHandler: MobHandler
    lateinit var damageHandler: DamageHandler
    lateinit var app:Javalin
    override fun onEnable() {

        instance = this
        log("--------------------------")
        loadVariables()
        registerEvents()
        loadCommands()
        log("--------------------------")
    }
    fun registerEvents(){
        SkyblockApi.start()
        server.pluginManager.registerEvents(mobHandler, this)
        server.pluginManager.registerEvents(AbilityHandler(),this)
        server.pluginManager.registerEvents(InputHandler,this)
        server.pluginManager.registerEvents(ItemHandler(),this)
        StatisticHandler.sbSandbox = this
        server.scheduler.scheduleSyncRepeatingTask(this,StatisticHandler,0,20)
        ActionBarManager(this)
        log("Registered Events")
    }

    override fun onDisable() {
        SkyblockApi.stop()
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
        val version = "0.1.3"
        val changes = listOf("Added this embed","Added auto restarter","Added mana usage")
        @JvmStatic
        fun log(msg:Any?) {
            println("[SkyblockSandbox] $msg")
        }
    }
}