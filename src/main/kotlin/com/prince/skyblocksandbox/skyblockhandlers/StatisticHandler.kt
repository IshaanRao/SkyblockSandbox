package com.prince.skyblocksandbox.skyblockhandlers

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockutils.SkyblockStats.getStats
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.roundToInt

object StatisticHandler : Runnable {
    var PlayerStats:HashMap<Player,Stats> = HashMap()
    var tick = 0
    lateinit var sbSandbox: SkyblockSandbox
    override fun run() {
        for(player in Bukkit.getOnlinePlayers()){
            tick++
            if(tick>=2){
                tick=0
            }
            val playerMaxHealth = player.getStats().health
            val playerMaxIntel = player.getStats().intelligence
            if(!PlayerStats.containsKey(player)){
                PlayerStats[player] = Stats(playerMaxHealth,playerMaxIntel,0.toBigInteger(),0)
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
    fun addAbsorption(p: Player,absorption:BigInteger,time:Long=0L) {
        if(!PlayerStats.containsKey(p)){
            return
        }
        val healthStats = PlayerStats[p]!!
        healthStats.absorption+=absorption
        if(time!=0L){
            //TODO Make absorption expire
        }
    }
    fun removeAbsorption(p:Player,absorption:BigInteger){
        if(!PlayerStats.containsKey(p)){
            return
        }
        val healthStats = PlayerStats[p]!!
        if(healthStats.absorption-absorption<0.toBigInteger()){
            healthStats.absorption = 0.toBigInteger()
        }else{
            healthStats.absorption = healthStats.absorption-absorption
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
    fun removeMana(p:Player,mana:BigInteger){
        if(!PlayerStats.containsKey(p)){
            return
        }
        val stats = PlayerStats[p]!!
        if(stats.mana-mana<0.toBigInteger()){
            stats.mana = 0.toBigInteger()
        }else {
            stats.mana-=mana
        }
        PlayerStats[p] = stats
    }
    fun removeHealth(p: Player,health:BigInteger){
        if(!PlayerStats.containsKey(p)){
            return
        }
        val stats = PlayerStats[p]!!
        if(stats.health-health<0.toBigInteger()){
            //KILL PLAYER
            p.sendMessage("§cYou died!")
            stats.health = p.getStats().health
        }else {
            stats.health-=health
        }
        PlayerStats[p] = stats
    }
    fun damagePlayer(p:Player,rawDamage:BigInteger){
        if(!PlayerStats.containsKey(p)){
            return
        }
        val stats = p.getStats()
        val reduction:BigDecimal = stats.defense.toBigDecimal()/(stats.defense.toBigDecimal()+100.0.toBigDecimal())
        val multiplier:BigDecimal = 1.0.toBigDecimal()-reduction
        val damage = (rawDamage.toBigDecimal()*multiplier).toBigInteger()
        removeHealth(p,damage)
    }
    fun getPlayerStats(p: Player):Stats {
        if(!PlayerStats.containsKey(p)){
            return Stats(100.toBigInteger(),100.toBigInteger(),0.toBigInteger(),0)
        }
        return PlayerStats[p]!!
    }
    data class Stats(var health: BigInteger,var mana:BigInteger,var absorption:BigInteger,var dmgReduction:Int)
}