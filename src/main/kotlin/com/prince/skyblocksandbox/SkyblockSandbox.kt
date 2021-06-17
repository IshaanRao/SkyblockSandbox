package com.prince.skyblocksandbox

import com.prince.skyblocksandbox.skyblockhandlers.MobHandler
import com.prince.skyblocksandbox.skyblockmobs.SkyblockZombie
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
    override fun onEnable() {
        loadVariables()
        println("--------------------------")

        println("--------------------------")
        server.pluginManager.registerEvents(Vampire(mobHandler), this)
        server.pluginManager.registerEvents(mobHandler, this)

    }

    override fun onDisable() {
        mobHandler.killAllMobs()
    }
    fun loadVariables(){
        mobHandler = MobHandler(this)
    }
    class Vampire(val mobHandler: MobHandler) : Listener {
        init {
            print("This thijgamsher has been registered")
        }

        @EventHandler
        fun onHit(event: PlayerJoinEvent) {
            SkyblockZombie(mobHandler, BigInteger.valueOf(6925)).spawn(event.player.location)
        }
    }
}