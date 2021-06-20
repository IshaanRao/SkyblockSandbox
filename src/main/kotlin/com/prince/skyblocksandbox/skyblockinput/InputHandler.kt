package com.prince.skyblocksandbox.skyblockinput

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.concurrent.CompletableFuture


object InputHandler: Listener {
    val playersInInputHandler = HashMap<Player,ChatInput>()
    @EventHandler
    fun onChat(e:AsyncPlayerChatEvent){
        if(playersInInputHandler.containsKey(e.player)){
            e.isCancelled = true
            val chatInput = playersInInputHandler[e.player]!!
            if(e.message.equals("cancel",true)){
                playersInInputHandler[e.player]!!.future.complete(null)
                playersInInputHandler.remove(e.player)
            }
            if(!chatInput.checker.passesCheck(e.message)){
                e.player.sendMessage("§c"+chatInput.checker.invalidMessage)
                return
            }
            chatInput.future.complete(e.message)
            playersInInputHandler.remove(e.player)
        }
    }
    @EventHandler
    fun onDc(e:PlayerQuitEvent){
        if(playersInInputHandler.containsKey(e.player)){
            playersInInputHandler[e.player]!!.future.complete(null)
            playersInInputHandler.remove(e.player)
        }
    }
    fun addPlayer(p:Player,c:ChatInput){
        playersInInputHandler[p]=c
    }
}