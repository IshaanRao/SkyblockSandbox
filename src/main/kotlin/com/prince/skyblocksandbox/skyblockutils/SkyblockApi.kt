package com.prince.skyblocksandbox.skyblockutils

import club.minnced.discord.webhook.WebhookClientBuilder
import club.minnced.discord.webhook.send.WebhookEmbed
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.prince.skyblocksandbox.SkyblockSandbox
import io.javalin.Javalin
import org.apache.commons.io.IOUtils
import org.bukkit.Bukkit
import java.net.URL

object SkyblockApi {
    lateinit var app: Javalin
    val client = WebhookClientBuilder("https://discord.com/api/webhooks/860061026908766238/vB23P1qXPJ0fjUqA_XOmBPteT5w2v_lrNZWutjK7knoZRM15bCNrxcVqvCQ9MpgtSSTZ").build()
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
                    Bukkit.broadcastMessage("§c§l[SERVER] §bServer is restarting in §a30 seconds§b for a §aGame Update")
                },20L*30)
                Bukkit.getScheduler().scheduleSyncDelayedTask(SkyblockSandbox.instance,{
                    Bukkit.broadcastMessage("§c§l[SERVER] §bServer is restarting in §a5 seconds§b for a §aGame Update")
                },20L*55)
                Bukkit.getScheduler().scheduleSyncDelayedTask(SkyblockSandbox.instance,{
                    var description = ""
                    val json = IOUtils.toString(URL("https://raw.githubusercontent.com/IshaanRao/SkyblockSandbox/master/src/main/kotlin/com/prince/skyblocksandbox/skyblockutils/update.json?token=AOL4U6F5TU6VEUKMFUGULHDA45K66"))
                    var jsonObj = gson.fromJson(json,JsonObject::class.java)
                    var changes = jsonObj.get("changelog").asJsonArray
                    changes.forEachIndexed { index, s ->
                        description += "• `${s.asString}`"
                        if(index-1!=changes.size()){
                            description += "\n"
                        }
                    }
                    client.send(WebhookEmbedBuilder()
                        .setAuthor(WebhookEmbed.EmbedAuthor("Update v${jsonObj.get("version").asString}",null,null))
                        .setTitle(WebhookEmbed.EmbedTitle("Change Log",null))
                        .setDescription(description)
                        .setColor(3077991)
                        .build()
                    )
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