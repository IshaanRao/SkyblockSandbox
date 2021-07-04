package com.prince.skyblocksandbox

import com.prince.skyblocksandbox.skyblockcommands.CreateSwordCommand
import com.prince.skyblocksandbox.skyblockcommands.ReforgeCommand
import com.prince.skyblocksandbox.skyblockcommands.TestMobCommand
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
       // SkyblockApi.stop()
        mobHandler.killAllMobs()
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