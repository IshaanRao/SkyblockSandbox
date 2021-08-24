package com.prince.skyblocksandbox.skyblockhandlers

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockutils.ActionBar
import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.getStats
import org.bukkit.Bukkit

class ActionBarManager(sbSandbox:SkyblockSandbox) : Runnable {
    override fun run() {
        for(player in Bukkit.getOnlinePlayers()){
            val stats = player.getStats()
            val stats2 = StatisticHandler.getPlayerStats(player)
            if(stats2.absorption<=0.toBigInteger()){
                ActionBar("§c${stats2.health}/${stats.health}❤   §a${stats.defense}❈ Defense   §b${stats2.mana}/${stats.intelligence}✎ Mana").sendToPlayer(player)
            }else{
                ActionBar("§6${stats2.health+stats2.absorption}/${stats.health}❤   §a${stats.defense}❈ Defense   §b${stats2.mana}/${stats.intelligence}✎ Mana").sendToPlayer(player)
            }
        }
    }
    init {
        sbSandbox.server.scheduler.scheduleSyncRepeatingTask(sbSandbox,this,0,4)
    }
}