package com.prince.skyblocksandbox

import com.prince.skyblocksandbox.skyblockcommands.CreateSwordCommand
import com.prince.skyblocksandbox.skyblockcommands.EnchantCommand
import com.prince.skyblocksandbox.skyblockcommands.ReforgeCommand
import com.prince.skyblocksandbox.skyblockcommands.TestMobCommand
import com.prince.skyblocksandbox.skyblockhandlers.*
import com.prince.skyblocksandbox.skyblockinput.InputHandler
import com.prince.skyblocksandbox.skyblockinventories.ApplyEnchantInventory
import com.prince.skyblocksandbox.skyblockinventories.EnchantInventory
import com.prince.skyblocksandbox.skyblockutils.SkyblockApi
import org.bukkit.plugin.java.JavaPlugin

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
    }
    fun registerEvents(){
        //SkyblockApi.start()
        server.pluginManager.registerEvents(LoginHandler(),this)
        server.pluginManager.registerEvents(EnchantInventory,this)
        server.pluginManager.registerEvents(mobHandler, this)
        server.pluginManager.registerEvents(AbilityHandler(),this)
        server.pluginManager.registerEvents(InputHandler,this)
        server.pluginManager.registerEvents(ItemHandler(),this)
        server.pluginManager.registerEvents(ApplyEnchantInventory,this)
        StatisticHandler.sbSandbox = this
        server.scheduler.scheduleSyncRepeatingTask(this,StatisticHandler,0,20)
        ActionBarManager(this)
        log("Registered Events")
    }

    override fun onDisable() {
        //SkyblockApi.stop()
        mobHandler.killAllMobs()
    }

    fun loadCommands(){
        getCommand("testmob").setExecutor(TestMobCommand(mobHandler))
        getCommand("createsword").setExecutor(CreateSwordCommand())
        getCommand("reforge").setExecutor(ReforgeCommand())
        getCommand("enchant").setExecutor(EnchantCommand())
        log("Loaded commands")
    }

    fun loadVariables(){
        damageHandler = DamageHandler()
        mobHandler = MobHandler(this,damageHandler)
        log("Loaded variables")
    }

    companion object {
        lateinit var instance: SkyblockSandbox
        @JvmStatic
        fun log(msg:Any?) {
            println("[SkyblockSandbox] $msg")
        }
    }
}