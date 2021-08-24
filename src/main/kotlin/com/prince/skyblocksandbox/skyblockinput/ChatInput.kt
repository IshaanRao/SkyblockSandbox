package com.prince.skyblocksandbox.skyblockinput

import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class ChatInput(val p:Player,val checker:InputChecks,val startingMsg:String) {
    lateinit var future: CompletableFuture<Any?>
    fun start():Any?{
        p.sendMessage(startingMsg)
        future = CompletableFuture<Any?>()
        InputHandler.addPlayer(p,this)
        return try{
            future.get(30000,TimeUnit.MILLISECONDS)
        }catch (e:TimeoutException){
            null
        }
    }
}