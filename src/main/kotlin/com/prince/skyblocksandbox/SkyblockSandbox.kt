package com.prince.skyblocksandbox

import com.prince.skyblocksandbox.skyblockcommands.TestMobCommand
import com.prince.skyblocksandbox.skyblockhandlers.AbilityHandler
import com.prince.skyblocksandbox.skyblockhandlers.DamageHandler
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler
import com.prince.skyblocksandbox.skyblockitems.SkyblockSword
import com.prince.skyblocksandbox.skyblockitems.data.ItemData
import com.prince.skyblocksandbox.skyblockitems.data.SwordStats
import com.prince.skyblocksandbox.skyblockmobs.SkyblockZombie
import com.prince.skyblocksandbox.skyblockutils.SkyblockColors
import com.prince.skyblocksandbox.skyblockutils.SkyblockRarities
import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.math.BigInteger


class SkyblockSandbox : JavaPlugin() {
    lateinit var mobHandler: MobHandler
    lateinit var damageHandler: DamageHandler
    override fun onEnable() {
        loadVariables()
        println("--------------------------")

        println("--------------------------")
        server.pluginManager.registerEvents(mobHandler, this)
        server.pluginManager.registerEvents(AbilityHandler(),this)
        getCommand("testmob").setExecutor(TestMobCommand(mobHandler))
    }

    override fun onDisable() {
        mobHandler.killAllMobs()
    }
    fun loadVariables(){
        damageHandler = DamageHandler()
        mobHandler = MobHandler(this,damageHandler)
    }
}