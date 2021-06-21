package com.prince.skyblocksandbox

import com.prince.skyblocksandbox.skyblockcommands.CreateSwordCommand
import com.prince.skyblocksandbox.skyblockcommands.TestMobCommand
import com.prince.skyblocksandbox.skyblockhandlers.AbilityHandler
import com.prince.skyblocksandbox.skyblockhandlers.DamageHandler
import com.prince.skyblocksandbox.skyblockinput.InputHandler
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler
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
        server.pluginManager.registerEvents(mobHandler, this)
        server.pluginManager.registerEvents(AbilityHandler(),this)
        server.pluginManager.registerEvents(InputHandler,this)
        log("Registered Events")
    }
    override fun onDisable() {
        mobHandler.killAllMobs()
    }
    fun loadCommands(){
        getCommand("testmob").setExecutor(TestMobCommand(mobHandler))
        getCommand("createsword").setExecutor(CreateSwordCommand())
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