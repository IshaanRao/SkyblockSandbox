package com.prince.skyblocksandbox.skyblockutils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.prince.skyblocksandbox.SkyblockSandbox
import io.javalin.Javalin
import org.bukkit.Bukkit

object SkyblockApi {
    lateinit var app: Javalin
    fun start() {
        val classLoader = Thread.currentThread().contextClassLoader
        Thread.currentThread().contextClassLoader = SkyblockSandbox::class.java.classLoader
        app = Javalin.create().start(2082)
        app.post("/") { context ->
            val gson = Gson()
            var body = gson.fromJson(context.body(),JsonObject::class.java)
            var action = body.get("action").asString
            if(action == "completed"){
                Bukkit.broadcastMessage("§c§l[SERVER] §bServer is restarting in 60 seconds for a §aGame Update")
            }
        }
        Thread.currentThread().contextClassLoader = classLoader
        SkyblockSandbox.log("Started Web Server")
    }
    fun stop() {
        val classLoader = Thread.currentThread().contextClassLoader
        Thread.currentThread().contextClassLoader = SkyblockSandbox::class.java.classLoader
        app.stop()
        Thread.currentThread().contextClassLoader = classLoader
        SkyblockSandbox.log("Stopped Web Server")
    }
}