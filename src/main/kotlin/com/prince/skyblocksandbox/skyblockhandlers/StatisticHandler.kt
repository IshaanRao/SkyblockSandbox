package com.prince.skyblocksandbox.skyblockhandlers

import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.getStats
import com.prince.skyblocksandbox.skyblockutils.plus
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.math.BigInteger
import kotlin.math.roundToInt

object StatisticHandler : Runnable {
    var PlayerStats:HashMap<Player,Stats> = HashMap()
    var tick = 0
    override fun run() {
        for(player in Bukkit.getOnlinePlayers()){
            tick++
            if(tick>=2){
                tick=0
            }
            val playerMaxHealth = player.getStats().health
            val playerMaxIntel = player.getStats().intelligence
            if(!PlayerStats.containsKey(player)){
                PlayerStats[player] = Stats(playerMaxHealth,playerMaxIntel)
            }else{
                val stats = PlayerStats[player]!!
                val playerCurrIntel = stats.mana
                val healAmt = 1.5 + (playerMaxHealth.toDouble()/100.0)
                val roundedHealAmt = healAmt.roundToInt().toBigInteger()
                healPlayer(player,roundedHealAmt)
                val regenAmt = playerMaxIntel/50.toBigInteger()
                if(regenAmt+playerCurrIntel>playerMaxIntel){
                    stats.mana=playerMaxIntel
                }else{
                    stats.mana=playerCurrIntel+regenAmt
                }
            }
        }
    }
    fun healPlayer(p: Player,heal:BigInteger){
        if(!PlayerStats.containsKey(p)){
            return
        }
        val stats = PlayerStats[p]!!
        if(stats.health>p.getStats().health||stats.health+heal>p.getStats().health){
            stats.health = p.getStats().health
        }else {
            stats.health = stats.health+heal
        }
        PlayerStats[p] = stats

    }
    fun getPlayerStats(p: Player):Stats {
        if(!PlayerStats.containsKey(p)){
            return Stats(100.toBigInteger(),100.toBigInteger())
        }
        return PlayerStats[p]!!
    }
    data class Stats(var health: BigInteger,var mana:BigInteger)
}