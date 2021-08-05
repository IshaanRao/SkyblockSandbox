package com.prince.skyblocksandbox.skyblockhandlers

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockexceptions.skyblockmobs.SkyblockMobSpawnException
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler.Companion.isSkyblockMob
import com.prince.skyblocksandbox.skyblockmobs.SkyblockMob
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Arrow
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.entity.*
import java.math.BigInteger
import java.util.*
import kotlin.ConcurrentModificationException
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MobHandler(val sbInstance: SkyblockSandbox, val dmgHandler: DamageHandler) : Listener {
    val playerDamageEventsCancelled:List<EntityDamageEvent.DamageCause> = listOf(EntityDamageEvent.DamageCause.FIRE,
        EntityDamageEvent.DamageCause.FIRE_TICK,EntityDamageEvent.DamageCause.FALL,EntityDamageEvent.DamageCause.DROWNING,EntityDamageEvent.DamageCause.BLOCK_EXPLOSION,EntityDamageEvent.DamageCause.SUFFOCATION)
    fun registerMob(mob: SkyblockMob) {
        var uuid = UUID.randomUUID()
        while(mobs.containsKey(uuid)){
            uuid = UUID.randomUUID()
        }
        mobs.put(uuid,mob)
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
        var mobs = HashMap<UUID,SkyblockMob>();
        fun Entity.isSkyblockMob(): SkyblockMob? {
            for (mob in mobs.values) {
                if (mob.entity == this) {
                    return mob
                }
            }
            return null
        }
        fun SkyblockMob.getId(): UUID? {
            for (key in mobs.keys) {
                if(this==mobs[key]){
                    return key
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
                val mobsToRemove = ArrayList<UUID>()
                val mobEntries = mobs.entries
                try {
                    for (set in mobEntries) {
                        val mob = set.value
                        val uuid = set.key
                        if (mob.hasSpawned) {
                            try {
                                if (mob.currentHealth <= BigInteger.valueOf(0) || mob.entity!!.isDead) {
                                    mobsToRemove.add(uuid)
                                    mob.entity!!.health = 0.0
                                } else {
                                    mob.loadName()
                                }
                            } catch (ignored: NullPointerException) {
                            }
                        }
                    }
                }catch (ignored:ConcurrentModificationException){}
                for(uuid in mobsToRemove) {
                    mobs.remove(uuid)
                }
            }
        }.start()
    }

    @EventHandler
    fun onDamage(e: EntityDamageEvent) {
        if (!e.entity.isDead) {
            val mob = e.entity.isSkyblockMob()
            if (mob != null) {
                if (e.cause != EntityDamageEvent.DamageCause.ENTITY_ATTACK&&e.cause!=EntityDamageEvent.DamageCause.PROJECTILE) {
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
            } else if(e.damager is Arrow){
                val arrow = (e.damager as Arrow)
                if(arrow.shooter is Player){
                    val shooter = (arrow.shooter as Player)
                    e.damage = 0.0
                    dmgHandler.bowDamage(mob,shooter,arrow.isCritical)
                }
            }
        }
    }
    @EventHandler
    fun portalEvent(e: EntityPortalEvent){
        if(e.entity !is Player){
            e.isCancelled = true
        }
    }
    @EventHandler
    fun cancelPlayerDamage(e: EntityDamageEvent){
        if(e.entity !is Player){
            return
        }
        if(playerDamageEventsCancelled.contains(e.cause)){
            e.isCancelled = true
        }
        if(e.cause == EntityDamageEvent.DamageCause.VOID){
            StatisticHandler.killPlayer(e.entity as Player)
            e.isCancelled = true
            return
        }
    }
    @EventHandler
    fun onFoodDepletion(e:FoodLevelChangeEvent){
        e.isCancelled = true
    }
    @EventHandler
    fun cancelPlayerHits(e: EntityDamageByEntityEvent){
        if(e.entity is Player && e.damager is Player){
            e.isCancelled = true
        }
    }
    @EventHandler
    fun onPlayerDamage(e: EntityDamageByEntityEvent) {
        if (!e.damager.isDead) {
            val mob:SkyblockMob? =
            if(e.damager is Arrow) {
                val arrow = e.damager as Arrow
                if(arrow.shooter is Entity) {
                        (arrow.shooter as Entity).isSkyblockMob()
                }else {
                    null
                }
            }else{
                e.damager.isSkyblockMob()
            }
            if(mob==null){
                return
            }
            if (e.entity is Player) {
                if((e.entity as Player).gameMode != GameMode.CREATIVE) {
                    e.damage = 0.0
                    StatisticHandler.damagePlayer(e.entity as Player, mob.damage.toBigInteger(), mob)
                }
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
        for (mob in mobs.values) {
            if (mob.hasSpawned) {
                mob.entity!!.remove()

            }
        }
        mobs.clear()
    }


}
