package com.prince.skyblocksandbox.skyblockutils

import com.google.gson.Gson
import org.bukkit.entity.Player
import java.util.UUID

class SkyblockStats() {

    fun getStats(player: Player) {

    }

    fun json(player: Player) {
        val uuid: UUID = player.getUniqueId()
        data class Stats(
            val id: UUID,
            val souls: Int,
            val combat: Int,
            val foraging: Int
        )
        var jsonStats = Gson().toJson(Stats(uuid, 0, 0, 0))
    }

}