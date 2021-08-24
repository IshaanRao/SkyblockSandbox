package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.skyblockhandlers.DamageHandler
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler.Companion.isSkyblockMob
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import java.util.*


object SalvationAbility : ItemAbility() {
    override val manaCost=20
    override val actions = listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)
    override val abilityType=AbilityTypes.SALVATION
    override val title = "§6Ability: Salvation §e§lRIGHT CLICK"
    override val name: String = "Salvation"
    override val noUseMessage: Boolean
        get() = true
    override val itemType = ItemTypes.BOW
    override fun getDesc(stats: StatsData) : List<String> { return listOf("§7Can be casted after landing §63 §7hits.","§7Shoot a beam penetrating up","§7to §e5 §7foes and dealing §c2x","§7the damage an arrow would.","§7The beam always crits.")}
    override fun execute(e: PlayerInteractEvent) {
        if(!playerOnCooldown(e.player)){
            startCooldown(e.player,40)
            var prev = e.player.eyeLocation

            // previous -> prev.y= prev.y-1
            // changed it a bit, because otherwise it will just look really weird
            // (player would be shooting a beam from their legs)
            prev.y = prev.y - 0.5

            for(i in 1..75) {
                prev = prev.add(prev.direction.multiply(0.5))
                spawnRedstoneParticles(prev)
                for(entity in prev.getNearbyEntities(1.0,1.0,1.0)){
                    val mob = entity.isSkyblockMob()
                    if(mob!=null){
                        DamageHandler.termAbilityDamage(mob,e.player)
                    }
                }
            }
        }

    }
    fun Location.getNearbyEntities(v:Double,v1:Double,v2:Double):Collection<Entity>{
        return world.getNearbyEntities(this,v,v1,v2)
    }
    fun spawnRedstoneParticles(location:Location){
        for(i in 1..5){
            location.world.spigot().playEffect(location,Effect.COLOURED_DUST,15,0,0f,0f,0f,1f,0,50)
        }
    }
}