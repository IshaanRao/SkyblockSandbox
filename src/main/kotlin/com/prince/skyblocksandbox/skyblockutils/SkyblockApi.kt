package com.prince.skyblocksandbox.skyblockutils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.prince.skyblocksandbox.SkyblockSandbox
import io.javalin.Javalin
import org.bukkit.Bukkit
import org.bukkit.Server

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
                Bukkit.broadcastMessage("§c§l[SERVER] §bServer is restarting in §a60 seconds§b for a §aGame Update")
                Bukkit.getScheduler().scheduleSyncDelayedTask(SkyblockSandbox.instance,{
                    Bukkit.broadcastMessage("§c§l[SERVER] §bServer is restarting in §a6=30 seconds§b for a §aGame Update")
                },20L*30)
                Bukkit.getScheduler().scheduleSyncDelayedTask(SkyblockSandbox.instance,{
                    Bukkit.broadcastMessage("§c§l[SERVER] §bServer is restarting in §a6=5 seconds§b for a §aGame Update")
                },20L*55)
                Bukkit.getScheduler().scheduleSyncDelayedTask(SkyblockSandbox.instance,{
                    SkyblockSandbox.instance.server.spigot().restart()
                },20L*60)
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