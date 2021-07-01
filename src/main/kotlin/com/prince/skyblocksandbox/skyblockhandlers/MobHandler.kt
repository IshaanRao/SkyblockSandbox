package com.prince.skyblocksandbox.skyblockhandlers

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockexceptions.skyblockmobs.SkyblockMobSpawnException
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import org.bukkit.Location
import org.bukkit.entity.Arrow
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityCombustEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import java.math.BigInteger


class MobHandler(val sbInstance: SkyblockSandbox, val dmgHandler: DamageHandler) : Listener {

    fun registerMob(mob: SkyblockMob) {
        mobs.add(mob)
    }
    fun spawnMob(mob:SkyblockMob,loc: Location){
        if(!mob.hasSpawned) {
            registerMob(mob)
            mob.hasSpawned = true
            mob.currentHealth = mob.startingHealth
            mob.entity = loc.world.spawnEntity(loc, mob.entityType) as LivingEntity?
            mob.defaultLoad()
            mob.load()
            return
        }
        throw SkyblockMobSpawnException("Tried to spawn mob that has already been spawned")

    }
    companion object {
        var mobs = ArrayList<SkyblockMob>();
        fun Entity.isSkyblockMob(): SkyblockMob? {
            for (mob in mobs) {
                if (mob.entity == this) {
                    return mob
                }
            }
            return null
        }
    }

    init {
        Thread {
            while (sbInstance.isEnabled) {
                try {
                    Thread.sleep(50)
                } catch (ignored: InterruptedException) {

                }
                val mobsToRemove = ArrayList<SkyblockMob>()
                for (mob in mobs) {
                    if (mob.hasSpawned) {
                        if (mob.currentHealth <= BigInteger.valueOf(0) || mob.entity!!.isDead) {
                            mobsToRemove.add(mob)
                            mob.entity!!.health = 0.0
                        } else {
                            mob.loadName()
                        }
                    }
                }
                mobs.removeAll(mobsToRemove)
            }
        }.start()
    }

    @EventHandler
    fun onDamage(e: EntityDamageEvent) {
        if (!e.entity.isDead) {
            val mob = e.entity.isSkyblockMob()
            if (mob != null) {
                if (e.cause != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                    e.isCancelled = true
                }

            }
        }
    }

    @EventHandler
    fun onDamage(e: EntityDamageByEntityEvent) {
        if (!e.entity.isDead) {
            val mob = e.entity.isSkyblockMob() ?: return
            if (e.damager is Player) {
                e.damage = 0.0
                dmgHandler.swordDamage(mob, e.damager as Player)
            } else if (e.damager !is Player && e.damager !is Arrow) {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onCombust(e:EntityCombustEvent){
        if(e.entity.isSkyblockMob()!=null){
            e.isCancelled=true
        }
    }

    fun killAllMobs() {
        for (mob in mobs) {
            if (mob.hasSpawned) {
                mob.entity!!.remove()

            }
        }
        mobs.clear()
    }


}
