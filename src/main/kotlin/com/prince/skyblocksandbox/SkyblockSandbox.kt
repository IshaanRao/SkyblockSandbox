package com.prince.skyblocksandbox

import com.prince.skyblocksandbox.skyblockcommands.*
import com.prince.skyblocksandbox.skyblockhandlers.*
import com.prince.skyblocksandbox.skyblockinput.InputHandler
import com.prince.skyblocksandbox.skyblockinventories.ApplyEnchantInventory
import com.prince.skyblocksandbox.skyblockinventories.EnchantInventory
import com.prince.skyblocksandbox.skyblockmobs.MobSpawning
import com.prince.skyblocksandbox.skyblockutils.Config
import com.prince.skyblocksandbox.skyblockutils.SkyblockApi
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin

class SkyblockSandbox : JavaPlugin() {
    lateinit var mobHandler: MobHandler
    lateinit var damageHandler: DamageHandler
    lateinit var spawningNodeConfig: Config
    override fun onEnable() {
        ConfigurationSerialization.registerClass(MobSpawning.SpawningNode::class.java, "SpawningNode")
        instance = this
        log("--------------------------")
        loadVariables()
        registerEvents()
        loadCommands()
        log("--------------------------")
    }
    fun registerEvents(){
        SkyblockApi.start()
        server.pluginManager.registerEvents(LoginHandler(),this)
        server.pluginManager.registerEvents(EnchantInventory,this)
        server.pluginManager.registerEvents(mobHandler, this)
        server.pluginManager.registerEvents(AbilityHandler(),this)
        server.pluginManager.registerEvents(InputHandler,this)
        server.pluginManager.registerEvents(ItemHandler(),this)
        server.pluginManager.registerEvents(ApplyEnchantInventory,this)
        MobSpawning
        StatisticHandler.sbSandbox = this
        server.scheduler.scheduleSyncRepeatingTask(this,StatisticHandler,0,20)
        ActionBarManager(this)
        log("Registered Events")
    }

    override fun onDisable() {
        SkyblockApi.stop()
        mobHandler.killAllMobs()
    }

    fun loadCommands(){
        getCommand("spawnmob").executor = SpawnMobCommand(mobHandler)
        getCommand("spawnmob").tabCompleter = SpawnMobCompletions()
        getCommand("createsword").executor = CreateSwordCommand()
        getCommand("reforge").executor = ReforgeCommand()
        getCommand("enchant").executor = EnchantCommand()
        getCommand("nodes").executor = NodesCommand()
        getCommand("nodes").tabCompleter = NodesCommand.NodesCommandCompletions()
        log("Loaded commands")
    }

    fun loadVariables(){
        damageHandler = DamageHandler()
        mobHandler = MobHandler(this,damageHandler)
        spawningNodeConfig = Config("SpawningNodes")
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