package com.prince.skyblocksandbox.skyblockutils

import com.prince.skyblocksandbox.SkyblockSandbox
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class Config(val name: String){
    val configFile:File
    val config:FileConfiguration
    init {
        configFile = File("${SkyblockSandbox.instance.dataFolder}/$name.yml")
        config = YamlConfiguration.loadConfiguration(configFile)
    }
    fun save(){
        config.save(configFile)
    }
    fun set(path:String,value:Any) {
        config.set(path, value)
    }

}